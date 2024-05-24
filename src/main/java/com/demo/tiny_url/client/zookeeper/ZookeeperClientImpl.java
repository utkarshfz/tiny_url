package com.demo.tiny_url.client.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ZookeeperClientImpl implements ZookeeperClient{

    @Autowired
    private ZkClient zkClient;

    @Override
    public long readData(String path) {
        return Long.parseLong(zkClient.readData(path));
    }

    @Override
    public List<Long> readData(String path, String separator) {
        String data = zkClient.readData(path);
        return Arrays.stream(data.split(separator)).map(String::trim).map(Long::parseLong).toList();
    }

    @Override
    public void writeData(String path, Object data) {
        zkClient.writeData(path, data);
    }

    @Override
    public void delete(String path) {
        zkClient.delete(path);
    }

    @Override
    public List<String> getChildren(String parentPath) {
        return zkClient.getChildren(parentPath);
    }

    @Override
    public boolean exists(String path) {
        return zkClient.exists(path);
    }

    @Override
    public void createEphemeralNode(String path) {
        zkClient.createEphemeral(path);
    }
}
