package com.demo.tiny_url.house_keeping;

import com.demo.tiny_url.repository.AliasDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

public class AliasCleanupServiceImpl implements AliasCleanupService{
    private static final Logger logger = LoggerFactory.getLogger(AliasCleanupService.class);

    private AliasDetailsRepository aliasDetailsRepository;

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public void process() {
        try {
            aliasDetailsRepository.deleteInBatchByExpiresAtAfter(LocalDateTime.now());
        } catch (Exception e) {
            logger.error("[Critical] Failed to clean alias repository", e);
        }
    }
}
