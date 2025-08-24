package com.password.domain;

import com.password.core.PasswordRules;
import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;
import com.password.model.ValidateRequest;

import jakarta.inject.Singleton;

@Singleton
public class PasswordValidator {

    public PasswordResponse validate(ValidateRequest validateRequest) {
        try {
            var password = validateRequest.getPassword();
            if (password == null) {
                return createPasswordResponse(PasswordResponseStatus.INVALID, PasswordRules.PASSWORD_IS_REQUIRED.getDescription(), password);
            }
            if (password.length() < 8) {
                return createPasswordResponse(PasswordResponseStatus.INVALID, PasswordRules.AT_LEAST_8_CHARACTERS.getDescription(), password);
            }
            if (password.length() > 128) {
                return createPasswordResponse(PasswordResponseStatus.INVALID, PasswordRules.AT_MOST_128_CHARACTERS.getDescription(), password);
            }
            if (!isValidPassword(password)) {
                return createPasswordResponse(PasswordResponseStatus.INVALID, PasswordRules.AT_LEAST_RULES.getDescription(), password);
            }

            return createPasswordResponse(PasswordResponseStatus.VALID, PasswordRules.PASSWORD_IS_VALID.getDescription(), password);
        } catch (Exception exception) {
            return createPasswordResponse(PasswordResponseStatus.VALID, exception.getMessage(), null);
        }
    }

    private PasswordResponse createPasswordResponse(PasswordResponseStatus status, String message, String password) {
        var response = new PasswordResponse();
        response.setStatus(status);
        response.setMessage(message);
        response.setPassword(password);

        return response;
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
