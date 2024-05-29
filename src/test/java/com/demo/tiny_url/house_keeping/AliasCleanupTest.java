package com.demo.tiny_url.house_keeping;

import com.demo.tiny_url.repository.AliasDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AliasCleanupTest {

    @Mock
    private AliasDetailsRepository aliasDetailsRepository;

    @InjectMocks
    private AliasCleanupService aliasCleanupService = new AliasCleanupServiceImpl();

    @Test
    public void aliasCleanupTest() {
        Mockito.doNothing().when(aliasDetailsRepository).deleteInBatchByExpiresAtAfter(Mockito.any());
        Assertions.assertDoesNotThrow(() -> aliasCleanupService.process());
    }

    @Test
    public void shouldNotBreakIfBatchProcessingFails() {
        Mockito.doThrow(new RuntimeException("Batch Processing failed")).when(aliasDetailsRepository).deleteInBatchByExpiresAtAfter(Mockito.any());
        Assertions.assertDoesNotThrow(() -> aliasCleanupService.process());
    }
}
