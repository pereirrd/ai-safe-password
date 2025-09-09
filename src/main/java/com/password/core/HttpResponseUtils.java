package com.password.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.password.model.PasswordResponse;
import com.password.model.PasswordResponseStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpResponseUtils {

    /**
     * Creates a PasswordResponse from a string that contains status and message
     * separated by ";"
     * Expected format: "status;message"
     * 
     * @param messageResponse The string to parse (e.g., "valid;Awesome password,
     *                        bro!")
     * @param password        The password for the response
     * @return PasswordResponse with parsed status and message
     */
    public static PasswordResponse createPasswordResponse(String messageResponse, String password) {
        if (messageResponse == null || messageResponse.trim().isEmpty()) {
            return createErrorResponse("Invalid response format", password);
        }

        var parts = messageResponse.split(";");
        if (parts.length < 2) {
            return createErrorResponse("Response must contain at least status and message", password);
        }

        var statusPart = parts[0].trim().toLowerCase();
        var message = parts[1].trim();

        PasswordResponseStatus status = parseStatus(statusPart);
        if (status == null) {
            return createErrorResponse("Invalid status format: " + statusPart, password);
        }

        var response = new PasswordResponse();
        response.setStatus(status);
        response.setMessage(message);
        response.setPassword(password);

        return response;
    }

    /**
     * Creates a PasswordResponse from a message string and status
     * 
     * @param message  The message for the response
     * @param password The password for the response
     * @param status   The status of the response
     * @return PasswordResponse with the specified status and message
     */
    public static PasswordResponse createPasswordResponse(String message, String password,
            PasswordResponseStatus status) {
        if (message == null) {
            message = "";
        }

        var response = new PasswordResponse();
        response.setStatus(status);
        response.setMessage(message);
        response.setPassword(password);

        return response;
    }

    /**
     * Creates an error PasswordResponse
     * 
     * @param errorMessage The error message
     * @param password     The password for the response
     * @return PasswordResponse with ERROR status
     */
    private static PasswordResponse createErrorResponse(String errorMessage, String password) {
        var response = new PasswordResponse();
        response.setStatus(PasswordResponseStatus.ERROR);
        response.setMessage(errorMessage);
        response.setPassword(password);
        return response;
    }

    /**
     * Parses a status string and returns the corresponding PasswordResponseStatus
     * 
     * @param statusPart The status string to parse
     * @return PasswordResponseStatus or null if invalid
     */
    private static PasswordResponseStatus parseStatus(String statusPart) {
        try {
            if (statusPart.startsWith("valid")) {
                return PasswordResponseStatus.VALID;
            } else if (statusPart.startsWith("invalid")) {
                return PasswordResponseStatus.INVALID;
            } else {
                return PasswordResponseStatus.ERROR;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
