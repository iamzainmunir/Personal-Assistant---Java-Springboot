package com.ztech.nlpservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SuccessResponse {

    @Schema(description = "The original query text provided by the user", example = "Add a meeting with John tomorrow at 10 AM")
    private String queryText;

    @Schema(description = "The processed text after NLP analysis", example = "Meeting with John scheduled for tomorrow at 10 AM")
    private TaskGenerationResponse task;

    @Schema(description = "The username of the authenticated user", example = "john_doe")
    private String userName;
}
