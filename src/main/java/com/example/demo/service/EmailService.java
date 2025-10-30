package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailService {
    // Very basic email regex — sufficient for simple validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public EmailResult validate(String email) {
        if (email == null) return new EmailResult(false, "Email is null");
        String trimmed = email.trim();
        if (trimmed.isEmpty()) return new EmailResult(false, "Email is empty");
        boolean ok = EMAIL_PATTERN.matcher(trimmed).matches();
        if (ok) return new EmailResult(true, "Looks valid");
        return new EmailResult(false, "Does not match a basic email pattern");
    }

    public static class EmailResult {
        private final boolean valid;
        private final String message;

        public EmailResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }
    }
}
