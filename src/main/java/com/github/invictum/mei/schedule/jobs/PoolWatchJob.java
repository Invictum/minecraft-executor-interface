package com.github.invictum.mei.schedule.jobs;

import com.github.invictum.mei.Backend;
import com.github.invictum.mei.command.Command;
import com.github.invictum.mei.command.Result;
import com.github.invictum.mei.dtos.Queue;
import org.knowm.sundial.Job;
import org.knowm.sundial.exceptions.JobInterruptException;

import java.util.ArrayList;
import java.util.List;

import static com.github.invictum.mei.command.Result.ALL_OK;
import static com.github.invictum.mei.command.Result.CONDITION_ERROR;

public class PoolWatchJob extends Job {

    @Override
    public void doRun() throws JobInterruptException {
        List<String> doneIds = new ArrayList<>();
        for (Queue queue : Backend.getInstance().getQueues()) {
            Command command = new Command(queue.getCommand(), queue.getConditions());
            Result result = command.execute();
            if (result == CONDITION_ERROR || result == ALL_OK) {
                doneIds.add(queue.getId());
            }
        }
        Backend.getInstance().removeQueues(doneIds);
    }
}
