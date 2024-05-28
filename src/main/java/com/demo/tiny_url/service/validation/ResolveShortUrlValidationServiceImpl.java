package com.demo.tiny_url.service.validation;

import com.demo.tiny_url.exception.model.ValidationException;
import com.demo.tiny_url.model.Message;
import org.springframework.stereotype.Service;

import static com.demo.tiny_url.constants.TinyUrlConstants.CUSTOM_URL_ID_LENGTH_LIMIT;

@Service
public class ResolveShortUrlValidationServiceImpl implements ResolveShortUrlIdValidationService{
    @Override
    public void validate(String id) {
        validateShortUrlIdLength(id);
    }

    private void validateShortUrlIdLength(String id) {
        if(id.length() > CUSTOM_URL_ID_LENGTH_LIMIT) {
            throw new ValidationException(Message.SHORT_URL_ID_EXCEEDS_ALLOCATED_LIMIT.getMessage(CUSTOM_URL_ID_LENGTH_LIMIT));
        }
    }
}
