package com.taskmanager.application.dto;

public class AuthResponse {
    public String token;
    public AuthResponse(String token) {
        this.token = token;
    }
}