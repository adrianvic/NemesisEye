package io.github.adrianvic.nemesiseye.reflection;

import io.github.adrianvic.nemesiseye.DataShifter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VersionMatcher {

    public VersionMatcher() {}

    private record Entry(String pattern, String classSuffix) {}

    public String getVersion(String type, String serverVersion) {
        if (type == null || serverVersion == null) {
            return "";
        }

        Map<String, List<Entry>> map = populateMap();
        List<Entry> entries = map.get(type.toLowerCase());
        if (entries == null || entries.isEmpty()) {
            return "";
        }

        for (Entry e : entries) {
            if (DataShifter.safeMatches(e.pattern, serverVersion)) {
                return e.pattern + "|" + e.classSuffix;
            }
        }

        List<Entry> sorted = new ArrayList<>(entries);
        Collections.sort(sorted, (a, b) -> compareVersions(a.pattern, b.pattern));

        Entry oldest = sorted.get(0);
        Entry newest = sorted.get(sorted.size() - 1);

        int cmpOldest = compareVersions(serverVersion, oldest.pattern);
        int cmpNewest = compareVersions(serverVersion, newest.pattern);

        if (cmpOldest < 0) {
            return oldest.pattern + "|" + oldest.classSuffix;
        } else if (cmpNewest > 0) {
            return newest.pattern + "|" + newest.classSuffix;
        }

        // should not happen because we already tried all patterns
        return newest.pattern + "|" + newest.classSuffix;
    }

    private Map<String, List<Entry>> populateMap() {
        Map<String, List<Entry>> map = new HashMap<>();

        // RELEASE patterns, newest first (order does not matter for matching)
        map.put("release", List.of(
                new Entry("^1\\.21\\..*$", "r1_21")
        ));

        // BETA patterns
        map.put("beta", List.of(
                new Entry("^1\\.7\\.3$", "b1_7_3")
        ));

        return map;
    }

    private int compareVersions(String v1, String v2) {
        String clean1 = v1.replaceAll("[^0-9.]", "");
        String clean2 = v2.replaceAll("[^0-9.]", "");

        String[] a1 = clean1.split("\\.");
        String[] a2 = clean2.split("\\.");

        int len = Math.max(a1.length, a2.length);
        for (int i = 0; i < len; i++) {
            int n1 = i < a1.length ? parseInt(a1[i]) : 0;
            int n2 = i < a2.length ? parseInt(a2[i]) : 0;
            if (n1 != n2) {
                return n1 - n2;
            }
        }
        return 0;
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Glimmer loadGlim() {
        String rawVersion;
        try {
            rawVersion = Bukkit.getMinecraftVersion();   // returns something like "1.21.10"
        } catch (NoSuchMethodError e) {
            return betaLoadGlim();
        }

        String matchInfo = getVersion("release", rawVersion);
        if (matchInfo.isEmpty()) {
            // TODO: Should change to something more robust, it's not beta since we have Bukkit.getMinecraftVersion()
            return betaLoadGlim();
        }

        // split the returned string: "pattern|classSuffix"
        String[] partsInfo = matchInfo.split("\\|");
        String classSuffix = partsInfo[1];   // e.g. "r1_21"

        String[] versionParts = rawVersion.split("\\.");
        int major = parseInt(versionParts[0]);
        int minor = parseInt(versionParts[1]);
        int patch = versionParts.length > 2 ? parseInt(versionParts[2]) : 0;

        while (true) {
            String className = "io.github.adrianvic.nemesiseye.impl." + classSuffix;
            Glimmer glimmer = tryInstantiate(className);
            if (glimmer != null) return glimmer;

            if (patch > 0) {
                patch--;
                continue;
            }
            if (minor > 0) {
                minor--;
                patch = 20;
                continue;
            }
            className = "io.github.adrianvic.nemesiseye.impl.r" + major + "_" + minor;
            glimmer = tryInstantiate(className);
            if (glimmer != null) return glimmer;

            throw new IllegalStateException(
                    "No suitable implementation found for version " + rawVersion);
        }
    }

    private Glimmer betaLoadGlim() {
        // Bukkit.getVersion() // returns something like "1.1.10 (MC: 1.7.3)" WEIRD
        return tryInstantiate("io.github.adrianvic.nemesiseye.impl.b1_7_3"); // only supported beta version for now
    }

    private Glimmer tryInstantiate(String className) {
        try {
            Class<?> clazz = Class.forName(className, true, getClass().getClassLoader());
            if (!Glimmer.class.isAssignableFrom(clazz)) {
                return null;
            }
            return (Glimmer) clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}