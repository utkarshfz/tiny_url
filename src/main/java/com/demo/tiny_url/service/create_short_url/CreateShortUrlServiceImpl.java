package com.demo.tiny_url.service.create_short_url;

import com.demo.tiny_url.entity.AliasDetails;
import com.demo.tiny_url.entity.ShortUrlDetails;
import com.demo.tiny_url.exception.model.ResourceAlreadyExistsException;
import com.demo.tiny_url.repository.AliasDetailsRepository;
import com.demo.tiny_url.repository.ShortUrlDetailsRepository;
import com.demo.tiny_url.service.counter.CounterService;
import com.demo.tiny_url.util.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class CreateShortUrlServiceImpl implements CreateShortUrlService{

    //should not include base62 characters
    private final String SHORT_URL_SALT = "#";

    @Autowired
    private ShortUrlDetailsRepository shortUrlDetailsRepository;

    @Autowired
    private AliasDetailsRepository aliasDetailsRepository;

    @Autowired
    private CounterService counterService;

    @Override
    public String process(String url) {
        String id = ApplicationUtil.toBase62(counterService.getCounter()).concat(SHORT_URL_SALT);
        counterService.incrementCounter();
        ShortUrlDetails details = new ShortUrlDetails(id, url, LocalDateTime.now());
        return shortUrlDetailsRepository.save(details).getShortUrlId();
    }

    @Override
    public String process(String url, String alias) {
        Optional<AliasDetails> aliasDetailsOptional = aliasDetailsRepository.findById(alias);
        if(aliasDetailsOptional.isEmpty()) {
            AliasDetails aliasDetails = new AliasDetails(alias, url, LocalDateTime.now());
            return aliasDetailsRepository.save(aliasDetails).getAlias();
        }
        throw new ResourceAlreadyExistsException(String.format("Custom url id {} not available", alias));
    }
}
