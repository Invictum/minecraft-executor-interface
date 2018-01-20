package com.github.invictum.mei.backend;

import com.github.invictum.mei.entity.TaskEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class FilesystemTest {

    @Rule
    public TemporaryFolder directory = new TemporaryFolder();

    private Backend backend;

    @Before
    public void beforeTest() {
        String path = directory.getRoot().getAbsolutePath();
        backend = new Filesystem(Collections.singletonMap("directory", path), Logger.getGlobal());
    }

    @Test
    public void listEmpty() {
        Assert.assertTrue(backend.list().isEmpty());
    }

    @Test
    public void listOnlyJson() throws IOException {
        Path file = Paths.get(directory.getRoot().getAbsolutePath()).resolve("file.txt");
        Files.createFile(file);
        Assert.assertTrue(backend.list().isEmpty());
    }

    @Test
    public void store() {
        backend.store(new TaskEntity());
        backend.store(new TaskEntity());
        Assert.assertEquals(2, backend.list().size());
    }

    @Test
    public void delete() {
        backend.store(new TaskEntity());
        backend.list().forEach(task -> backend.delete(task.getId()));
        Assert.assertTrue(backend.list().isEmpty());
    }

    @Test
    public void deleteNotExist() {
        backend.store(new TaskEntity());
        backend.delete("INVALID_ID");
        Assert.assertEquals(1, backend.list().size());
    }

    @Test
    public void deleteInvalid() throws IOException {
        Path file = Paths.get(directory.getRoot().getAbsolutePath()).resolve("task.json");
        Files.createFile(file);
        Files.write(file, "invalid_json".getBytes());
        Assert.assertTrue(backend.list().isEmpty());
    }
}
