package com.demo.tiny_url.service.validation;

import com.demo.tiny_url.exception.model.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.demo.tiny_url.constants.TinyUrlConstants.CUSTOM_URL_ID_LENGTH_LIMIT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResolveUrlValidationTest {

    private static final ResolveShortUrlIdValidationService resolveShortUrlIdValidationService = new ResolveShortUrlValidationServiceImpl();

    @Test
    public void resolveShortUrlShouldThrowExceptionIfUrlIdLengthExceedsLimit() {
        String urlId = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> resolveShortUrlIdValidationService.validate(urlId));
        assertEquals("Short URL ID length exceeds allocated limit : " + CUSTOM_URL_ID_LENGTH_LIMIT, exception.getMessage());
    }

    @Test
    public void resolveShortUrlShouldNotThrowExceptionIfUrlIdIsValid() {
        String urlId = "aaa";
        Assertions.assertDoesNotThrow(() -> resolveShortUrlIdValidationService.validate(urlId));
    }


}
