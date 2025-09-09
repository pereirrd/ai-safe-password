package com.password.domain.ai.validator;

import com.password.core.HttpResponseUtils;
import com.password.model.PasswordResponse;

import io.micronaut.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Bean
@Slf4j
@RequiredArgsConstructor
public class AIPasswordValidatorDecorator {

    private final AIPasswordValidator aiPasswordValidator;

    public PasswordResponse validatePassword(String userPassword) {
        log.debug("Validating password using AI validator: {}", userPassword != null ? "***" : "null");

        try {
            var result = aiPasswordValidator.validate(userPassword);
            log.info("AI validation result: {}", result);
            return HttpResponseUtils.createPasswordResponse(result, userPassword);
        } catch (Exception exception) {
            log.error("Error during AI password validation", exception);
            throw new RuntimeException(exception);
        }
    }
}
