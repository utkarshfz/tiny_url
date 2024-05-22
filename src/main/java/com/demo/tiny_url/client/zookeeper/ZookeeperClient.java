package com.demo.tiny_url.client.zookeeper;

public interface ZookeeperClient {
    /**
     * Gets counter value
     * @return counter value
     */
    long getCounter();

    /**
     * Increments counter.
     */
    void incrementCounter();
}
