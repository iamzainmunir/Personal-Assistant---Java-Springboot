package com.ztech.nlpservice.Controller;

import com.ztech.nlpservice.DTO.ErrorResponse;
import com.ztech.nlpservice.DTO.NLPQuery;
import com.ztech.nlpservice.DTO.SuccessResponse;
import com.ztech.nlpservice.DTO.TaskGenerationResponse;
import com.ztech.nlpservice.Service.NLPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ask")
@Tag(name = "NLP Service", description = "API for creating task using AI")
public class NLPController {

    @Autowired
    private NLPService nlpService;

    @PostMapping("/ai")
    @Operation(
            summary = "Create task using AI",
            description = "Return an AI generated task.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AI generated task for the authenticated user"),
                    @ApiResponse(responseCode = "403", description = "Forbidden if access is denied"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized if token is invalid")
            })
    public ResponseEntity<?> processQuery(@RequestBody NLPQuery nlpQuery, Authentication authentication) {
        try {
            String username = authentication.getName();
            TaskGenerationResponse task = nlpService.processQuery(nlpQuery.getQueryText(), username);

            SuccessResponse successResponse = new SuccessResponse();
            successResponse.setQueryText(nlpQuery.getQueryText());
            successResponse.setTask(task);
            successResponse.setUserName(username);

            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse("An unexpected error occurred", "500")
            );
        }

    }
}