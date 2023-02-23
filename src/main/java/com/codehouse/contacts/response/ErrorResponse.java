package com.codehouse.contacts.response;

public record ErrorResponse(int code,
                            String message,
                            String cause) {
    public static ErrorResponse of(String message) {
        return new ErrorResponse(501, message, "unknown");
    }

    public static ErrorResponse of(int code, String message, String cause) {
        return new ErrorResponse(code, message, cause);
    }

    public static ErrorResponse of(int code, String message) {
        return new ErrorResponse(code, message,null);
    }

}
