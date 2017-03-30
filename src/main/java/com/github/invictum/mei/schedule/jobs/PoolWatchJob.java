package com.github.invictum.mei.schedule.jobs;


import com.github.invictum.mei.Backend;
import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.dtos.Queue;
import org.bukkit.Bukkit;
import org.knowm.sundial.Job;
import org.knowm.sundial.exceptions.JobInterruptException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PoolWatchJob extends Job {

    private Logger log = MeiPlugin.getPlugin(MeiPlugin.class).getLogger();
    private Backend backend = MeiPlugin.getPlugin(MeiPlugin.class).getBackend();

    @Override
    public void doRun() throws JobInterruptException {
        List<String> doneIds = new ArrayList<>();
        for (Queue queue : backend.getQueues()) {
            log.info("Processing of command: " + queue.getCommand());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), queue.getCommand());
            doneIds.add(queue.getId());
        }
        backend.removeQueues(doneIds);
    }
}
