package com.demo.tiny_url.service.validation;

import com.demo.tiny_url.model.CreateShortUrlRequest;

public interface CreateShortUrlValidationService {
    void validate(CreateShortUrlRequest request);
}
