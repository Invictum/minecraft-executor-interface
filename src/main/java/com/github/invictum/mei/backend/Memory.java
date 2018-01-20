package com.github.invictum.mei.backend;

import com.github.invictum.mei.entity.TaskEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Backend stores tasks data into JVM memory
 * Caution: all data will be lost on MC server restart
 */
public class Memory implements Backend {

    private Set<TaskEntity> tasks = new HashSet<>();

    @Override
    public void store(TaskEntity task) {
        tasks.add(task);
    }

    @Override
    public Set<TaskEntity> list() {
        return tasks;
    }

    @Override
    public void delete(String... ids) {
        if (ids != null) {
            tasks.stream().filter(task -> Arrays.asList(ids).contains(task.getId())).forEach(tasks::remove);
        }
    }
}
