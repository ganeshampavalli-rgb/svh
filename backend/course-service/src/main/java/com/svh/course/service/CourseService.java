package com.svh.course.service;

import com.svh.course.dto.CourseDTO;
import com.svh.course.entity.Course;
import com.svh.course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = mapDTOToEntity(courseDTO);
        course = courseRepository.save(course);
        log.info("Course created: {} by instructor: {}", course.getTitle(), course.getInstructorId());
        return mapEntityToDTO(course);
    }
    
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        if (courseOpt.isEmpty()) {
            throw new RuntimeException("Course not found: " + id);
        }
        
        Course course = courseOpt.get();
        if (courseDTO.getTitle() != null) course.setTitle(courseDTO.getTitle());
        if (courseDTO.getDescription() != null) course.setDescription(courseDTO.getDescription());
        if (courseDTO.getLevel() != null) course.setLevel(Course.CourseLevel.valueOf(courseDTO.getLevel()));
        if (courseDTO.getCategory() != null) course.setCategory(courseDTO.getCategory());
        if (courseDTO.getThumbnailUrl() != null) course.setThumbnailUrl(courseDTO.getThumbnailUrl());
        if (courseDTO.getCoverImageUrl() != null) course.setCoverImageUrl(courseDTO.getCoverImageUrl());
        if (courseDTO.getPrice() != null) course.setPrice(courseDTO.getPrice());
        if (courseDTO.getIsPublished() != null) course.setIsPublished(courseDTO.getIsPublished());
        if (courseDTO.getDurationHours() != null) course.setDurationHours(courseDTO.getDurationHours());
        
        course = courseRepository.save(course);
        log.info("Course updated: {} (ID: {})", course.getTitle(), course.getId());
        return mapEntityToDTO(course);
    }
    
    public CourseDTO getCourseById(Long id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        if (courseOpt.isEmpty()) {
            throw new RuntimeException("Course not found: " + id);
        }
        return mapEntityToDTO(courseOpt.get());
    }
    
    public Page<CourseDTO> getPublishedCourses(Pageable pageable) {
        return courseRepository.findByIsPublishedTrue(pageable)
                .map(this::mapEntityToDTO);
    }
    
    public Page<CourseDTO> getCoursesByCategory(String category, Pageable pageable) {
        return courseRepository.findByCategory(category, pageable)
                .map(this::mapEntityToDTO);
    }
    
    public Page<CourseDTO> getCoursesByLevel(String level, Pageable pageable) {
        try {
            Course.CourseLevel courseLevel = Course.CourseLevel.valueOf(level);
            return courseRepository.findByLevel(courseLevel, pageable)
                    .map(this::mapEntityToDTO);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid course level: " + level);
        }
    }
    
    public List<CourseDTO> getInstructorCourses(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found: " + id);
        }
        courseRepository.deleteById(id);
        log.info("Course deleted: {}", id);
    }
    
    private CourseDTO mapEntityToDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructorId(course.getInstructorId())
                .level(course.getLevel().name())
                .category(course.getCategory())
                .thumbnailUrl(course.getThumbnailUrl())
                .coverImageUrl(course.getCoverImageUrl())
                .price(course.getPrice())
                .isPublished(course.getIsPublished())
                .durationHours(course.getDurationHours())
                .rating(course.getRating())
                .totalEnrollments(course.getTotalEnrollments())
                .build();
    }
    
    private Course mapDTOToEntity(CourseDTO courseDTO) {
        return Course.builder()
                .title(courseDTO.getTitle())
                .description(courseDTO.getDescription())
                .instructorId(courseDTO.getInstructorId())
                .level(Course.CourseLevel.valueOf(courseDTO.getLevel()))
                .category(courseDTO.getCategory())
                .thumbnailUrl(courseDTO.getThumbnailUrl())
                .coverImageUrl(courseDTO.getCoverImageUrl())
                .price(courseDTO.getPrice())
                .isPublished(courseDTO.getIsPublished() != null ? courseDTO.getIsPublished() : false)
                .durationHours(courseDTO.getDurationHours())
                .build();
    }
}

