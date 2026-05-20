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
        String rawVersion = null;
        try {
            rawVersion = Bukkit.getMinecraftVersion();
        } catch (NoSuchMethodError ignored) {}

        if (rawVersion == null || rawVersion.isEmpty()) {
            String v = Bukkit.getVersion(); // e.g. "git-Bukkit-0.0.0-1060-... (MC: 1.7.3)"
            int start = v.lastIndexOf("MC: ");
            if (start != -1) {
                rawVersion = v.substring(start + 4, v.length() - 1);
            } else {
                rawVersion = "unknown";
            }
        }

        String matchInfo = getVersion("release", rawVersion);
        if (matchInfo.isEmpty()) {
            matchInfo = getVersion("beta", rawVersion);
        }

        if (matchInfo.isEmpty()) {
            // Fallback to b1.7.3 if everything fails, or throw error?
            // User said it supports b1.7.3 and 1.21.x.
            Glimmer fallback = tryInstantiate("io.github.adrianvic.nemesiseye.impl.b1_7_3");
            if (fallback != null) return fallback;
            throw new IllegalStateException("No suitable implementation found for version " + rawVersion);
        }

        String[] partsInfo = matchInfo.split("\\|");
        String classSuffix = partsInfo[1];

        Glimmer glimmer = tryInstantiate("io.github.adrianvic.nemesiseye.impl." + classSuffix);
        if (glimmer != null) return glimmer;

        // Backward search for older implementations
        String[] versionParts = rawVersion.split("\\.");
        int major = parseInt(versionParts[0]);
        int minor = versionParts.length > 1 ? parseInt(versionParts[1]) : 0;
        int patch = versionParts.length > 2 ? parseInt(versionParts[2]) : 0;

        while (major >= 0) {
            while (minor >= 0) {
                while (patch >= 0) {
                    String className = String.format("io.github.adrianvic.nemesiseye.impl.r%d_%d_%d", major, minor, patch);
                    glimmer = tryInstantiate(className);
                    if (glimmer != null) return glimmer;
                    
                    className = String.format("io.github.adrianvic.nemesiseye.impl.r%d_%d", major, minor);
                    glimmer = tryInstantiate(className);
                    if (glimmer != null) return glimmer;

                    patch--;
                }
                minor--;
                patch = 20; // Search up to .20 patch of previous minor
            }
            major--;
            minor = 30; // Search up to .30 minor of previous major
        }

        throw new IllegalStateException("No suitable implementation found for version " + rawVersion);
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