//package com.ztech.nlpservice.Model;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonValue;
//
//public enum ResponseFormat {
//    JSON_OBJECT("json_object"),
//    TEXT("text"),
//    JSON("json");
//
//    private final String value;
//
//    ResponseFormat(String value) {
//        this.value = value;
//    }
//
//    @JsonValue
//    public String getValue() {
//        return value;
//    }
//
//    @JsonCreator
//    public static ResponseFormat fromString(String value) {
//        for (ResponseFormat format : ResponseFormat.values()) {
//            if (format.value.equalsIgnoreCase(value)) {
//                return format;
//            }
//        }
//        throw new IllegalArgumentException("Unknown format: " + value);
//    }
//}
