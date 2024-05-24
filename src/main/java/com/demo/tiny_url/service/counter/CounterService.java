package com.demo.tiny_url.service.counter;

public interface CounterService {
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
