package com.demo.tiny_url.service.url_resolution;

import com.demo.tiny_url.entity.AliasDetails;
import com.demo.tiny_url.entity.ShortUrlDetails;
import com.demo.tiny_url.exception.model.NotFoundException;
import com.demo.tiny_url.repository.AliasDetailsRepository;
import com.demo.tiny_url.repository.ShortUrlDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UrlResolutionServiceImpl implements UrlResolutionService{

    @Autowired
    private ShortUrlDetailsRepository shortUrlDetailsRepository;

    @Autowired
    private AliasDetailsRepository aliasDetailsRepository;

    /**
     * Takes id for the short url and returns the long url address
     *
     * @param id short url id.
     * @return long url
     */
    @Override
    public String process(String id) {
        Optional<ShortUrlDetails> shortUrlDetailsOptional = shortUrlDetailsRepository.findById(id);
        if(shortUrlDetailsOptional.isPresent()) {
            return shortUrlDetailsOptional.get().getLongUrl();
        }
        Optional<AliasDetails> aliasDetailsOptional = aliasDetailsRepository.findById(id);
        if(aliasDetailsOptional.isPresent()) {
            return aliasDetailsOptional.get().getLongUrl();
        }
        throw new NotFoundException(String.format("URL: {} does not exist", id));
    }
}