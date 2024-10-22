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
    private int replaceTokens;

    @Column(name = "delete_tokens")
    private int deleteTokens;

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

    public boolean hasEnoughReplaceTokens() {
        return this.replaceTokens > 0;
    }

    public boolean hasEnoughDeleteTokens() {
        return this.deleteTokens > 0;
    }

    public void useReplaceToken() {
        if (this.replaceTokens > 0) {
            this.replaceTokens--;
        } else {
            throw new IllegalStateException("No replace tokens available");
        }
    }

    public void useDeleteToken() {
        if (this.deleteTokens > 0) {
            this.deleteTokens--;
        } else {
            throw new IllegalStateException("No delete tokens available");
        }
    }

    public void resetTokens() {
        this.replaceTokens = 2;  // 2 jetons de remplacement par jour
        this.deleteTokens = 1;   // 1 jeton de suppression par mois
        this.lastTokenReset = LocalDateTime.now();
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
