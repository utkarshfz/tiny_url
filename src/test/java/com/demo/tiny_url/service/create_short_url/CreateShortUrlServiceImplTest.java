package com.demo.tiny_url.service.create_short_url;

import com.demo.tiny_url.constants.TinyUrlConstants;
import com.demo.tiny_url.entity.AliasDetails;
import com.demo.tiny_url.entity.ShortUrlDetails;
import com.demo.tiny_url.exception.model.ResourceAlreadyExistsException;
import com.demo.tiny_url.repository.AliasDetailsRepository;
import com.demo.tiny_url.repository.ShortUrlDetailsRepository;
import com.demo.tiny_url.service.counter.CounterService;
import com.demo.tiny_url.util.ApplicationUtil;
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
public class CreateShortUrlServiceImplTest {

    @Mock
    private ShortUrlDetailsRepository shortUrlDetailsRepository;

    @Mock
    private AliasDetailsRepository aliasDetailsRepository;

    @Mock
    private CounterService counterService;

    @InjectMocks
    private CreateShortUrlService createShortUrlService = new CreateShortUrlServiceImpl();


    @Test
    public void createShortUrlTest() {
        String url = "testUrl";
        long counter = 1000000;
        Mockito.when(counterService.getCounter()).thenReturn(counter);
        String testId = ApplicationUtil.toBase62(counter).concat(TinyUrlConstants.SHORT_URL_SALT);
        ShortUrlDetails shortUrlDetails = new ShortUrlDetails(testId, url, LocalDateTime.now());
        Mockito.when(shortUrlDetailsRepository.findByLongUrl(url)).thenReturn(Optional.empty());
        Mockito.when(shortUrlDetailsRepository.save(Mockito.any())).thenReturn(shortUrlDetails);
        assertEquals(createShortUrlService.process(url), testId);
    }

    @Test
    public void createShortUrlIfShortUrlAlreadyPresentTest() {
        String url = "testUrl";
        ShortUrlDetails shortUrlDetails = new ShortUrlDetails("testId", url, LocalDateTime.now());
        Mockito.when(shortUrlDetailsRepository.findByLongUrl(url)).thenReturn(Optional.of(shortUrlDetails));
        Mockito.when(shortUrlDetailsRepository.save(Mockito.any())).thenReturn(shortUrlDetails);
        assertEquals(createShortUrlService.process(url), "testId");
    }

    @Test
    public void createCustomShortUrlTest() {
        String alias = "alias";
        String url = "testUrl";
        AliasDetails aliasDetails = new AliasDetails(alias, url, LocalDateTime.now());
        Mockito.when(aliasDetailsRepository.findById(alias)).thenReturn(Optional.empty());
        Mockito.when(aliasDetailsRepository.save(Mockito.any())).thenReturn(aliasDetails);
        assertEquals(createShortUrlService.process(url, alias), "alias");
    }

    @Test
    public void createCustomShortUrlShouldThrowExceptionIfCustomUrlAlreadyExistsTest() {
        String alias = "alias";
        String url = "testUrl";
        AliasDetails aliasDetails = new AliasDetails(alias, url, LocalDateTime.now());
        Mockito.when(aliasDetailsRepository.findById(alias)).thenReturn(Optional.of(aliasDetails));

        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> createShortUrlService.process(url, alias));
    }
}
