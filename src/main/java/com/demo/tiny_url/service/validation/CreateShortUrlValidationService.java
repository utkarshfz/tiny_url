package com.demo.tiny_url.service.validation;

import com.demo.tiny_url.model.CreateShortUrlRequest;

public interface CreateShortUrlValidationService {

    /**
     * Validates create short url request.
     * @param request create short url request.
     */
    void validate(CreateShortUrlRequest request);
}
