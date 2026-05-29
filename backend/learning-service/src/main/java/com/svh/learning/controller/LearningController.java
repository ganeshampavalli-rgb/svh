package com.svh.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learning")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class LearningController {

    @GetMapping("/progress/{enrollmentId}")
    public ResponseEntity<String> getProgress(@PathVariable Long enrollmentId) {
        log.info("Fetching progress for enrollment: {}", enrollmentId);
        // Implementation will include progress tracking from student_progress table
        return ResponseEntity.ok("{\"message\": \"Progress tracking endpoint\"}");
    }

    @PostMapping("/progress/{enrollmentId}/update")
    public ResponseEntity<String> updateProgress(@PathVariable Long enrollmentId, @RequestBody String progressData) {
        log.info("Updating progress for enrollment: {}", enrollmentId);
        // Implementation will update student_progress table
        return ResponseEntity.ok("{\"message\": \"Progress updated\"}");
    }

    @GetMapping("/assignments/{lessonId}")
    public ResponseEntity<String> getAssignments(@PathVariable Long lessonId) {
        log.info("Fetching assignments for lesson: {}", lessonId);
        // Implementation will fetch from assignments table
        return ResponseEntity.ok("{\"message\": \"Assignments endpoint\"}");
    }

    @PostMapping("/assignments/{assignmentId}/submit")
    public ResponseEntity<String> submitAssignment(@PathVariable Long assignmentId, @RequestBody String submission) {
        log.info("Submitting assignment: {}", assignmentId);
        // Implementation will save to submissions table
        return ResponseEntity.ok("{\"message\": \"Assignment submitted\"}");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Learning Service is running");
    }
}

