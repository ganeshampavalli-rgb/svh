package com.svh.course.controller;

import com.svh.course.dto.CourseDTO;
import com.svh.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        log.info("Creating course: {}", courseDTO.getTitle());
        CourseDTO createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        log.info("Fetching course: {}", id);
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) {
        log.info("Updating course: {}", id);
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.info("Deleting course: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/published")
    public ResponseEntity<Page<CourseDTO>> getPublishedCourses(Pageable pageable) {
        log.info("Fetching published courses");
        Page<CourseDTO> courses = courseService.getPublishedCourses(pageable);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<CourseDTO>> getCoursesByCategory(
            @PathVariable String category,
            Pageable pageable) {
        log.info("Fetching courses by category: {}", category);
        Page<CourseDTO> courses = courseService.getCoursesByCategory(category, pageable);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/level/{level}")
    public ResponseEntity<Page<CourseDTO>> getCoursesByLevel(
            @PathVariable String level,
            Pageable pageable) {
        log.info("Fetching courses by level: {}", level);
        Page<CourseDTO> courses = courseService.getCoursesByLevel(level, pageable);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<CourseDTO>> getInstructorCourses(@PathVariable Long instructorId) {
        log.info("Fetching courses for instructor: {}", instructorId);
        List<CourseDTO> courses = courseService.getInstructorCourses(instructorId);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Course Service is running");
    }
}

