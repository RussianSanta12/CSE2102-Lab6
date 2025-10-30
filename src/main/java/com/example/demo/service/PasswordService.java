package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PasswordService {

    // Evaluate password quality and return score 0-100
    public PasswordResult evaluate(String password) {
        if (password == null) password = "";
        String p = password;

        String lower = p.toLowerCase(Locale.ROOT);
        // Common passwords — if found, treat as very weak
        String[] common = {"password", "123456", "12345678", "qwerty", "abc123", "letmein"};
        for (String c : common) {
            if (lower.contains(c)) {
                return new PasswordResult(5, "Weak", "Matches a common password pattern");
            }
        }

        int score = 0;

        // length
        int len = p.length();
        if (len >= 8) score += 20;
        if (len >= 12) score += 10;
        if (len >= 16) score += 10;

        boolean hasLower = p.chars().anyMatch(Character::isLowerCase);
        boolean hasUpper = p.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = p.chars().anyMatch(Character::isDigit);
        boolean hasSymbol = p.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));

        if (hasLower) score += 15;
        if (hasUpper) score += 15;
        if (hasDigit) score += 15;
        if (hasSymbol) score += 15;

        // penalty for long repeated sequences
        if (hasRepeatedSequence(p)) score -= 10;

        // clamp
        if (score < 0) score = 0;
        if (score > 100) score = 100;

        String category;
        if (score < 40) category = "Weak";
        else if (score < 70) category = "Moderate";
        else category = "Strong";

        String message = String.format("Length=%d classes=[%s%s%s%s]", len,
                hasLower ? "l" : "",
                hasUpper ? "U" : "",
                hasDigit ? "d" : "",
                hasSymbol ? "s" : "");

        return new PasswordResult(score, category, message);
    }

    private boolean hasRepeatedSequence(String s) {
        if (s == null || s.length() < 3) return false;
        // simple check: three same chars in a row
        for (int i = 0; i < s.length() - 2; i++) {
            char a = s.charAt(i);
            if (s.charAt(i + 1) == a && s.charAt(i + 2) == a) return true;
        }
        return false;
    }

    public static class PasswordResult {
        private final int score;
        private final String category;
        private final String message;

        public PasswordResult(int score, String category, String message) {
            this.score = score;
            this.category = category;
            this.message = message;
        }

        public int getScore() {
            return score;
        }

        public String getCategory() {
            return category;
        }

        public String getMessage() {
            return message;
        }
    }
}
