package com.demo.tiny_url.house_keeping;

import com.demo.tiny_url.repository.ShortUrlDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortUrlCleanupTest {

    @Mock
    private ShortUrlDetailsRepository shortUrlDetailsRepository;

    @InjectMocks
    private ShortUrlCleanupService shortUrlCleanupService = new ShortUrlCleanupServiceImpl();

    @Test
    public void shortUrlCleanupTest() {
        Mockito.doNothing().when(shortUrlDetailsRepository).deleteInBatchByExpiresAtAfter(Mockito.any());
        Assertions.assertDoesNotThrow(() -> shortUrlCleanupService.process());
    }

    @Test
    public void shouldNotThrowExceptionIfBatchProcessingFails() {
        Mockito.doThrow(new RuntimeException("Batch Processing failed")).when(shortUrlDetailsRepository).deleteInBatchByExpiresAtAfter(Mockito.any());
        Assertions.assertDoesNotThrow(() -> shortUrlCleanupService.process());
    }

}
