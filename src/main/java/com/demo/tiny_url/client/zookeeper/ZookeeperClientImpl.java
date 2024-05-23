package com.demo.tiny_url.client.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ZookeeperClientImpl implements ZookeeperClient{


    private static final Logger logger = LoggerFactory.getLogger(ZookeeperClientImpl.class);
    private final ZkClient zkClient;

    private static final String PARENT_NODE_PATH = "/counter";
    private static final String LOCK = "_LOCK";
    private String counterNodePath;
    private static final String counterLimitsSplitElement = " ";

    @Autowired
    ZookeeperClientImpl(ZkClient zkClient) {
        this.zkClient = zkClient;
        counterNodePath = selectCounterNodePath();
    }


    /**
     * Gets counter value
     *
     * @return counter value
     */
    @Override
    public long getCounter() {
        long counter = Long.parseLong(zkClient.readData(counterNodePath));
        String counterLimits = zkClient.readData(PARENT_NODE_PATH);
        List<Long> counterLimitList = Arrays.stream(counterLimits.split(counterLimitsSplitElement)).map(String::trim).map(Long::parseLong).toList();
        if(counterLimitList.contains(counter)) {
            logger.info("Counter Node : {} exhausted, attempting to use another node", counterNodePath);
            zkClient.delete(counterNodePath);
            counterNodePath = selectCounterNodePath();
        }
        return counter;
    }

    /**
     * Increments counter.
     */
    @Override
    public void incrementCounter() {
        long counter = Long.parseLong(zkClient.readData(counterNodePath));
        zkClient.writeData(counterNodePath, String.valueOf(counter+1));
    }

    private String selectCounterNodePath() {
        List<String> children = zkClient.getChildren(PARENT_NODE_PATH);
        for(String node: children) {
            String nodePath = PARENT_NODE_PATH.concat("/").concat(node);
            String lockNode = nodePath.concat(LOCK);
            if(zkClient.exists(lockNode)) {
                continue;
            }
            try{
                zkClient.createEphemeral(lockNode);
            } catch (Exception e) {
                logger.error("Error attempting lock on zookeeper node, will try another node {}", e.getMessage());
                continue;
            }
            logger.info("Server querying counter node : {}", nodePath);
            return nodePath;
        }
        throw new RuntimeException("[CRITICAL] Counters have been exhausted!");
    }
}
