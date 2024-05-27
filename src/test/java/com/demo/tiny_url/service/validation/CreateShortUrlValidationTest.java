package com.demo.tiny_url.service.validation;

import com.demo.tiny_url.exception.model.ValidationException;
import com.demo.tiny_url.model.CreateShortUrlRequest;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.demo.tiny_url.constants.TinyUrlConstants.CUSTOM_URL_ID_LENGTH_LIMIT;
import static com.demo.tiny_url.constants.TinyUrlConstants.URL_LENGTH_LIMIT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateShortUrlValidationTest {

    public static final CreateShortUrlValidationService createShortUrlValidationService = new CreateShortUrlValidationServiceImpl();

    @Test
    public void shouldThrowExceptionIfCustomUrlIdLengthGreaterThanLimit() {
        CreateShortUrlRequest request = CreateShortUrlRequest.builder().url("testUrl")
                .alias("asdfkssdfsafaafdafssadfsddddoeoeodomvdlfslpsfdsasasdfasfdasfsafasasadfs").build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> createShortUrlValidationService.validate(request));
        assertEquals(exception.getMessage(), "Custom URL length exceeds allocated limit : " + CUSTOM_URL_ID_LENGTH_LIMIT);
    }

    @Test
    public void shouldThrowExceptionIfCustomUrlIdCharisNotBase62Limit() {
        CreateShortUrlRequest request = CreateShortUrlRequest.builder().url("testUrl")
                .alias("@#*&@&&@").build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> createShortUrlValidationService.validate(request));
        assertEquals(exception.getMessage(), "Custom URL contains invalid characters only alphabets and numbers allowed");
    }

    @Test
    public void shouldThrowExceptionIfCustomUrlIdBlankLimit() {
        CreateShortUrlRequest request = CreateShortUrlRequest.builder().url("testUrl")
                .alias("       ").build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> createShortUrlValidationService.validate(request));
        assertEquals(exception.getMessage(), "Input must not be blank");
    }


    @Test
    public void shouldThrowExceptionIfUrlIsBlank() {
        CreateShortUrlRequest request = CreateShortUrlRequest.builder().url("   ").build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> createShortUrlValidationService.validate(request));
        assertEquals(exception.getMessage(), "Input must not be blank");
    }

    @Test
    public void shouldThrowExceptionIfUrlLengthExceedsLimit() {
        String url = Strings.EMPTY;
        for(int i = 0; i<=URL_LENGTH_LIMIT+1; i++) {
            url = url.concat("A");
        }
        CreateShortUrlRequest request = CreateShortUrlRequest.builder().url(url).build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> createShortUrlValidationService.validate(request));
        assertEquals(exception.getMessage(), "URL length limit exceed, max allowable limit is " + URL_LENGTH_LIMIT);
    }

    @Test
    public void shouldNotThrowExceptionIfUrlValid() {
        CreateShortUrlRequest request = CreateShortUrlRequest.builder().url("test").alias("test").build();
        Assertions.assertDoesNotThrow(() -> createShortUrlValidationService.validate(request));
    }

}
