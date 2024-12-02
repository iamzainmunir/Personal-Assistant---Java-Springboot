package com.ztech.taskservice.Controller;

import com.ztech.taskservice.Entity.Task;
import com.ztech.taskservice.Model.CustomUserDetails;
import com.ztech.taskservice.Service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(
            summary = "Get tasks for the authenticated user",
            description = "Returns a list of tasks for the currently authenticated user. Admins can view all tasks.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of tasks for the authenticated user"),
                    @ApiResponse(responseCode = "403", description = "Forbidden if access is denied"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized if token is invalid")
            })
    public List<Task> getTasks(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return taskService.getAllTasks(); // Admin can view all tasks
        }
        return taskService.getTasksForUser(authentication.getName()); // User can view only their tasks
    }

    @PostMapping
    @Operation(
            summary = "Create a new task for the authenticated user",
            description = "Creates a new task for the authenticated user and assigns it to them.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task successfully created"),
                    @ApiResponse(responseCode = "400", description = "Bad request if the task data is invalid"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized if token is invalid")
            })
    public Task createTask(
            @Parameter(description = "Task data to be created", required = true) @RequestBody Task task,
            Authentication authentication) {
        task.setUsername(authentication.getName()); // Associate task with the current user
        return taskService.createTask(task);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a task",
            description = "Deletes a task by its ID. Only admins can delete tasks.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Task successfully deleted"),
                    @ApiResponse(responseCode = "403", description = "Forbidden if access is denied"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized if token is invalid")
            })
    public ResponseEntity<?> deleteTask(@Parameter(description = "ID of the task to delete", required = true)
                                        @PathVariable Long id, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            taskService.deleteTask(id); // Admin can delete any task
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Task successfully deleted
        } else {
            // Return a structured error response for forbidden access
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access Denied: Only admins can delete tasks.");
        }
    }
}
