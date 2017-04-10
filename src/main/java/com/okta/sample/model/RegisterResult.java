package com.okta.sample.model;

public class RegisterResult {

    public enum Status {
        SUCCESS, FAIL
    }

    private Status status;
    private String message;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
