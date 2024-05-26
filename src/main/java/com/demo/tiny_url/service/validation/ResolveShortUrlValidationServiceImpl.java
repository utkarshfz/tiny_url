package com.demo.tiny_url.service.validation;

import com.demo.tiny_url.exception.model.ValidationException;
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
            throw new ValidationException("Short URL ID length exceeds allocated limit : " + CUSTOM_URL_ID_LENGTH_LIMIT);
        }
    }
}
