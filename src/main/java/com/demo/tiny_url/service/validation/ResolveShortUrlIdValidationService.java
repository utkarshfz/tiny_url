package com.demo.tiny_url.service.validation;

public interface ResolveShortUrlIdValidationService {

    /**
     * Validates resolve short url parameters.
     * @param id short url id
     */
    void validate(String id);
}
