package com.github.invictum.mei.backend;

import com.github.invictum.mei.Json;
import com.github.invictum.mei.MeiPlugin;
import com.github.invictum.mei.Utils;
import com.github.invictum.mei.entity.TaskEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stores tasks details into separate json files in specified directory on filesystem
 */
public class Filesystem implements Backend {

    private static final String DIRECTORY_OPTION = "directory";
    private static final String EXT = "json";

    private static final Logger LOGGER = JavaPlugin.getPlugin(MeiPlugin.class).getLogger();
    private Path root;

    public Filesystem(Map<String, Object> options) {
        /* Check directory option */
        try {
            root = Paths.get((String) options.get(DIRECTORY_OPTION));
        } catch (InvalidPathException e) {
            LOGGER.warning("Unable to determinate directory. Check configuration file");
            Utils.disablePlugin();
        }
        /* Create storage directory */
        if (Files.notExists(root)) {
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                LOGGER.warning("Unable to create directory at " + root.toString() + " Check permissions");
                Utils.disablePlugin();
            }
        }
    }

    @Override
    public void store(TaskEntity task) {
        Path path = root.resolve(UUID.randomUUID().toString() + "." + EXT);
        try {
            Files.createFile(path);
            Files.write(path, Json.get().toJson(task).getBytes());
        } catch (IOException e) {
            LOGGER.warning("Failed to store new task with " + task.getId() + " id");
        }
    }

    @Override
    public Set<TaskEntity> list() {
        try {
            return Files.list(root).filter(path -> path.toString().endsWith(EXT)).map(path -> {
                try {
                    byte[] data = Files.readAllBytes(path);
                    TaskEntity task = Json.get().fromJson(new String(data), TaskEntity.class);
                    task.setId(path.getFileName().toString());
                    return task;
                } catch (Exception e) {
                    LOGGER.warning("Unable to parse " + path.getFileName().toString());
                    delete(path.getFileName().toString());
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.warning("Unable to list files in " + root.toString());
            return new HashSet<>();
        }
    }

    @Override
    public void delete(String... ids) {
        if (ids != null) {
            Stream.of(ids).map(id -> root.resolve(id)).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    LOGGER.warning("Unable to remove task with " + path.getFileName().toString() + " id");
                }
            });
        }
    }
}
