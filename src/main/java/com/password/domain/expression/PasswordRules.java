package com.password.domain.expression;

public enum PasswordRules {
    AT_LEAST_RULES("Password must contain at least one uppercase letter, at least one lowercase letter, at least one number, at least one special character"),
    AT_LEAST_8_CHARACTERS("Password must be at least 8 characters long"),
    AT_MOST_128_CHARACTERS("Password must be less than 128 characters long"),
    PASSWORD_IS_REQUIRED("Password is required"),
    PASSWORD_IS_VALID("Password is valid");

    private final String description;

    PasswordRules(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
