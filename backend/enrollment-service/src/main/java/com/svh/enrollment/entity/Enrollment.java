package com.svh.enrollment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long courseId;

    private LocalDateTime enrollmentDate;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer completionPercentage;

    private LocalDateTime lastAccessed;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        enrollmentDate = LocalDateTime.now();
        createdAt = LocalDateTime.now();
        status = EnrollmentStatus.ACTIVE;
        completionPercentage = 0;
    }

    public enum EnrollmentStatus {
        ACTIVE, COMPLETED, DROPPED, PAUSED
    }
}

