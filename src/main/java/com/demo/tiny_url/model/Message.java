package com.demo.tiny_url.model;


import org.slf4j.helpers.MessageFormatter;


public enum Message {
    RESOURCE_ALREADY_EXISTS("Custom url id {} not available"),
    URL_NOT_EXISTS("URL does not exits"),
    CUSTOM_URL_EXCEEDS_ALLOCATED_LIMIT("Custom URL length exceeds allocated limit : {}"),
    URL_EXCEEDS_ALLOCATED_LIMIT("URL length exceeds allocated limit : {}"),
    SHORT_URL_ID_EXCEEDS_ALLOCATED_LIMIT("Short URL ID length exceeds allocated limit : {}"),
    ONLY_ALPHA_NUMERIC("Custom URL contains invalid characters only alphabets and numbers allowed"),
    INPUT_NOT_BLANK("Input must not be blank."),
    COUNTERS_EXHAUSTED("[CRITICAL] Counters have been exhausted!"),
    SOMETHING_WENT_WRONG("Something went wrong");


    private final String messagePlaceholder;
    Message(String messagePlaceholder){
        this.messagePlaceholder = messagePlaceholder;
    }

    public String getMessage(Object ...args) {
        return MessageFormatter.arrayFormat(this.messagePlaceholder, args).getMessage();
    }



}
