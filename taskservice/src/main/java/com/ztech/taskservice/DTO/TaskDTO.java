package com.ztech.taskservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Task details")
public class TaskDTO {

    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    @Schema(description = "Name of the task", example = "Finish report")
    private String name;

    @Schema(description = "Description of the task", example = "Complete the annual report by the end of the week")
    private String description;

    @Schema(description = "Category of the task", example = "Work")
    private String category;

    @Schema(description = "Status of the task", example = "In Progress")
    private String status;

    @Schema(description = "Priority of the task", example = "High")
    private String priority;

    @Schema(description = "Due date of the task", example = "2024-11-22")
    private String dueDate;

    // Getters and Setters
}
