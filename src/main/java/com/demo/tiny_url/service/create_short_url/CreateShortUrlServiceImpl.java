package com.demo.tiny_url.service.create_short_url;

import com.demo.tiny_url.constants.TinyUrlConstants;
import com.demo.tiny_url.entity.AliasDetails;
import com.demo.tiny_url.entity.ShortUrlDetails;
import com.demo.tiny_url.exception.model.ResourceAlreadyExistsException;
import com.demo.tiny_url.model.Message;
import com.demo.tiny_url.repository.AliasDetailsRepository;
import com.demo.tiny_url.repository.ShortUrlDetailsRepository;
import com.demo.tiny_url.service.counter.CounterService;
import com.demo.tiny_url.util.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreateShortUrlServiceImpl implements CreateShortUrlService{

    @Value("${application.shortUrl.expiry_time_days}")
    private int shortUrlExpiryTimeDays;

    @Autowired
    private ShortUrlDetailsRepository shortUrlDetailsRepository;

    @Autowired
    private AliasDetailsRepository aliasDetailsRepository;

    @Autowired
    private CounterService counterService;

    @Override
    public String process(String url) {
        Optional<ShortUrlDetails> shortUrlDetailsOptional = shortUrlDetailsRepository.findByLongUrl(url);
        if(shortUrlDetailsOptional.isPresent()) {
            String id = shortUrlDetailsOptional.get().getShortUrlId();
            return shortUrlDetailsRepository.save(new ShortUrlDetails(id, url, getExpiryDate())).getShortUrlId();
        }
        String id = ApplicationUtil.toBase62(counterService.getCounter()).concat(TinyUrlConstants.SHORT_URL_SALT);
        counterService.incrementCounter();
        ShortUrlDetails details = new ShortUrlDetails(id, url, getExpiryDate());
        return shortUrlDetailsRepository.save(details).getShortUrlId();
    }

    @Override
    public String process(String url, String alias) {
        Optional<AliasDetails> aliasDetailsOptional = aliasDetailsRepository.findById(alias);
        if(aliasDetailsOptional.isEmpty()) {
            AliasDetails aliasDetails = new AliasDetails(alias, url, getExpiryDate());
            return aliasDetailsRepository.save(aliasDetails).getAlias();
        }
        throw new ResourceAlreadyExistsException(Message.RESOURCE_ALREADY_EXISTS.getMessage(alias));
    }

    private LocalDateTime getExpiryDate() {
        return LocalDateTime.now().plusDays(shortUrlExpiryTimeDays);
    }
}
