package com.demo.tiny_url.service.url_resolution;

import com.demo.tiny_url.entity.AliasDetails;
import com.demo.tiny_url.entity.ShortUrlDetails;
import com.demo.tiny_url.exception.model.NotFoundException;
import com.demo.tiny_url.repository.AliasDetailsRepository;
import com.demo.tiny_url.repository.ShortUrlDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UrlResolutionServiceImplTest {

    @Mock
    private ShortUrlDetailsRepository shortUrlDetailsRepository;

    @Mock
    private AliasDetailsRepository aliasDetailsRepository;

    @InjectMocks
    private UrlResolutionService urlResolutionService = new UrlResolutionServiceImpl();

    @Test
    public void resolveShortUrlTest() {
        String shortUrlId = "testId";
        String url = "testUrl";
        ShortUrlDetails shortUrlDetails = new ShortUrlDetails(shortUrlId, url, LocalDateTime.now());
        Mockito.when(shortUrlDetailsRepository.findById(shortUrlId)).thenReturn(Optional.of(shortUrlDetails));
        assertEquals(urlResolutionService.process(shortUrlId), url);
    }


    @Test
    public void resolveCustomShortUrlTest() {
        String customUrlId = "testId";
        String url = "testUrl";
        AliasDetails aliasDetails = new AliasDetails(customUrlId, url, LocalDateTime.now());
        Mockito.when(shortUrlDetailsRepository.findById(customUrlId)).thenReturn(Optional.empty());
        Mockito.when(aliasDetailsRepository.findById(customUrlId)).thenReturn(Optional.of(aliasDetails));
        assertEquals(urlResolutionService.process(customUrlId), url);
    }

    @Test
    public void resolveCustomUrlIfUrlNotExistsTest() {
        String customUrlId = "testId";
        String url = "testUrl";
        Mockito.when(shortUrlDetailsRepository.findById(customUrlId)).thenReturn(Optional.empty());
        Mockito.when(aliasDetailsRepository.findById(customUrlId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> urlResolutionService.process(customUrlId));
    }
}
