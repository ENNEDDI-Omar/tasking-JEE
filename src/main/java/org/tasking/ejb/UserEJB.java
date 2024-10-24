package org.tasking.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.tasking.domain.entities.Role;
import org.tasking.domain.entities.TaskChangeRequest;
import org.tasking.domain.entities.User;
import org.tasking.exceptions.UserException;
import org.tasking.util.UserValidation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Stateless
public class UserEJB {
    @PersistenceContext(unitName = "TaskingPU")
    private EntityManager em;


    public User createUser(User user) throws UserException {
        if (UserValidation.isValidUser(user)) {
            em.persist(user);
            return user;
        }
        throw new UserException("Invalid user data and user creation failed");
    }

    public User findUserById(Long id) {
        return em.find(User.class, id);
    }

    public User findUserByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findAllUsers() {
        try {
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            System.out.println("Users found: " + users.size());
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public User updateUser(User user) throws UserException {
        if (UserValidation.isValidUser(user)) {
            return em.merge(user);
        }
        throw new UserException("Invalid user data and user update failed");
    }

    public void deleteUser(Long id) throws UserException {
        User user = findUserById(id);
        if (user != null) {
            em.remove(user);
        } else {
            throw new UserException("User not found");
        }
    }

    public List<Role> getAllRoles() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    public void resetUserTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<User> users = em.createQuery(
                        "SELECT u FROM User u WHERE u.role.name = :roleName", User.class)
                .setParameter("roleName", "USER")
                .getResultList();

        for (User user : users) {
            boolean updated = false;

            if (user.getLastTokenReset() == null ||
                    user.getLastTokenReset().isBefore(now.minusDays(1))) {
                user.setReplaceTokens(2);
                updated = true;
            }

            if (user.getLastTokenReset() == null ||
                    user.getLastTokenReset().isBefore(now.minusMonths(1))) {
                user.setDeleteTokens(1);
                updated = true;
            }

            if (updated) {
                user.setLastTokenReset(now);
                em.merge(user);
            }
        }
    }

    public void checkAndUpdatePendingTokens() {
        LocalDateTime twelveHoursAgo = LocalDateTime.now().minusHours(12);

        List<TaskChangeRequest> pendingRequests = em.createQuery(
                        "SELECT r FROM TaskChangeRequest r WHERE r.status = :status " +
                                "AND r.requestDate < :date " +
                                "AND r.requestedBy.role.name = :roleName",
                        TaskChangeRequest.class)
                .setParameter("status", "PENDING")
                .setParameter("date", twelveHoursAgo)
                .setParameter("roleName", "USER")
                .getResultList();

        for (TaskChangeRequest request : pendingRequests) {
            User user = request.getRequestedBy();
            if ("USER".equals(user.getRole().getName())) {
                user.setReplaceTokens(user.getReplaceTokens() * 2);
                em.merge(user);
            }
            request.setStatus("EXPIRED");
            em.merge(request);
        }
    }

}

