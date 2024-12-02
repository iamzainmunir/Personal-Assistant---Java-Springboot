//package com.ztech.nlpservice.Config;
//
//import com.ztech.nlpservice.Model.ResponseFormat;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConfigurationProperties(prefix = "spring.ai.openai.chat.options")
//public class OpenAiChatOptions {
//
//    private ResponseFormat responseFormat;
//    private double temperature;
//    private int maxTokens;
//    private double topP;
//
//    // Getters and setters
//    public ResponseFormat getResponseFormat() {
//        return responseFormat;
//    }
//
//    public void setResponseFormat(ResponseFormat responseFormat) {
//        this.responseFormat = responseFormat;
//    }
//
//    public double getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(double temperature) {
//        this.temperature = temperature;
//    }
//
//    public int getMaxTokens() {
//        return maxTokens;
//    }
//
//    public void setMaxTokens(int maxTokens) {
//        this.maxTokens = maxTokens;
//    }
//
//    public double getTopP() {
//        return topP;
//    }
//
//    public void setTopP(double topP) {
//        this.topP = topP;
//    }
//}
//
