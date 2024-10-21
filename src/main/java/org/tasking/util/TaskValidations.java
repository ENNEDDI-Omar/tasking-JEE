package org.tasking.util;

import org.tasking.domain.entities.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskValidations {

    public static List<String> validateTask(Task task) {
        List<String> errors = new ArrayList<>();

        if (task == null) {
            errors.add("Task cannot be null");
            return errors;
        }

        if (!isValidTitle(task.getTitle())) {
            errors.add("Invalid title: must not be empty and should be less than 100 characters");
        }

        if (!isValidDueDate(task.getDueDate())) {
            errors.add("Invalid due date: must be in the future");
        }

        if (!isValidTags(task.getTags())) {
            errors.add("Invalid tags: must have at least one tag and no more than 5 tags");
        }

        if (task.getAssignedUser() == null) {
            errors.add("Task must be assigned to a user");
        }

        if (task.getCreatedBy() == null) {
            errors.add("Task must have a creator");
        }

        return errors;
    }

    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty() && title.length() <= 100;
    }

    public static boolean isValidDueDate(LocalDateTime dueDate) {
        return dueDate != null && dueDate.isAfter(LocalDateTime.now());
    }

    public static boolean isValidTags(List<String> tags) {
        return tags != null && !tags.isEmpty() && tags.size() <= 5;
    }
}
