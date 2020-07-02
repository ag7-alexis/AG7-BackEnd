package com.example.demo.api.error;


import java.util.Date;

public class ErrorMessage {
    private Date timestamp;
    private String localization;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String localization, String message) {
        this.timestamp = new Date();
        this.localization = localization;
        this.message = message;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
