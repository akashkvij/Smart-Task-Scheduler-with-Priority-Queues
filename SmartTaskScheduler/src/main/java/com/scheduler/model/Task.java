package com.scheduler.model;

import java.time.LocalDate;

public class Task implements Comparable<Task> {
    private String title;
    private String priority;  // High, Medium, Low
    private LocalDate deadline;

    public Task(String title, String priority, LocalDate deadline) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getTitle() { return title; }
    public String getPriority() { return priority; }
    public LocalDate getDeadline() { return deadline; }

    public void setTitle(String title) { this.title = title; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public boolean isDueSoon() {
        return deadline != null && !deadline.isBefore(LocalDate.now()) && deadline.isBefore(LocalDate.now().plusDays(1));
    }

    @Override
    public int compareTo(Task other) {
        int priorityCompare = other.priorityLevel() - this.priorityLevel();
        if (priorityCompare == 0)
            return this.deadline.compareTo(other.deadline);
        return priorityCompare;
    }

    private int priorityLevel() {
        return switch (priority.toLowerCase()) {
            case "high" -> 3;
            case "medium" -> 2;
            case "low" -> 1;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return title + " (" + priority + ") - " + deadline;
    }
}
