package com.password.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import com.password.api.AiPasswordApi;
import com.password.model.PasswordResponse;
import com.password.model.ValidateRequest;
import jakarta.validation.Valid;

@Controller
public class AiPasswordApiImpl implements AiPasswordApi {

    @Override
    public HttpResponse<PasswordResponse> generate() {
        return HttpResponse.ok(null);
    }

    @Override
    public HttpResponse<PasswordResponse> validate(@Valid ValidateRequest validateRequest) {
        return HttpResponse.ok(null);
    }
}
