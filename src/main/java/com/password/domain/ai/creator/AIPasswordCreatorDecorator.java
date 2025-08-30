package com.password.domain.ai.creator;

import com.password.domain.ai.validator.AIPasswordValidator;
import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;

import core.HttpResponseUtils;
import io.micronaut.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Bean
@Slf4j
@RequiredArgsConstructor
public class AIPasswordCreatorDecorator {

    private final AIPasswordCreator aiPasswordCreator;
    private final AIPasswordValidator aiPasswordValidator;

    /**
     * Generates a password using AI and validates it before returning
     * Recursively tries until a VALID password is generated
     * 
     * @return PasswordResponse with the generated password and validation result
     */
    public PasswordResponse generateAndValidatePassword() {
        return generateAndValidatePasswordRecursive(1);
    }

    /**
     * Recursive method to generate and validate password until VALID status is
     * achieved
     * 
     * @param attemptNumber Current attempt number
     * @return PasswordResponse with the generated password and validation result
     */
    private PasswordResponse generateAndValidatePasswordRecursive(int attemptNumber) {
        try {
            log.info("Password generation attempt #{}", attemptNumber);

            // Generate password using AI
            var generatedPassword = aiPasswordCreator.generate("Generate a password");
            log.debug("Generated password (attempt #{}): {}", attemptNumber,
                    generatedPassword != null ? "***" : "null");

            if (generatedPassword == null || generatedPassword.trim().isEmpty()) {
                log.warn("AI password creator returned null or empty password on attempt #{}", attemptNumber);
                throw new RuntimeException("AI password creator returned null or empty password");
            }

            // Validate the generated password
            var validationResult = aiPasswordValidator.validate(generatedPassword);
            log.info("Password validation result (attempt #{}): {}", attemptNumber, validationResult);

            // Parse validation result and create response
            var response = HttpResponseUtils.createPasswordResponse(validationResult, generatedPassword);

            // Check if the password is valid
            if (response.getStatus() == PasswordResponseStatus.VALID) {
                log.info("Password generation and validation completed successfully on attempt #{}", attemptNumber);
                return response;
            } else {
                log.warn("Generated password is invalid on attempt #{}, retrying...", attemptNumber);
                // Recursive call to try again
                return generateAndValidatePasswordRecursive(attemptNumber + 1);
            }
        } catch (Exception exception) {
            log.error("Error during password generation and validation on attempt #{}", attemptNumber, exception);
            throw new RuntimeException(exception);
        }
    }
}
