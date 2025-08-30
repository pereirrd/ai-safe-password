package com.password.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import com.password.api.AiPasswordApi;
import com.password.domain.ai.AIPasswordValidatorAdapter;
import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;
import com.password.model.ValidateRequest;

import core.HttpResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AiPasswordApiImpl implements AiPasswordApi {

    private final AIPasswordValidatorAdapter aiPasswordValidatorAdapter;

    @Override
    public HttpResponse<PasswordResponse> generate() {
        log.info("Generating AI password");
        return HttpResponse.ok(null);
    }

    @Override
    public HttpResponse<PasswordResponse> validate(@Valid ValidateRequest validateRequest) {
        log.info("Validating password with AI");
        var password = validateRequest.getPassword();

        try {
            var passwordResponse = aiPasswordValidatorAdapter.validatePassword(password);
            log.info("AI Password validation result: {}", passwordResponse);

            return HttpResponse.ok(passwordResponse);
        } catch (Exception exception) {
            log.error("Error during password validation", exception);

            var errorResponse = HttpResponseUtils.createPasswordResponse(
                    "invalid - Sorry, the AI validator is having issues right now!",
                    password, PasswordResponseStatus.ERROR);

            return HttpResponse.serverError(errorResponse);
        }
    }
}
