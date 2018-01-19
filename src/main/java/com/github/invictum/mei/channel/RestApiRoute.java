package com.github.invictum.mei.channel;

import com.github.invictum.mei.Json;
import com.github.invictum.mei.backend.BackendProvider;
import com.github.invictum.mei.command.Command;
import com.github.invictum.mei.entity.TaskEntity;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class RestApiRoute implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        TaskEntity task = Json.get().fromJson(request.body(), TaskEntity.class);
        if (task.getCommand() == null) {
            return Spark.halt(500);
        }
        switch (new Command(task).execute()) {
            case ALL_OK:
                response.status(200);
                break;
            case CONDITION_FAIL:
                response.status(201);
                BackendProvider.get().store(task);
                break;
            case CONDITION_ERROR:
                response.status(403);
                break;
        }
        return "";
    }
}
