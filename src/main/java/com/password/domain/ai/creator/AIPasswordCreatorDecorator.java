package com.password.domain.ai.creator;

import com.password.domain.ai.validator.AIPasswordValidator;
import com.password.model.PasswordResponse;
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
     * 
     * @return PasswordResponse with the generated password and validation result
     */
    public PasswordResponse generateAndValidatePassword() {
        try {
            log.info("Starting password generation and validation process");

            // Generate password using AI
            var generatedPassword = aiPasswordCreator.generate("Generate a password");
            log.debug("Generated password: {}", generatedPassword != null ? "***" : "null");

            if (generatedPassword == null || generatedPassword.trim().isEmpty()) {
                log.warn("AI password creator returned null or empty password");
                throw new RuntimeException("AI password creator returned null or empty password");
            }

            // Validate the generated password
            var validationResult = aiPasswordValidator.validate(generatedPassword);
            log.info("Password validation result: {}", validationResult);

            // Parse validation result and create response
            var response = HttpResponseUtils.createPasswordResponse(validationResult, generatedPassword);

            log.info("Password generation and validation completed successfully");
            return response;
        } catch (Exception exception) {
            log.error("Error during password generation and validation", exception);
            throw new RuntimeException(exception);
        }
    }
}
