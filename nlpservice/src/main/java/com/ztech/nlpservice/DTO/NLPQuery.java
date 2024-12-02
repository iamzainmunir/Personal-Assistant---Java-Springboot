package com.ztech.nlpservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NLPQuery {

    @Schema(description = "The original query text provided by the user", example = "Add a meeting with John tomorrow at 10 AM")
    private String queryText;
}
