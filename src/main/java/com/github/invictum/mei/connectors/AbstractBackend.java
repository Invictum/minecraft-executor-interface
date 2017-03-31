package com.github.invictum.mei.connectors;

import com.github.invictum.mei.dtos.Queue;

import java.util.List;
import java.util.Map;

public abstract class AbstractBackend {

    private Map<String, Object> config = null;

    public AbstractBackend(Map<String, Object> config) {
        this.config = config;
    }

    /**
     * Initialize connection to backend
     */
    public abstract Boolean initBackend();

    /**
     * Close connection to backend
     */
    public abstract void closeBackend();

    /**
     * Get list of queue
     *
     * @return List of DTO's
     */
    public abstract List<Queue> getQueues();

    /**
     * Remove items from of queue list by ID's
     */
    public abstract void removeQueues(List<String> ids);

    String getProperty(String key) {
        return getProperty(key, null);
    }

    String getProperty(String key, String defaultValue) {
        if (config.containsKey(key)) {
            return config.get(key).toString();
        }
        return defaultValue;
    }

}
