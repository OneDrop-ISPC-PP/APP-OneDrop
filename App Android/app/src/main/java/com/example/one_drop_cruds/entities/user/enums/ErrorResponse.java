package com.example.one_drop_cruds.entities.user.enums;

public class ErrorResponse {
    private String message;
    private int internalCode;

    public ErrorResponse(String message, int internalCode) {
        this.message = message;
        this.internalCode = internalCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(int internalCode) {
        this.internalCode = internalCode;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", internalCode=" + internalCode +
                '}';
    }
}
