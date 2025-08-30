package com.password.domain.ai.creator;

import dev.langchain4j.service.SystemMessage;
import io.micronaut.langchain4j.annotation.AiService;

@AiService
public interface AIPasswordCreator {

    @SystemMessage("""
                You are a good creator of passwords.
                The password rules are, these rules are mandatory but you can add other rules if you want:
                - At least one uppercase letter.
                - At least one lowercase letter.
                - At least one number.
                - At least one special character.
                - At least 8 characters long.
                - At most 12 characters long.
                Answer only the password, no other text.
            """)
    String generate(String userMessage);
}
