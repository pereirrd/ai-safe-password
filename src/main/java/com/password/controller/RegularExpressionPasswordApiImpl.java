package com.password.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import core.HttpResponseUtils;
import com.password.api.RegularExpressionPasswordApi;
import com.password.domain.expression.PasswordValidator;
import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;
import com.password.model.ValidateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegularExpressionPasswordApiImpl implements RegularExpressionPasswordApi {

    private final PasswordValidator passwordValidator;

    @Override
    public HttpResponse<PasswordResponse> validate(@Valid ValidateRequest validateRequest) {
        log.info("Validating password with regular expression");
        var password = validateRequest.getPassword();

        try {
            var passwordResponse = passwordValidator.validate(password);

            return HttpResponse.ok(passwordResponse);
        } catch (Exception exception) {
            log.error("Error during password validation", exception);

            var errorResponse = HttpResponseUtils.createPasswordResponse(
                    "invalid - Sorry, the regular expression validator is having issues right now!",
                    password, PasswordResponseStatus.ERROR);

            return HttpResponse.serverError(errorResponse);
        }
    }
}
