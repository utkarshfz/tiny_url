package com.demo.tiny_url.controller;

import com.demo.tiny_url.exception.model.ValidationException;
import com.demo.tiny_url.model.CreateShortUrlRequest;
import com.demo.tiny_url.service.create_short_url.CreateShortUrlService;
import com.demo.tiny_url.service.url_resolution.UrlResolutionService;
import com.demo.tiny_url.service.validation.CreateShortUrlValidationService;
import com.demo.tiny_url.service.validation.ResolveShortUrlIdValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest
public class ControllerTest {

    @Mock
    private UrlResolutionService urlResolutionService;

    @Mock
    private CreateShortUrlService createShortUrlService;

    @Mock
    private ResolveShortUrlIdValidationService resolveShortUrlIdValidationService;

    @Mock
    private CreateShortUrlValidationService createShortUrlValidationService;

    @InjectMocks
    private Controller controller = new ControllerImpl();

    @Test
    public void shouldResolveShortUrl() {
        String shortUrl = "shortUrl";
        String longUrl = "longUrl";
        Mockito.doNothing().when(resolveShortUrlIdValidationService).validate(Mockito.any());
        Mockito.when(urlResolutionService.process(shortUrl)).thenReturn(longUrl);
        ResponseEntity<Void> response = controller.resolveUrl(shortUrl);
        Assertions.assertEquals(longUrl, Objects.requireNonNull(response.getHeaders().getLocation()).toString());
        Assertions.assertEquals(HttpStatus.MOVED_PERMANENTLY, response.getStatusCode());
    }

    @Test
    public void shouldThrowExceptionIfResolveShortUrlValidationFails() {
        String shortUrl = "shortUrl";
        Mockito.doThrow(new ValidationException("Validation Failed")).when(resolveShortUrlIdValidationService).validate(Mockito.any());
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> controller.resolveUrl(shortUrl));
        Assertions.assertEquals("Validation Failed", exception.getMessage());
    }

    @Test
    public void shouldCreateShortUrlTest() {
        String url = "test/";
        String processedUrl = "test";
        String shortUrlId = "shortUrlTest";
        String shortUrl = "localhost:9080/shortUrlTest";
        Mockito.doNothing().when(createShortUrlValidationService).validate(Mockito.any());
        Mockito.when(createShortUrlService.process(processedUrl)).thenReturn(shortUrlId);
        Assertions.assertEquals(shortUrl, Objects.requireNonNull(controller.createShortURl(CreateShortUrlRequest.builder().url(url).build()).getBody()).getUrl());
    }

    @Test
    public void shouldCreateCustomShortUrlTest() {
        String url = "test/";
        String processedUrl = "test";
        String alias = "alias";
        String shortUrl = "localhost:9080/alias";
        Mockito.doNothing().when(createShortUrlValidationService).validate(Mockito.any());
        Mockito.when(createShortUrlService.process(processedUrl, alias)).thenReturn(alias);
        Assertions.assertEquals(shortUrl, Objects.requireNonNull(controller.createShortURl(CreateShortUrlRequest.builder().url(url).alias(alias).build()).getBody()).getUrl());
    }

    @Test
    public void shouldThrowExceptionIfCreateShortUrlValidationFails() {
        String url = "test/";
        Mockito.doThrow(new ValidationException("Validation Failed")).when(createShortUrlValidationService).validate(Mockito.any());
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> controller.createShortURl(CreateShortUrlRequest.builder().url(url).build()));
        Assertions.assertEquals("Validation Failed", exception.getMessage());
    }
}
