package com.password.domain.expression;

import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    private PasswordValidator passwordValidator;

    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    void validate_ValidPassword_ShouldReturnValidResponse() {
        // Arrange
        String password = "SecurePass123!";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.VALID, response.getStatus());
        assertEquals("Password is valid", response.getMessage());
        assertEquals("SecurePass123!", response.getPassword());
    }

    @Test
    void validate_NullPassword_ShouldReturnInvalidResponse() {
        // Arrange
        String password = null;

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals("Password is required", response.getMessage());
        assertNull(response.getPassword());
    }

    @Test
    void validate_EmptyPassword_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals("Password must be at least 8 characters long", response.getMessage());
        assertEquals("", response.getPassword());
    }

    @Test
    void validate_PasswordTooShort_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "Short1!";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals("Password must be at least 8 characters long", response.getMessage());
        assertEquals("Short1!", response.getPassword());
    }

    @Test
    void validate_PasswordTooLong_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "a".repeat(129) + "A1!";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals("Password must be less than 128 characters long", response.getMessage());
        assertEquals("a".repeat(129) + "A1!", response.getPassword());
    }

    @Test
    void validate_PasswordWithoutUppercase_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "securepass123!";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals(
                "Password must contain at least one uppercase letter, at least one lowercase letter, at least one number, at least one special character",
                response.getMessage());
        assertEquals("securepass123!", response.getPassword());
    }

    @Test
    void validate_PasswordWithoutLowercase_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "SECUREPASS123!";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals(
                "Password must contain at least one uppercase letter, at least one lowercase letter, at least one number, at least one special character",
                response.getMessage());
        assertEquals("SECUREPASS123!", response.getPassword());
    }

    @Test
    void validate_PasswordWithoutNumber_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "SecurePass!";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals(
                "Password must contain at least one uppercase letter, at least one lowercase letter, at least one number, at least one special character",
                response.getMessage());
        assertEquals("SecurePass!", response.getPassword());
    }

    @Test
    void validate_PasswordWithoutSpecialCharacter_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "SecurePass123";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals(
                "Password must contain at least one uppercase letter, at least one lowercase letter, at least one number, at least one special character",
                response.getMessage());
        assertEquals("SecurePass123", response.getPassword());
    }

    @Test
    void validate_PasswordWithWhitespace_ShouldReturnInvalidResponse() {
        // Arrange
        String password = "   ";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.INVALID, response.getStatus());
        assertEquals("Password must be at least 8 characters long", response.getMessage());
        assertEquals("   ", response.getPassword());
    }

    @Test
    void validate_PasswordExactly8Characters_ShouldReturnValidResponse() {
        // Arrange
        String password = "Abc123!@";

        // Act
        PasswordResponse response = passwordValidator.validate(password);

        // Assert
        assertNotNull(response);
        assertEquals(PasswordResponseStatus.VALID, response.getStatus());
        assertEquals("Password is valid", response.getMessage());
        assertEquals("Abc123!@", response.getPassword());
    }
}
