package com.svh.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Instructor ID is required")
    private Long instructorId;
    
    @NotBlank(message = "Level is required")
    private String level;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    private String thumbnailUrl;
    
    private String coverImageUrl;
    
    private BigDecimal price;
    
    private Boolean isPublished;
    
    private Integer durationHours;
    
    private Double rating;
    
    private Integer totalEnrollments;
}

