package com.demo.tiny_url.controller;

import com.demo.tiny_url.model.CreateShortUrlRequest;
import com.demo.tiny_url.model.CreateShortUrlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerImpl implements Controller{
    @Override
    public ResponseEntity<Void> resolveUrl(String id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<CreateShortUrlResponse> createShortURl(CreateShortUrlRequest body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
