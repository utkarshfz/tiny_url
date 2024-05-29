package com.demo.tiny_url.client;

import com.demo.tiny_url.client.zookeeper.ZookeeperClient;
import com.demo.tiny_url.client.zookeeper.ZookeeperClientImpl;
import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ZookeeperClientTest {

    @Mock
    private ZkClient zkClient;

    @InjectMocks
    private ZookeeperClient client = new ZookeeperClientImpl();

    @Test
    public void shouldReadDataTest() {
        long data = 1L;
        String path = "testPath";
        Mockito.when(zkClient.readData(path)).thenReturn("1");
        Assertions.assertEquals(data, client.readData(path));
    }

    @Test
    public void shouldSplitAndReadDataTest() {
        List<Long> data= List.of(1L, 2L);
        String path = "testPath";
        Mockito.when(zkClient.readData(path)).thenReturn("1 2");
        Assertions.assertEquals(data, client.readData(path, " "));
    }

    @Test
    public void shouldNotThrowOnWriting() {
        Assertions.assertDoesNotThrow(() -> client.writeData("path", "testData"));
    }

    @Test
    public void shouldNotThrowOnDelete() {
        Assertions.assertDoesNotThrow(() -> client.delete("path"));
    }

    @Test
    public void shouldNotThrowOnGetChildren() {
        Assertions.assertDoesNotThrow(() -> client.getChildren("path"));
    }

    @Test
    public void shouldNotThrowOnCreateEphemeralNode() {
        Assertions.assertDoesNotThrow(() -> client.createEphemeralNode("path"));
    }
}
