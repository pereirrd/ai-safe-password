package com.password.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;

import com.password.api.RegularExpressionPasswordApi;
import com.password.domain.PasswordValidator;
import com.password.model.PasswordResponse;
import com.password.model.ValidateRequest;
import jakarta.validation.Valid;

@Controller
public class RegularExpressionPasswordApiImpl implements RegularExpressionPasswordApi {

    private final PasswordValidator passwordValidator;

    public RegularExpressionPasswordApiImpl(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @Override
    public HttpResponse<PasswordResponse> validate(@Valid ValidateRequest validateRequest) {
        try {
            var passwordResponse = passwordValidator.validate(validateRequest);
    
            return createHttpResponse(passwordResponse);
        } catch (Exception exception) {
            return createHttpResponse(null);
        }
    }

    private HttpResponse<PasswordResponse> createHttpResponse(PasswordResponse passwordResponse) {
        var status = passwordResponse.getStatus();

        switch (status) {
            case VALID:
                return HttpResponse.ok(passwordResponse);
            case INVALID:
                return HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                   .body(passwordResponse);
            default:
                return HttpResponse.serverError();
        }
    }
}
