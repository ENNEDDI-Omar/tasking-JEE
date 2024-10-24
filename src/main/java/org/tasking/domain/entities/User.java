package org.tasking.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "replace_tokens")
    private Integer replaceTokens = 0;  // Initialisation par défaut à 0

    @Column(name = "delete_tokens")
    private Integer deleteTokens = 0;   // Initialisation par défaut à 0

    @Column(name = "last_token_reset")
    private LocalDateTime lastTokenReset;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void initializeTokens() {
        if (this.role != null && "USER".equals(this.role.getName())) {
            this.replaceTokens = 2;
            this.deleteTokens = 1;
            this.lastTokenReset = LocalDateTime.now();
        }
    }

    public boolean hasEnoughReplaceTokens() {
        return "USER".equals(this.role.getName()) && this.replaceTokens > 0;
    }

    public boolean hasEnoughDeleteTokens() {
        return "USER".equals(this.role.getName()) && this.deleteTokens > 0;
    }

    public void useReplaceToken() {
        if (!hasEnoughReplaceTokens()) {
            throw new IllegalStateException("No replace tokens available or not a USER");
        }
        this.replaceTokens--;
    }

    public void useDeleteToken() {
        if (!hasEnoughDeleteTokens()) {
            throw new IllegalStateException("No delete tokens available or not a USER");
        }
        this.deleteTokens--;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role.getName() + '\'' +
                '}';
    }
}
