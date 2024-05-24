package com.demo.tiny_url.client.zookeeper;

import java.util.List;

public interface ZookeeperClient {
    /**
     * Reads data from path of zk node and returns value in long format
     * @param path zk node path
     * @return - value converted to long format
     */
    long readData(String path);

    /**
     * Reads data from path of zk node and returns value as list of long separated by specified separator.
     * @param path zk node path
     * @param separator separator for string representation of array
     * @return list of long values.
     */
    List<Long> readData(String path, String separator);

    /**
     * Writes the data to znode specified by path
     * @param path znode path
     * @param data znode data
     */
    void writeData(String path, Object data);

    /**
     * Delete znode pointed by path
     * @param path znode path
     */
    void delete(String path);

    /**
     * List chidren of given parent node.
     * @param parentPath parent node path.
     * @return List of children.
     */
    List<String> getChildren(String parentPath);

    /**
     * Checks if node pointed by path exists or not.
     * @param path path of znode.
     * @return if znode exists or not
     */
    boolean exists(String path);

    /**
     * Creates a ephemeral znode.
     * @param path znode path.
     */
    void createEphemeralNode(String path);
}
