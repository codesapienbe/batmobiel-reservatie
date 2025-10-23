package io.github.codesapienbe.user;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {@Index(columnList = "username", unique = true)})
public class User {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    protected User() {}

    public User(UUID id, String username, String passwordHash) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
}


