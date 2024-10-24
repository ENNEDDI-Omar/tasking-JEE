package org.tasking.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.tasking.domain.entities.User;
import org.tasking.domain.entities.Role;
import org.tasking.exceptions.AuthException;
import org.tasking.util.AuthValidation;

import javax.security.auth.login.LoginException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
                .setParameter("name", "USER")  // Changé à USER par défaut
                .getSingleResult();
        user.setRole(userRole);
        user.initializeTokens();  // Initialise les tokens selon le rôle
        userEJB.createUser(user);
    }


    public User login(String username, String password) throws AuthException {
        try {
            User user = userEJB.findUserByUsername(username);
            if (user != null && verifyPassword(password, user.getPassword())) {
                return user;
            }
            throw new AuthException("Invalid username or password");
        } catch (Exception e) {
            throw new AuthException("Login failed: " + e.getMessage());
        }
    }

    public void logout(HttpSession session) {
        try {
            if (session != null) {
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    // Vous pouvez ajouter ici la logique pour enregistrer la dernière déconnexion
                    // user.setLastLogout(LocalDateTime.now());
                    // em.merge(user);
                }
                session.removeAttribute("user");
                session.invalidate();
            }
        } catch (Exception e) {
            // Log l'erreur et potentiellement la relancer
            e.printStackTrace();
            throw new AuthException("Logout failed: " + e.getMessage());
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }
}