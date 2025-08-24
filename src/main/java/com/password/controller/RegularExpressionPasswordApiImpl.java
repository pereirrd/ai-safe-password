package com.password.controller;

import com.password.api.RegularExpressionPasswordApi;
import com.password.model.ValidateRequest;
import io.micronaut.core.annotation.Nullable;
import jakarta.validation.Valid;

public class RegularExpressionPasswordApiImpl implements RegularExpressionPasswordApi {

    @Override
    public String validate(@Nullable @Valid ValidateRequest validateRequest) {
        return "Hello, World!";
    }
}
