package com.demo.tiny_url.controller;

import com.demo.tiny_url.model.CreateShortUrlRequest;
import com.demo.tiny_url.model.CreateShortUrlResponse;
import com.demo.tiny_url.service.create_short_url.CreateShortUrlService;
import com.demo.tiny_url.service.url_resolution.UrlResolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class ControllerImpl implements Controller{

    private static final String URL_PREFIX = "https://miniurl/v1/";

    @Autowired
    private UrlResolutionService urlResolutionService;

    @Autowired
    private CreateShortUrlService createShortUrlService;

    @Override
    public ResponseEntity<Void> resolveUrl(String id) {
        String longUrl = urlResolutionService.process(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @Override
    public ResponseEntity<CreateShortUrlResponse> createShortURl(CreateShortUrlRequest body) {
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
