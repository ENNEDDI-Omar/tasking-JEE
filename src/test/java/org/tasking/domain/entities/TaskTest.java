package org.tasking.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tasking.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private User manager;
    private User assignedUser;
    private List<Tag> tags;
    private LocalDateTime dueDate;
    private Task task;

    @BeforeEach
    void setUp() {
        // Création d'un manager
        Role managerRole = new Role();
        managerRole.setName("MANAGER");
        manager = new User();
        manager.setId(1L);
        manager.setUsername("manager1");
        manager.setEmail("manager@test.com");
        manager.setRole(managerRole);

        // Création d'un utilisateur assigné
        Role userRole = new Role();
        userRole.setName("USER");
        assignedUser = new User();
        assignedUser.setId(2L);
        assignedUser.setUsername("user1");
        assignedUser.setEmail("user@test.com");
        assignedUser.setRole(userRole);

        // Création des tags
        tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("urgent");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("backend");
        tags.add(tag1);
        tags.add(tag2);

        // Définition de la date d'échéance
        dueDate = LocalDateTime.now().plusDays(2);

        // Création de la tâche
        task = new Task(
                "Test Task",
                "Test Description",
                dueDate,
                assignedUser,
                manager,
                tags
        );
    }

    @Test
    void testTaskCreation() {
        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(manager, task.getCreatedBy());
        assertEquals(assignedUser, task.getAssignedUser());
        assertNotNull(task.getCreationDate());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(2, task.getTags().size());
    }

    @Test
    void testAddTag() {
        Tag newTag = new Tag();
        newTag.setId(3L);
        newTag.setName("frontend");

        int initialSize = task.getTags().size();
        task.addTag(newTag);

        assertEquals(initialSize + 1, task.getTags().size());
        assertTrue(task.getTags().contains(newTag));
    }

    @Test
    void testRemoveTag() {
        Tag tagToRemove = tags.get(0);
        int initialSize = task.getTags().size();

        task.removeTag(tagToRemove);

        assertEquals(initialSize - 1, task.getTags().size());
        assertFalse(task.getTags().contains(tagToRemove));
    }
}