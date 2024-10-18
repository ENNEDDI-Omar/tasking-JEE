package org.tasking.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.tasking.domain.entities.User;
import org.tasking.domain.entities.Role;
import org.tasking.exceptions.AuthException;
import org.tasking.util.AuthValidation;

import javax.security.auth.login.LoginException;

@Stateless
public class AuthEJB {
    @PersistenceContext(unitName = "TaskingPU")
    private EntityManager em;

    @EJB
    private UserEJB userEJB;

    public void registerUser(String username, String email, String password) throws AuthException {
        if (!AuthValidation.isValidUsername(username)) {
            throw new AuthException("Invalid username format");
        }
        if (!AuthValidation.isValidEmail(email)) {
            throw new AuthException("Invalid email format");
        }
        if (!AuthValidation.isValidPassword(password)) {
            throw new AuthException("Invalid password format");
        }
        if (userEJB.findUserByUsername(username) != null) {
            throw new AuthException("Username already exists");
        }

        String hashedPassword = hashPassword(password);
        User user = new User(username, email, hashedPassword);
        Role userRole = em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", "USER")
                .getSingleResult();
        user.setRole(userRole);
        userEJB.createUser(user);
    }


    public User authenticateUser(String username, String password) throws LoginException {
        User user = userEJB.findUserByUsername(username);
        if (user != null && verifyPassword(password, user.getPassword())) {
            return user;
        }
        throw new LoginException("Invalid username or password");
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String inputPassword, String storedPassword) {
        return BCrypt.checkpw(inputPassword, storedPassword);
    }
}