package com.password.controller;

import com.password.domain.ai.creator.AIPasswordCreatorDecorator;
import com.password.domain.ai.validator.AIPasswordValidatorDecorator;
import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;
import com.password.model.ValidateRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AiPasswordApiImplTest {

    @Mock
    private AIPasswordValidatorDecorator aiPasswordValidatorAdapter;

    @Mock
    private AIPasswordCreatorDecorator aiPasswordCreatorAdapter;

    private AiPasswordApiImpl controller;

    @BeforeEach
    void setUp() {
        controller = new AiPasswordApiImpl(aiPasswordValidatorAdapter, aiPasswordCreatorAdapter);
    }

    @Test
    void generate_ShouldReturnOkResponse() {
        // Arrange
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.VALID);
        expectedResponse.setMessage("Password generated successfully");
        expectedResponse.setPassword("SecurePass123!");

        when(aiPasswordCreatorAdapter.generateAndValidatePassword()).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.generate();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(aiPasswordCreatorAdapter, times(1)).generateAndValidatePassword();
    }

    @Test
    void validate_ValidPassword_ShouldReturnOkResponse() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("SecurePass123!");
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.VALID);
        expectedResponse.setMessage("Awesome password, bro!");
        expectedResponse.setPassword("SecurePass123!");

        when(aiPasswordValidatorAdapter.validatePassword(anyString())).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(aiPasswordValidatorAdapter, times(1)).validatePassword("SecurePass123!");
    }

    @Test
    void validate_InvalidPassword_ShouldReturnOkResponse() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("weak");
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.INVALID);
        expectedResponse.setMessage("Password too weak, my friend!");
        expectedResponse.setPassword("weak");

        when(aiPasswordValidatorAdapter.validatePassword(anyString())).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(aiPasswordValidatorAdapter, times(1)).validatePassword("weak");
    }

    @Test
    void validate_NullPassword_ShouldReturnOkResponse() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword(null);
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.INVALID);
        expectedResponse.setMessage("Password is required, dude!");
        expectedResponse.setPassword(null);

        when(aiPasswordValidatorAdapter.validatePassword(null)).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(aiPasswordValidatorAdapter, times(1)).validatePassword(null);
    }

    @Test
    void validate_EmptyPassword_ShouldReturnOkResponse() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("");
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.INVALID);
        expectedResponse.setMessage("Password is empty, my friend!");
        expectedResponse.setPassword("");

        when(aiPasswordValidatorAdapter.validatePassword("")).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(aiPasswordValidatorAdapter, times(1)).validatePassword("");
    }

    @Test
    void validate_ValidatorThrowsException_ShouldReturnServerError() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("SecurePass123!");

        when(aiPasswordValidatorAdapter.validatePassword(anyString()))
                .thenThrow(new RuntimeException("AI service unavailable"));

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertNotNull(response.getBody().get());
        assertEquals(PasswordResponseStatus.ERROR, response.getBody().get().getStatus());

        verify(aiPasswordValidatorAdapter, times(1)).validatePassword("SecurePass123!");
    }

    @Test
    void validate_ComplexPassword_ShouldReturnOkResponse() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("MySuperSecurePassword123!@#");
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.VALID);
        expectedResponse.setMessage("This password is absolutely amazing!");
        expectedResponse.setPassword("MySuperSecurePassword123!@#");

        when(aiPasswordValidatorAdapter.validatePassword(anyString())).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(aiPasswordValidatorAdapter, times(1)).validatePassword("MySuperSecurePassword123!@#");
    }
}
