package com.demo.tiny_url.client.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZookeeperClientImpl implements ZookeeperClient{

    @Autowired
    private ZkClient zkClient;

    private static final String PARENT_NODE_PATH = "/counter";
    private static final String LOCK = "_LOCK";
    private String counterNodePath;

    ZookeeperClientImpl() {
//        counterNodePath = selectCounterNodePath();
    }


    /**
     * Gets counter value
     *
     * @return counter value
     */
    @Override
    public long getCounter() {
        long counter = zkClient.readData(counterNodePath);
        List<Long> counterLimits = zkClient.readData(PARENT_NODE_PATH);
        if(counterLimits.contains(counter)) {
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
        zkClient.writeData(counterNodePath, counter+1);

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
                //log information.
                continue;
            }

            return nodePath;
        }
        throw new RuntimeException("[CRITICAL] Counters have been exhausted!");
    }
}
