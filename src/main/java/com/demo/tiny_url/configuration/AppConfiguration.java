package com.demo.tiny_url.configuration;

import com.demo.tiny_url.util.StringSerializer;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${application.zookeeper.server}")
    private String hostPort;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(hostPort, 12000, 3000, new StringSerializer());
    }

}
