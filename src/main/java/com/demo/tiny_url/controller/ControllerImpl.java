package com.demo.tiny_url.controller;

import com.demo.tiny_url.model.CreateShortUrlRequest;
import com.demo.tiny_url.model.CreateShortUrlResponse;
import com.demo.tiny_url.service.create_short_url.CreateShortUrlService;
import com.demo.tiny_url.service.url_resolution.UrlResolutionService;
import com.demo.tiny_url.service.validation.CreateShortUrlValidationService;
import com.demo.tiny_url.service.validation.ResolveShortUrlIdValidationService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class ControllerImpl implements Controller{

    private static final String URL_PREFIX = "localhost:9080/";

    @Autowired
    private UrlResolutionService urlResolutionService;

    @Autowired
    private CreateShortUrlService createShortUrlService;

    @Autowired
    private ResolveShortUrlIdValidationService resolveShortUrlIdValidationService;

    @Autowired
    private CreateShortUrlValidationService createShortUrlValidationService;

    @Override
    @RateLimiter(name = "resolveUrlRateLimit")
    public ResponseEntity<Void> resolveUrl(String id) {
        resolveShortUrlIdValidationService.validate(id);
        String longUrl = urlResolutionService.process(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @Override
    @RateLimiter(name = "createShortUrlRateLimit")
    public ResponseEntity<CreateShortUrlResponse> createShortURl(CreateShortUrlRequest body) {
        createShortUrlValidationService.validate(body);
        String shortUrl = URL_PREFIX;
        if(Objects.isNull(body.getAlias())) {
            shortUrl = shortUrl.concat(createShortUrlService.process(body.getUrl()));
        }else {
            shortUrl = shortUrl.concat(createShortUrlService.process(body.getUrl(), body.getAlias()));
        }
        CreateShortUrlResponse shortUrlResponse = new CreateShortUrlResponse();
        shortUrlResponse.setUrl(shortUrl);
        return new ResponseEntity<>(shortUrlResponse, HttpStatus.OK);
    }
}
