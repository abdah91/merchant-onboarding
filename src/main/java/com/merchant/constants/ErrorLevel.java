package com.merchant.constants;

public enum ErrorLevel {
    INFO("info"),

    WARNING("warning"),

    ERROR("error");

    private String status;
    private ErrorLevel(String status) {
        this.status=status;
    }

    public String getValue() {
        return this.status;
    }
}
