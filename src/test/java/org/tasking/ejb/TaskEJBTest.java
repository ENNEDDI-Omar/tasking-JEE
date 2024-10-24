package org.tasking.ejb;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tasking.domain.entities.Role;
import org.tasking.domain.entities.Tag;
import org.tasking.domain.entities.Task;
import org.tasking.domain.entities.User;
import org.tasking.domain.enums.TaskStatus;
import org.tasking.exceptions.TaskException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskEJBTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private TaskEJB taskEJB;

    private User manager;
    private User normalUser;
    private Task task;
    private List<Tag> tags;

    @BeforeEach
    void setUp() {
        // Configuration du manager
        Role managerRole = new Role();
        managerRole.setName("MANAGER");
        manager = new User();
        manager.setId(1L);
        manager.setUsername("manager");
        manager.setRole(managerRole);

        // Configuration de l'utilisateur normal
        Role userRole = new Role();
        userRole.setName("USER");
        normalUser = new User();
        normalUser.setId(2L);
        normalUser.setUsername("user");
        normalUser.setRole(userRole);

        // Configuration des tags
        tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("urgent");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("backend");
        tags.add(tag1);
        tags.add(tag2);

        // Configuration de la tâche
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(LocalDateTime.now().plusDays(2));
        task.setCreatedBy(manager);
        task.setAssignedUser(normalUser);
        task.setTags(tags);
    }

    @Test
    void testCreateTask_ByManager_Success() {
        // Given
        doNothing().when(em).persist(any(Task.class));

        // When
        Task createdTask = taskEJB.createTask(task, manager);

        // Then
        assertNotNull(createdTask);
        assertEquals(TaskStatus.TODO, createdTask.getStatus());
        verify(em).persist(any(Task.class));
    }

    @Test
    void testCreateTask_ByNormalUser_ThrowsException() {
        // When/Then
        assertThrows(TaskException.class, () -> {
            taskEJB.createTask(task, normalUser);
        });
        verify(em, never()).persist(any(Task.class));
    }

    @Test
    void testCreateTask_WithInvalidDueDate_ThrowsException() {
        // Given
        task.setDueDate(LocalDateTime.now().plusDays(4));

        // When/Then
        assertThrows(TaskException.class, () -> {
            taskEJB.createTask(task, manager);
        });
        verify(em, never()).persist(any(Task.class));
    }

    @Test
    void testCreateTask_WithInvalidTags_ThrowsException() {
        // Given
        task.setTags(new ArrayList<>());  // Aucun tag

        // When/Then
        assertThrows(TaskException.class, () -> {
            taskEJB.createTask(task, manager);
        });
        verify(em, never()).persist(any(Task.class));
    }

    @Test
    void testAssignTaskToSelf_Success() {
        // Given
        Task unassignedTask = new Task();
        unassignedTask.setId(1L);
        unassignedTask.setAssignedUser(null);
        when(em.find(Task.class, 1L)).thenReturn(unassignedTask);
        when(em.merge(any(Task.class))).thenReturn(unassignedTask);

        // When
        Task result = taskEJB.assignTaskToSelf(1L, normalUser);

        // Then
        assertNotNull(result);
        assertEquals(normalUser, result.getAssignedUser());
        verify(em).merge(any(Task.class));
    }

    @Test
    void testCompleteTask_BeforeDeadline_Success() {
        // Given
        task.setAssignedUser(normalUser);
        when(em.find(Task.class, 1L)).thenReturn(task);
        when(em.merge(any(Task.class))).thenReturn(task);

        // When
        Task result = taskEJB.completeTask(1L, normalUser);

        // Then
        assertNotNull(result);
        assertEquals(TaskStatus.DONE, result.getStatus());
        verify(em).merge(any(Task.class));
    }

    @Test
    void testCompleteTask_AfterDeadline_ThrowsException() {
        // Given
        task.setDueDate(LocalDateTime.now().minusDays(1));
        task.setAssignedUser(normalUser);
        when(em.find(Task.class, 1L)).thenReturn(task);

        // When/Then
        assertThrows(TaskException.class, () -> {
            taskEJB.completeTask(1L, normalUser);
        });
        verify(em, never()).merge(any(Task.class));
    }
}