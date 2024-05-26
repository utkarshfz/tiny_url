package com.demo.tiny_url.service.validation;

import com.demo.tiny_url.exception.model.ValidationException;
import com.demo.tiny_url.model.CreateShortUrlRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;


import java.util.Objects;

import static com.demo.tiny_url.constants.TinyUrlConstants.*;

@Service
public class CreateShortUrlValidationServiceImpl implements CreateShortUrlValidationService{

    @Override
    public void validate(CreateShortUrlRequest request) {
        if(Objects.nonNull(request.getAlias())) {
            validateCustomUrlIdLength(request.getAlias());
            validateCustomUrlIdCharacters(request.getAlias());
        }
        validateInputNotBlank(request.getUrl());
        validateUrlLength(request.getUrl());
    }

    /**
     * Checks if custom-url id length is within limits
     * @param id custom url id
     */
    private void validateCustomUrlIdLength(String id) {
        if(id.length() > CUSTOM_URL_ID_LENGTH_LIMIT) {
            throw new ValidationException("Custom URL length exceeds allocated limit : " + CUSTOM_URL_ID_LENGTH_LIMIT);
        }
    }

    /**
     * Checks if custom url id have valid characters.
     * @param id custom url id
     */
    private void validateCustomUrlIdCharacters(String id) {
        for(int i = 0; i<id.length(); i++) {
            if(!BASE62_CHARS.contains(Strings.EMPTY + id.charAt(i))) {
                throw new ValidationException("Custom URL contains invalid characters only alphabets and numbers allowed");
            }
        }
    }

    /**
     * Checks if input is not blank
     * @param input value sent by user.
     */
    private void validateInputNotBlank(String input) {
        if(Strings.EMPTY.equals(input.trim())) {
            throw new ValidationException("Input must not be blank");
        }
    }


    /**
     * Checks if url length exceeds max limit
     * @param url url
     */
    private void validateUrlLength(String url) {
        if(url.length() > URL_LENGTH_LIMIT) {
            throw new ValidationException("URL length limit exceed, max allowable limit is " + URL_LENGTH_LIMIT);
        }
    }

}
