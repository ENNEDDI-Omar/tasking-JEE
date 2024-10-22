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

    public Task createTask(Task task, User creator) throws TaskException
    {
        if (!"MANAGER".equals(creator.getRole().getName())) {
            throw new TaskException("Only managers can create tasks");
        }
        List<String> errors = TaskValidations.validateTask(task);
        if (!errors.isEmpty()) {
            throw new TaskException(String.join(", ", errors));
        }
        if (task.getCreationDate().isBefore(LocalDateTime.now())) {
            throw new TaskException("Task cannot be created in the past");
        }
        if (task.getDueDate().isAfter(LocalDateTime.now().plusDays(3))) {
            throw new TaskException("Task due date cannot be more than 3 days in the future");
        }
        if (task.getTags().size() < 2 || task.getTags().size() > 5) {
            throw new TaskException("A task must have between 2 and 5 tags");
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

    public Task assignTaskToSelf(Long taskId, User user) throws TaskException {
        Task task = em.find(Task.class, taskId);
        if (task == null) {
            throw new TaskException("Task not found");
        }

        if (task.getAssignedUser() != null) {
            throw new TaskException("This task is already assigned");
        }

        task.setAssignedUser(user);
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
            if (!user.hasEnoughReplaceTokens()) {
                throw new TaskException("Insufficient tokens to replace task");
            }
            user.useReplaceToken();
            em.merge(user);  // Mise à jour de l'utilisateur dans la base de données
        }

        List<String> errors = TaskValidations.validateTask(newTask);
        if (!errors.isEmpty()) {
            throw new TaskException(String.join(", ", errors));
        }

        if (newTask.getDueDate().isAfter(LocalDateTime.now().plusDays(3))) {
            throw new TaskException("Task due date cannot be more than 3 days in the future");
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

        if (!"MANAGER".equals(user.getRole().getName())) {
            if (!task.getCreatedBy().equals(user)) {
                if (!user.hasEnoughDeleteTokens()) {
                    throw new TaskException("Insufficient tokens to delete task");
                }
                user.useDeleteToken();
                em.merge(user);  // Mise à jour de l'utilisateur dans la base de données
            }
        }

        em.remove(task);
    }



    public void markTasksAsUnfinished() {
        LocalDateTime now = LocalDateTime.now();
        em.createQuery("UPDATE Task t SET t.status = :unfinished WHERE t.status != :done AND t.dueDate < :now")
                .setParameter("unfinished", TaskStatus.TODO)
                .setParameter("done", TaskStatus.DONE)
                .setParameter("now", now)
                .executeUpdate();
    }


}