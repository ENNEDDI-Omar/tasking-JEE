package org.tasking.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tasking.domain.entities.Task;
import org.tasking.domain.entities.User;
import org.tasking.domain.entities.Tag;
import org.tasking.domain.enums.TaskStatus;
import org.tasking.exceptions.TaskException;
import org.tasking.util.TaskValidations;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TaskEJB {

    @PersistenceContext
    private EntityManager em;

    public Task createTask(Task task, User creator) throws TaskException {
        if (!"MANAGER".equals(creator.getRole().getName())) {
            throw new TaskException("Only managers can create tasks");
        }

        List<String> errors = TaskValidations.validateTask(task);
        if (!errors.isEmpty()) {
            throw new TaskException(String.join(", ", errors));
        }

        if (task.getDueDate().isAfter(LocalDateTime.now().plusDays(3))) {
            throw new TaskException("Task due date cannot be more than 3 days in the future");
        }

        task.setCreatedBy(creator);
        task.setCreationDate(LocalDateTime.now());
        task.setStatus(TaskStatus.TODO);
        em.persist(task);
        return task;
    }

    public Task assignTask(Long taskId, User assignee, User manager) throws TaskException {
        Task task = em.find(Task.class, taskId);
        if (task == null) {
            throw new TaskException("Task not found");
        }

        if (!"MANAGER".equals(manager.getRole().getName())) {
            throw new TaskException("Only managers can assign tasks");
        }

        task.setAssignedUser(assignee);
        return em.merge(task);
    }

    public Task completeTask(Long taskId, User user) throws TaskException {
        Task task = em.find(Task.class, taskId);
        if (task == null) {
            throw new TaskException("Task not found");
        }

        if (!task.getAssignedUser().equals(user)) {
            throw new TaskException("You can only complete tasks assigned to you");
        }

        if (LocalDateTime.now().isAfter(task.getDueDate())) {
            throw new TaskException("Task cannot be completed after due date");
        }

        task.setStatus(TaskStatus.DONE);
        return em.merge(task);
    }

    public List<Task> getTasksByUser(User user) {
        return em.createQuery("SELECT t FROM Task t WHERE t.assignedUser = :user", Task.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Task> getAllTasks() {
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    public Task replaceTask(Long taskId, Task newTask, User user) throws TaskException {
        Task existingTask = em.find(Task.class, taskId);
        if (existingTask == null) {
            throw new TaskException("Task not found");
        }

        if (!"MANAGER".equals(user.getRole().getName())) {
            // Implement token logic here
            // Check if user has enough tokens
            // Deduct a token if the operation is successful
            // Throw InsufficientTokensException if not enough tokens
        }

        List<String> errors = TaskValidations.validateTask(newTask);
        if (!errors.isEmpty()) {
            throw new TaskException(String.join(", ", errors));
        }

        existingTask.setTitle(newTask.getTitle());
        existingTask.setDescription(newTask.getDescription());
        existingTask.setDueDate(newTask.getDueDate());
        existingTask.setTags(newTask.getTags());
        existingTask.setAssignedUser(newTask.getAssignedUser());

        return em.merge(existingTask);
    }

    public void deleteTask(Long taskId, User user) throws TaskException {
        Task task = em.find(Task.class, taskId);
        if (task == null) {
            throw new TaskException("Task not found");
        }

        if (!"MANAGER".equals(user.getRole().getName()) && !task.getCreatedBy().equals(user)) {
            // Implement token logic for deletion
            // Check if user has enough tokens for deletion
            // Deduct a token if the operation is successful
            // Don't deduct token if the user is deleting their own task
        }

        em.remove(task);
    }
}