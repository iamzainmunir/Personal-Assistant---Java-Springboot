package com.ztech.nlpservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponse {

    @Schema(description = "Error message explaining what went wrong", example = "Error message")
    private String message;

    @Schema(description = "Error code associated with the failure", example = "500")
    private String errorCode;

    // Constructor
    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
