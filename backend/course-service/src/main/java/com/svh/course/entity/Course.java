package com.svh.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long instructorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseLevel level;

    @Column(nullable = false)
    private String category;

    private String thumbnailUrl;

    private String coverImageUrl;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isPublished;

    private Integer durationHours;

    @Column(precision = 3, scale = 2)
    private Double rating;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer totalEnrollments;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        isPublished = false;
        totalEnrollments = 0;
        if (price == null) {
            price = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum CourseLevel {
        NURSERY, PRIMARY, SECONDARY, JNTU, GRADUATION, PROFESSIONAL
    }
}

