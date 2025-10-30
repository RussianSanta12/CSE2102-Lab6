package com.example.demo.controller;

import com.example.demo.dto.PasswordRequest;
import com.example.demo.dto.PasswordResponse;
import com.example.demo.service.PasswordService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping(value = "/quality", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PasswordResponse quality(@RequestBody PasswordRequest request) {
        PasswordService.PasswordResult r = passwordService.evaluate(request == null ? null : request.getPassword());
        return new PasswordResponse(r.getScore(), r.getCategory(), r.getMessage());
    }
}
