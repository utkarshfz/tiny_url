package com.demo.tiny_url.service.counter;

import com.demo.tiny_url.client.zookeeper.ZookeeperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZookeeperCounterServiceImpl implements CounterService {


    private static final Logger logger = LoggerFactory.getLogger(ZookeeperCounterServiceImpl.class);
    private final ZookeeperClient zkClient;

    private static final String PARENT_NODE_PATH = "/counter";
    private static final String LOCK = "_LOCK";
    private String counterNodePath;
    private static final String counterLimitsSeparatorElement = " ";

    @Autowired
    ZookeeperCounterServiceImpl(ZookeeperClient zkClient) {
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
        long counter = zkClient.readData(counterNodePath);
        List<Long> counterLimitList = zkClient.readData(PARENT_NODE_PATH, counterLimitsSeparatorElement);
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
        long counter = zkClient.readData(counterNodePath);
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
                zkClient.createEphemeralNode(lockNode);
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
