package com.demo.tiny_url.house_keeping;

import com.demo.tiny_url.repository.AliasDetailsRepository;
import com.demo.tiny_url.repository.ShortUrlDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ShortUrlCleanupServiceImpl implements ShortUrlCleanupService{

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlCleanupServiceImpl.class);

    @Autowired
    private ShortUrlDetailsRepository shortUrlDetailsRepository;


    @Override
    @Scheduled(cron = "0 0 3 * * *")
    public void process() {
        try{
            shortUrlDetailsRepository.deleteInBatchByExpiresAtAfter(LocalDateTime.now());
        } catch (Exception e) {
            logger.error("[Critical] Short Url Cleanup failed!", e);
        }

    }
}
