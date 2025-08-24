package com.password;

import io.micronaut.http.annotation.*;

@Controller("/ai-safe-password")
public class AiSafePasswordController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}