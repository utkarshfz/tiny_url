package com.demo.tiny_url.service.counter;

import com.demo.tiny_url.client.zookeeper.ZookeeperClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.demo.tiny_url.service.counter.ZookeeperCounterServiceImpl.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ZookeeperCounterServiceImplTest {

    @Mock
    private ZookeeperClient zkClient;

    private ZookeeperCounterServiceImpl counterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(zkClient.getChildren(PARENT_NODE_PATH)).thenReturn(List.of("NODE1", "NODE2"));
        counterService = new ZookeeperCounterServiceImpl(zkClient);
    }

    @Test
    void shouldReturnCounter() {
        when(zkClient.readData(anyString())).thenReturn(10L);

        long counter = counterService.getCounter();
        assertEquals(10L, counter);
    }

    @Test
    void shouldDeleteNodeIfCounterLimitReached() {
        when(zkClient.readData(anyString())).thenReturn(20L);
        when(zkClient.readData(PARENT_NODE_PATH, COUNTER_LIMITS_SEPARATOR_ELEMENT)).thenReturn(Arrays.asList(10L, 20L));
        when(zkClient.exists(anyString())).thenReturn(false);
        when(zkClient.getChildren(anyString())).thenReturn(List.of("NODE1", "NODE2"));
        counterService.getCounter();
        verify(zkClient).delete(anyString());

    }

    @Test
    void shouldSuccessfullyIncrementCounter() {
        when(zkClient.readData(anyString())).thenReturn(10L);

        counterService.incrementCounter();
        verify(zkClient).writeData(anyString(), eq("11"));
    }

    @Test
    void shouldSkipNodeIfLockNodeExists() {
        when(zkClient.getChildren(anyString())).thenReturn(Arrays.asList("NODE1", "NODE2"));
        when(zkClient.exists(PARENT_NODE_PATH.concat("/").concat("NODE1").concat(LOCK))).thenReturn(true);
        new ZookeeperCounterServiceImpl(zkClient);
        verify(zkClient).createEphemeralNode(PARENT_NODE_PATH.concat("/").concat("NODE2").concat(LOCK));
    }

    @Test
    void shouldThrowExceptionIfCounterNodeExhausted() {
        when(zkClient.getChildren(anyString())).thenReturn(List.of());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> new ZookeeperCounterServiceImpl(zkClient));
        assertEquals("[CRITICAL] Counters have been exhausted!", exception.getMessage());
    }

    @Test
    void shouldNotBreakAndTryAnotherNodeIfEphemeralNodeCreationFails() {
        when(zkClient.getChildren(anyString())).thenReturn(Arrays.asList("NODE1", "NODE2"));
        when(zkClient.exists(PARENT_NODE_PATH.concat("/").concat("NODE1").concat(LOCK))).thenReturn(false);
        Mockito.doThrow(new RuntimeException()).when(zkClient).createEphemeralNode(PARENT_NODE_PATH.concat("/").concat("NODE1").concat(LOCK));
        new ZookeeperCounterServiceImpl(zkClient);
        verify(zkClient).createEphemeralNode(PARENT_NODE_PATH.concat("/").concat("NODE2").concat(LOCK));
    }

}
