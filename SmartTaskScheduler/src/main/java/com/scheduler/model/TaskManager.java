package com.scheduler.model;

import java.time.LocalDate;
import java.util.*;

public class TaskManager {
    private final PriorityQueue<Task> queue = new PriorityQueue<>();

    public void addTask(Task task) {
        queue.add(task);
    }

    public void removeTask(Task task) {
        queue.remove(task);
    }

    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>(queue);
        Collections.sort(list);
        return list;
    }

    public List<Task> getTasksForDate(LocalDate date) {
        List<Task> filtered = new ArrayList<>();
        for (Task t : queue) {
            if (date.equals(t.getDeadline())) filtered.add(t);
        }
        return filtered;
    }
}
