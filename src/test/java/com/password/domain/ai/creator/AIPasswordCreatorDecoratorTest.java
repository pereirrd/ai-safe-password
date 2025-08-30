package com.password.domain.ai.creator;

import com.password.domain.ai.validator.AIPasswordValidator;
import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AIPasswordCreatorDecoratorTest {

    @Mock
    private AIPasswordCreator aiPasswordCreator;

    @Mock
    private AIPasswordValidator aiPasswordValidator;

    private AIPasswordCreatorDecorator decorator;

    @BeforeEach
    void setUp() {
        decorator = new AIPasswordCreatorDecorator(aiPasswordCreator, aiPasswordValidator);
    }

    @Test
    void generateAndValidatePassword_SuccessOnFirstAttempt() {
        // Arrange
        String generatedPassword = "SecurePass123!";
        String validationResult = "valid;Awesome password, bro!";

        when(aiPasswordCreator.generate(anyString())).thenReturn(generatedPassword);
        when(aiPasswordValidator.validate(generatedPassword)).thenReturn(validationResult);

        // Act
        PasswordResponse result = decorator.generateAndValidatePassword();

        // Assert
        assertNotNull(result);
        assertEquals(PasswordResponseStatus.VALID, result.getStatus());
        assertEquals("Awesome password, bro!", result.getMessage());
        assertEquals(generatedPassword, result.getPassword());

        verify(aiPasswordCreator, times(1)).generate(anyString());
        verify(aiPasswordValidator, times(1)).validate(generatedPassword);
    }

    @Test
    void generateAndValidatePassword_SuccessOnSecondAttempt() {
        // Arrange
        String firstPassword = "WeakPass";
        String secondPassword = "SecurePass123!";
        String firstValidationResult = "invalid;Password too weak, my friend!";
        String secondValidationResult = "valid;Awesome password, bro!";

        when(aiPasswordCreator.generate(anyString()))
                .thenReturn(firstPassword)
                .thenReturn(secondPassword);
        when(aiPasswordValidator.validate(firstPassword)).thenReturn(firstValidationResult);
        when(aiPasswordValidator.validate(secondPassword)).thenReturn(secondValidationResult);

        // Act
        PasswordResponse result = decorator.generateAndValidatePassword();

        // Assert
        assertNotNull(result);
        assertEquals(PasswordResponseStatus.VALID, result.getStatus());
        assertEquals("Awesome password, bro!", result.getMessage());
        assertEquals(secondPassword, result.getPassword());

        verify(aiPasswordCreator, times(2)).generate(anyString());
        verify(aiPasswordValidator, times(2)).validate(anyString());
    }

    @Test
    void generateAndValidatePassword_SuccessOnThirdAttempt() {
        // Arrange
        String firstPassword = "WeakPass";
        String secondPassword = "StillWeak";
        String thirdPassword = "SecurePass123!";
        String firstValidationResult = "invalid;Password too weak, my friend!";
        String secondValidationResult = "invalid;Still not good enough!";
        String thirdValidationResult = "valid;Perfect password!";

        when(aiPasswordCreator.generate(anyString()))
                .thenReturn(firstPassword)
                .thenReturn(secondPassword)
                .thenReturn(thirdPassword);
        when(aiPasswordValidator.validate(firstPassword)).thenReturn(firstValidationResult);
        when(aiPasswordValidator.validate(secondPassword)).thenReturn(secondValidationResult);
        when(aiPasswordValidator.validate(thirdPassword)).thenReturn(thirdValidationResult);

        // Act
        PasswordResponse result = decorator.generateAndValidatePassword();

        // Assert
        assertNotNull(result);
        assertEquals(PasswordResponseStatus.VALID, result.getStatus());
        assertEquals("Perfect password!", result.getMessage());
        assertEquals(thirdPassword, result.getPassword());

        verify(aiPasswordCreator, times(3)).generate(anyString());
        verify(aiPasswordValidator, times(3)).validate(anyString());
    }

    @Test
    void generateAndValidatePassword_ValidatorThrowsException() {
        // Arrange
        String generatedPassword = "SecurePass123!";
        when(aiPasswordCreator.generate(anyString())).thenReturn(generatedPassword);
        when(aiPasswordValidator.validate(generatedPassword))
                .thenThrow(new RuntimeException("AI service unavailable"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> decorator.generateAndValidatePassword());

        assertTrue(exception.getMessage().contains("AI service unavailable"));
        verify(aiPasswordCreator, times(1)).generate(anyString());
        verify(aiPasswordValidator, times(1)).validate(generatedPassword);
    }

    @Test
    void generateAndValidatePassword_CreatorThrowsException() {
        // Arrange
        when(aiPasswordCreator.generate(anyString()))
                .thenThrow(new RuntimeException("AI creator service down"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> decorator.generateAndValidatePassword());

        assertTrue(exception.getMessage().contains("AI creator service down"));
        verify(aiPasswordCreator, times(1)).generate(anyString());
        verify(aiPasswordValidator, never()).validate(anyString());
    }
}
