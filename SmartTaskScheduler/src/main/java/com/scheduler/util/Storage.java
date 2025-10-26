package com.scheduler.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scheduler.model.Task;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String FILE_PATH = "tasks.json";

    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new Gson().toJson(tasks, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
