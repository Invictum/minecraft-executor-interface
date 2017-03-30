package com.github.invictum.mei.schedule;

import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.schedule.jobs.PoolWatchJob;
import org.knowm.sundial.SundialJobScheduler;

import java.util.logging.Logger;

public class ScheduleFacility {

    private static final String POOL_WATCH_JOB = "PoolWatchJob";
    private static final Logger LOG = MeiPlugin.getPlugin(MeiPlugin.class).getLogger();

    public static void start() {
        SundialJobScheduler.startScheduler();
        LOG.info("Scheduler facility has started");
        int interval = MeiPlugin.getPlugin(MeiPlugin.class).getConfig().getInt("interval", 60);
        SundialJobScheduler.addJob(POOL_WATCH_JOB, PoolWatchJob.class);
        SundialJobScheduler.addSimpleTrigger(POOL_WATCH_JOB, POOL_WATCH_JOB, -1, interval * 1000);
    }

    public static void shutdown() {
        SundialJobScheduler.shutdown();
        LOG.info("Scheduler facility has stopped");
    }
}
