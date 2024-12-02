package com.ztech.nlpservice.Service;

import com.ztech.nlpservice.DTO.TaskGenerationResponse;
import com.ztech.nlpservice.Entity.NLP;
import com.ztech.nlpservice.Repository.NLPRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NLPService {

    @Autowired
    private NLPRepository nlpRepository;

    @Autowired
    private ChatModel chatModel;
    BeanOutputConverter<TaskGenerationResponse> beanOutputConverter =
            new BeanOutputConverter<>(TaskGenerationResponse.class);

    public TaskGenerationResponse processQuery(String queryText, String username) {
        String format = this.beanOutputConverter.getFormat();

        String template = """
            You are a task management assistant. Your goal is to generate a response for {queryText} in JSON format matching the following object structure: {format}.
            For description: Generate a short description that includes the title (if provided) and the due date (in Pakistan standard format) to indicate when the task is due.
            For due date: Extract the date and time in ISO 8601 format (e.g., 2024-11-21T22:00:00.000Z).
            For success: Set to false if the title or due date is not provided by the user.
            For reason: Provide the reason why success is false (only applicable if success is false), or set it to null if success is true.
            If sufficient information is provided, create the task with a success value of true. If the provided information is incomplete or invalid, set success to false and provide a clear reason for why the task creation failed. Always respond in JSON format.
        """;

        Generation generation = chatModel.call(
                new PromptTemplate(template, Map.of("queryText", queryText, "format", format)).create()).getResult();

        TaskGenerationResponse task = this.beanOutputConverter.convert(generation.getOutput().getContent());

        // Save the processed query to MongoDB
        NLP nlpQuery = new NLP();
        nlpQuery.setQueryText(queryText);
        nlpQuery.setTask(task);
        nlpQuery.setUsername(username);
        nlpRepository.save(nlpQuery);

        return task;
    }
}
