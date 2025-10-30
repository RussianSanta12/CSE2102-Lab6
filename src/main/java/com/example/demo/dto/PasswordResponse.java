package com.example.demo.dto;

public class PasswordResponse {
    private int score; // 0-100
    private String category; // Weak/Moderate/Strong
    private String message;

    public PasswordResponse() {}

    public PasswordResponse(int score, String category, String message) {
        this.score = score;
        this.category = category;
        this.message = message;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
