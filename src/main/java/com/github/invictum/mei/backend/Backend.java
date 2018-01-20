package com.github.invictum.mei.backend;

import com.github.invictum.mei.entity.TaskEntity;

import java.util.Set;

/**
 * Define backend operations for tasks storage
 */
public interface Backend {
    /**
     * Saves {@link TaskEntity} to storage level
     *
     * @param task to save
     */
    void store(TaskEntity task);

    /**
     * Lists a set of stored tasks
     *
     * @return a set of tasks
     */
    Set<TaskEntity> list();

    /**
     * Removes tasks for a given IDs
     *
     * @param ids an array of tasks IDs to delete
     */
    void delete(String... ids);
}
