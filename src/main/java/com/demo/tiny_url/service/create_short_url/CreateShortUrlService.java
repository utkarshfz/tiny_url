package com.demo.tiny_url.service.create_short_url;

public interface CreateShortUrlService {
    /**
     * Takes long url and returns corresponding shortUrl
     * @param url long url
     * @return short url
     */
    String process(String url);

    /**
     * Takes long url and returns alias-url pointing to it.
     * @param url long url
     * @param alias alias
     * @return custom url
     */
    String process(String url, String alias);
}
