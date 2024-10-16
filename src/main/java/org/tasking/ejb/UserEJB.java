package org.tasking.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.tasking.domain.entities.User;
import org.tasking.exceptions.UserException;
import org.tasking.util.UserValidation;
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
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
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

}

