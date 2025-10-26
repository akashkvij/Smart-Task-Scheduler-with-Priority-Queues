package com.scheduler.ui;

import com.scheduler.model.Task;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.time.LocalDate;

public class TaskDialog {
    public Task showDialog() {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Add Task");

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();

        Label priorityLabel = new Label("Priority:");
        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("High", "Medium", "Low");

        Label deadlineLabel = new Label("Deadline:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(priorityLabel, 0, 1);
        grid.add(priorityBox, 1, 1);
        grid.add(deadlineLabel, 0, 2);
        grid.add(datePicker, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(new Callback<ButtonType, Task>() {
            @Override
            public Task call(ButtonType button) {
                if (button == ButtonType.OK) {
                    return new Task(
                            titleField.getText(),
                            priorityBox.getValue(),
                            datePicker.getValue()
                    );
                }
                return null;
            }
        });

        return dialog.showAndWait().orElse(null);
    }
}
