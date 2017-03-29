package com.github.invictum.mei;

import com.github.invictum.mei.dtos.Queue;

import java.util.List;
import java.util.Map;

public abstract class Backend {

    private Map<String, Object> config = null;

    /**
     * Initialize connection to backend
     */
    public abstract void initBackend();

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

    protected void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    protected String getProperty(String key) {
        return getProperty(key, null);
    }

    protected String getProperty(String key, String defaultValue) {
        if (config.containsKey(key)) {
            return config.get(key).toString();
        }
        return defaultValue;
    }

}
