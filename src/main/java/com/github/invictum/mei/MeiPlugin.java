package com.github.invictum.mei;

import com.github.invictum.mei.backend.BackendProvider;
import com.github.invictum.mei.channel.ChannelsFacility;
import com.github.invictum.mei.command.Command;
import com.github.invictum.mei.command.Result;
import com.github.invictum.mei.entity.TaskEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MeiPlugin extends JavaPlugin {

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ChannelsFacility channelsFacility;

    @Override
    public void onEnable() {
        /* Prepare configuration */
        saveDefaultConfig();
        getConfig().options().copyHeader(true).copyDefaults(true);
        saveConfig();
        /* Start executor facility */
        long delay = Long.valueOf(getConfig().getString("interval"));
        executor.scheduleWithFixedDelay(() -> {
            for (TaskEntity task : BackendProvider.get().list()) {
                Result result = new Command(task).execute();
                if (result == Result.ALL_OK || result == Result.CONDITION_ERROR) {
                    BackendProvider.get().delete(task.getId());
                }
            }
        }, 0, delay, TimeUnit.MILLISECONDS);

        /* Start channels */
        channelsFacility = new ChannelsFacility(getConfig());
        channelsFacility.start();
    }

    @Override
    public void onDisable() {
        channelsFacility.shutdown();
        executor.shutdown();
    }
}
