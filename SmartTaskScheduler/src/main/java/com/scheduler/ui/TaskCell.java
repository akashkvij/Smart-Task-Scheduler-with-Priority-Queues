package com.scheduler.ui;

import com.scheduler.model.Task;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TaskCell extends ListCell<Task> {
    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty || task == null) {
            setText(null);
        } else {
            Text text = new Text(task.toString());
            switch (task.getPriority().toLowerCase()) {
                case "high" -> text.setFill(Color.RED);
                case "medium" -> text.setFill(Color.ORANGE);
                default -> text.setFill(Color.GREEN);
            }
            setGraphic(text);
        }
    }
}
