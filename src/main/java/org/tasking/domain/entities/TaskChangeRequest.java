package org.tasking.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_change_requests")
@Getter
@Setter
public class TaskChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "requested_by", nullable = false)
    private User requestedBy;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED, EXPIRED

    @Column(name = "request_type", nullable = false)
    private String requestType; // REPLACE, DELETE

    @Column
    private String description;

    // Constructeur par défaut
    public TaskChangeRequest() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDING";
    }
}