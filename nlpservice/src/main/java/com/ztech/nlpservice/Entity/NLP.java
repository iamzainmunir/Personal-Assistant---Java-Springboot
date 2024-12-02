package com.ztech.nlpservice.Entity;

import com.ztech.nlpservice.DTO.TaskGenerationResponse;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nlp_queries")
@Data
public class NLP {

    @Id
    private ObjectId id;  // MongoDB uses String for id
    private String queryText;
    private TaskGenerationResponse task;
    private String username;

    // Getters and Setters
}