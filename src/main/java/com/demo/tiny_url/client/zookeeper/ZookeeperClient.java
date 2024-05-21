package com.demo.tiny_url.client.zookeeper;

public interface ZookeeperClient {
    long getCounter();
    void incrementCounter();
}
