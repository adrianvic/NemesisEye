package io.github.adrianvic.nemesiseye;

import java.util.regex.Pattern;

public class DataShifter {
    public static boolean safeMatches(String expression, String against) {
        String cleanPattern = expression.trim();
        Pattern pattern = Pattern.compile(cleanPattern, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(against).matches();
    }
}
