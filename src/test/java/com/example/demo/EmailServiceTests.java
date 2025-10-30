package com.example.demo;

import com.example.demo.service.EmailService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailServiceTests {

    private final EmailService svc = new EmailService();

    @Test
    void nullEmail_invalid() {
        EmailService.EmailResult r = svc.validate(null);
        assertThat(r.isValid()).isFalse();
    }

    @Test
    void simpleValidEmail_ok() {
        EmailService.EmailResult r = svc.validate("user@example.com");
        assertThat(r.isValid()).isTrue();
    }

    @Test
    void invalidEmail_fail() {
        EmailService.EmailResult r = svc.validate("not-an-email@.com");
        assertThat(r.isValid()).isFalse();
    }

    @Test
    void emptyString_invalid() {
        EmailService.EmailResult r = svc.validate("");
        assertThat(r.isValid()).isFalse();
    }

    @Test
    void whitespaceTrim_valid() {
        EmailService.EmailResult r = svc.validate("  user@example.com  ");
        assertThat(r.isValid()).isTrue();
    }

    @Test
    void missingAt_invalid() {
        EmailService.EmailResult r = svc.validate("userexample.com");
        assertThat(r.isValid()).isFalse();
    }

    @Test
    void multipleAt_invalid() {
        EmailService.EmailResult r = svc.validate("a@b@c.com");
        assertThat(r.isValid()).isFalse();
    }

    @Test
    void noTld_invalid() {
        EmailService.EmailResult r = svc.validate("user@localhost");
        assertThat(r.isValid()).isFalse();
    }

    @Test
    void subdomain_valid() {
        EmailService.EmailResult r = svc.validate("user@mail.example.co.uk");
        assertThat(r.isValid()).isTrue();
    }

    @Test
    void plusInLocal_partValid() {
        EmailService.EmailResult r = svc.validate("first+label@example.com");
        assertThat(r.isValid()).isTrue();
    }
}
