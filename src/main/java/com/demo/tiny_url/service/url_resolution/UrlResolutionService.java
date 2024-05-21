package com.demo.tiny_url.service.url_resolution;

public interface UrlResolutionService {
    /**
     * Takes id for the short url and returns the long url address
     * @param id  short url id.
     * @return long url
     */
    String process(String id);
}
