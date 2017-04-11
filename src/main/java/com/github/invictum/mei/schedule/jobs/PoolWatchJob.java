package com.github.invictum.mei.schedule.jobs;

import com.github.invictum.mei.Backend;
import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.conditions.ConditionProcessor;
import com.github.invictum.mei.dtos.Queue;
import org.bukkit.Bukkit;
import org.knowm.sundial.Job;
import org.knowm.sundial.exceptions.JobInterruptException;

import java.util.ArrayList;
import java.util.List;

public class PoolWatchJob extends Job {

    @Override
    public void doRun() throws JobInterruptException {
        List<String> doneIds = new ArrayList<>();
        for (Queue queue : Backend.getInstance().getQueues()) {
            boolean isApplicable = false;
            try {
                ConditionProcessor condition = new ConditionProcessor(queue.getConditions());
                isApplicable = condition.isApplicable();
            } catch (IllegalArgumentException ex) {
                MeiPlugin.log().info("Invalid condition format: " + ex.getMessage());
                doneIds.add(queue.getId());
            }
            if (isApplicable) {
                MeiPlugin.log().info("Processing of command: " + queue.getCommand());
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), queue.getCommand());
                doneIds.add(queue.getId());
            }
        }
        Backend.getInstance().removeQueues(doneIds);
    }
}
