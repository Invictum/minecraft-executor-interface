package com.github.invictum.mei;

import com.github.invictum.mei.dtos.Queue;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EventProcessor extends BukkitRunnable {
    private MeiPlugin plugin;
    private Logger log;

    public EventProcessor(MeiPlugin plugin) {
        this.plugin = plugin;
        this.log = plugin.getLogger();
    }

    @Override
    public void run() {
        List<String> doneIds = new ArrayList<>();
        for (Queue queue : plugin.getBackend().getQueues()) {
            log.info("Processing of command: " + queue.getCommand());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), queue.getCommand());
            doneIds.add(queue.getId());
        }
        plugin.getBackend().removeQueues(doneIds);
    }
}
