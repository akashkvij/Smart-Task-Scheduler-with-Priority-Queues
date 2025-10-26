package com.scheduler;

import com.scheduler.model.Task;
import com.scheduler.model.TaskManager;
import com.scheduler.ui.TaskCell;
import com.scheduler.ui.TaskDialog;
import com.scheduler.util.Storage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class MainApp extends Application {

    private final TaskManager taskManager = new TaskManager();
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        taskList.addAll(Storage.loadTasks());

        ListView<Task> listView = new ListView<>(taskList);
        listView.setCellFactory(param -> new TaskCell());

        Button addBtn = new Button("Add Task");
        addBtn.setOnAction(e -> {
            TaskDialog dialog = new TaskDialog();
            Task task = dialog.showDialog();
            if (task != null) {
                taskManager.addTask(task);
                taskList.setAll(taskManager.getTasks());
                Storage.saveTasks(taskManager.getTasks());
            }
        });

        Button deleteBtn = new Button("Delete Task");
        deleteBtn.setOnAction(e -> {
            Task selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                taskManager.removeTask(selected);
                taskList.setAll(taskManager.getTasks());
                Storage.saveTasks(taskManager.getTasks());
            }
        });

        Button filterBtn = new Button("Show Today’s Tasks");
        filterBtn.setOnAction(e -> {
            LocalDate today = LocalDate.now();
            taskList.setAll(taskManager.getTasksForDate(today));
        });

        ToolBar toolBar = new ToolBar(addBtn, deleteBtn, filterBtn);

        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setCenter(listView);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Smart Task Scheduler");
        stage.setScene(scene);
        stage.show();

        startReminders();
    }

    private void startReminders() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Task task : taskManager.getTasks()) {
                    if (task.isDueSoon()) {
                        System.out.println("Reminder: Task \"" + task.getTitle() + "\" is due soon!");
                    }
                }
            }
        }, 0, 60_000);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

