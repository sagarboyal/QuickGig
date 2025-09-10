package com.main.quickgig.payload.response;


public record MessageResponse(String message) {
    public static MessageResponse build(String message) {
       return new MessageResponse(message);
    }
}