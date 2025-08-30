package com.password.domain.expression;

import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;

import core.HttpResponseUtils;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class PasswordValidator {

    public PasswordResponse validate(String password) {
        try {
            log.debug("Validating password: {}", password != null ? "***" : "null");

            if (password == null) {
                log.warn("Password validation failed: password is null");
                return HttpResponseUtils.createPasswordResponse(
                        PasswordRules.PASSWORD_IS_REQUIRED.getDescription(), password, PasswordResponseStatus.INVALID);
            }
            if (password.length() < 8) {
                log.warn("Password validation failed: password too short ({} characters)", password.length());
                return HttpResponseUtils.createPasswordResponse(
                        PasswordRules.AT_LEAST_8_CHARACTERS.getDescription(), password, PasswordResponseStatus.INVALID);
            }
            if (password.length() > 128) {
                log.warn("Password validation failed: password too long ({} characters)", password.length());
                return HttpResponseUtils.createPasswordResponse(
                        PasswordRules.AT_MOST_128_CHARACTERS.getDescription(), password,
                        PasswordResponseStatus.INVALID);
            }
            if (!isValidPassword(password)) {
                log.warn("Password validation failed: password does not meet complexity requirements");
                return HttpResponseUtils.createPasswordResponse(
                        PasswordRules.AT_LEAST_RULES.getDescription(), password, PasswordResponseStatus.INVALID);
            }

            log.info("Password validation successful");

            return HttpResponseUtils.createPasswordResponse(
                    PasswordRules.PASSWORD_IS_VALID.getDescription(), password, PasswordResponseStatus.VALID);
        } catch (Exception exception) {
            log.error("Error during password validation", exception);

            throw new RuntimeException(exception);
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
