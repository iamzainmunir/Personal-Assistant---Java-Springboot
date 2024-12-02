package com.ztech.nlpservice.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskGenerationResponse {

    private String title;

    private String description;

    private LocalDateTime dueDate;

    private Boolean success;

    private String reason;
}
