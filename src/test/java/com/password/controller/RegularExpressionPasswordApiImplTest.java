package com.password.controller;

import com.password.domain.expression.PasswordValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegularExpressionPasswordApiImplTest {

    @Mock
    private PasswordValidator passwordValidator;

    private RegularExpressionPasswordApiImpl controller;

    @BeforeEach
    void setUp() {
        controller = new RegularExpressionPasswordApiImpl(passwordValidator);
    }

    @Test
    void validate_ValidPassword_ShouldReturnOkResponse() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("SecurePass123!");
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.VALID);
        expectedResponse.setMessage("Password is valid");
        expectedResponse.setPassword("SecurePass123!");

        when(passwordValidator.validate(any(String.class))).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(passwordValidator, times(1)).validate(request.getPassword());
    }

    @Test
    void validate_InvalidPassword_ShouldReturnUnprocessableEntity() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("weak");

        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.INVALID);
        expectedResponse.setMessage(
                "Password must contain at least one uppercase letter, at least one lowercase letter, at least one number, at least one special character");
        expectedResponse.setPassword("weak");

        when(passwordValidator.validate(any(String.class))).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(passwordValidator, times(1)).validate(request.getPassword());
    }

    @Test
    void validate_NullPassword_ShouldReturnUnprocessableEntity() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword(null);

        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.INVALID);
        expectedResponse.setMessage("Password is required");
        expectedResponse.setPassword(null);

        when(passwordValidator.validate(any(String.class))).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());

        verify(passwordValidator, times(1)).validate(request.getPassword());
    }

    @Test
    void validate_ComplexValidPassword_ShouldReturnOkResponse() {
        // Arrange
        ValidateRequest request = new ValidateRequest();
        request.setPassword("MySuperSecurePassword123!@#");
        PasswordResponse expectedResponse = new PasswordResponse();
        expectedResponse.setStatus(PasswordResponseStatus.VALID);
        expectedResponse.setMessage("Password is valid");
        expectedResponse.setPassword("MySuperSecurePassword123!@#");

        when(passwordValidator.validate(any(String.class))).thenReturn(expectedResponse);

        // Act
        HttpResponse<PasswordResponse> response = controller.validate(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.getBody().get());

        verify(passwordValidator, times(1)).validate(request.getPassword());
    }
}
