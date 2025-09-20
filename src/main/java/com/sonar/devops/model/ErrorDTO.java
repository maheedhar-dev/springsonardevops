package com.sonar.devops.model;

public class ErrorDTO {
    private String message;
    private String code;

    public ErrorDTO() {
    }

    public ErrorDTO(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ErrorDTO{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
