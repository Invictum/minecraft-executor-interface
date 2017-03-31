package com.github.invictum.mei.schedule;

import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.schedule.jobs.PoolWatchJob;
import org.knowm.sundial.SundialJobScheduler;

public class ScheduleFacility {

    private static final String POOL_WATCH_JOB = "PoolWatchJob";

    public static void start() {
        SundialJobScheduler.startScheduler();
        MeiPlugin.log().info("Scheduler facility has started");
        int interval = MeiPlugin.getPlugin(MeiPlugin.class).getConfig().getInt("interval", 60);
        SundialJobScheduler.addJob(POOL_WATCH_JOB, PoolWatchJob.class);
        SundialJobScheduler.addSimpleTrigger(POOL_WATCH_JOB, POOL_WATCH_JOB, -1, interval * 1000);
    }

    public static void shutdown() {
        if (SundialJobScheduler.getScheduler() != null) {
            SundialJobScheduler.shutdown();
            MeiPlugin.log().info("Scheduler facility has stopped");
        }
    }
}
