package com.example.demo;

import com.example.demo.service.PasswordService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordServiceTests {

    private final PasswordService svc = new PasswordService();

    @Test
    void nullPassword_isWeak() {
        PasswordService.PasswordResult r = svc.evaluate(null);
        assertThat(r.getScore()).isLessThan(40);
        assertThat(r.getCategory()).isEqualTo("Weak");
    }

    @Test
    void commonPassword_detected() {
        PasswordService.PasswordResult r = svc.evaluate("password123");
        assertThat(r.getScore()).isLessThan(10);
        assertThat(r.getCategory()).isEqualTo("Weak");
    }

    @Test
    void strongPassword_highScore() {
        PasswordService.PasswordResult r = svc.evaluate("S3cure!Passw0rd#2025");
        assertThat(r.getScore()).isGreaterThanOrEqualTo(70);
        assertThat(r.getCategory()).isEqualTo("Strong");
    }

    @Test
    void emptyPassword_isWeak() {
        PasswordService.PasswordResult r = svc.evaluate("");
        assertThat(r.getScore()).isLessThan(40);
        assertThat(r.getCategory()).isEqualTo("Weak");
    }

    @Test
    void shortPassword_lowScore() {
        PasswordService.PasswordResult r = svc.evaluate("Ab1!");
        // short but has many character classes — not 'Strong' but still scores moderately
        assertThat(r.getScore()).isLessThan(70);
    }

    @Test
    void onlyDigits_lowScore() {
        PasswordService.PasswordResult r = svc.evaluate("1234567890");
        assertThat(r.getScore()).isLessThan(40);
        assertThat(r.getCategory()).isEqualTo("Weak");
    }

    @Test
    void onlySymbols_lowScore() {
        PasswordService.PasswordResult r = svc.evaluate("!@#$%^&*");
        assertThat(r.getScore()).isLessThan(40);
    }

    @Test
    void repeatedSequence_penaltyApplied() {
        // three repeated chars should trigger a -10 penalty in our logic
        PasswordService.PasswordResult r = svc.evaluate("AAAaaa111!!!");
        // repeated sequences apply a penalty; expect score to be below the no-penalty maximum
        assertThat(r.getScore()).isLessThan(90);
    }

    @Test
    void commonSubstring_caseInsensitive() {
        PasswordService.PasswordResult r = svc.evaluate("MyPassWordIs123");
        // contains "password" ignoring case, should be flagged very weak
        assertThat(r.getScore()).isLessThan(10);
        assertThat(r.getCategory()).isEqualTo("Weak");
    }

    @Test
    void veryLongPassword_scoresWell() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 40; i++) sb.append((char) ('a' + (i % 26)));
        sb.append("A1!");
        PasswordService.PasswordResult r = svc.evaluate(sb.toString());
        assertThat(r.getScore()).isGreaterThanOrEqualTo(70);
        assertThat(r.getCategory()).isEqualTo("Strong");
    }
}
