package com.password.controller;

import com.password.api.AiPasswordApi;
import com.password.model.ValidateRequest;
import io.micronaut.core.annotation.Nullable;
import jakarta.validation.Valid;

public class AiPasswordApiImpl implements AiPasswordApi {

    @Override
    public String generate() {
        return "Hello, World!";
    }

    @Override
    public String validate(@Nullable @Valid ValidateRequest validateRequest) {
        return "Hello, World!";
    }
}
