package com.svh.enrollment.controller;

import com.svh.enrollment.dto.EnrollmentDTO;
import com.svh.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enrollCourse(@Valid @RequestBody EnrollmentDTO dto) {
        log.info("Enrolling student {} in course {}", dto.getStudentId(), dto.getCourseId());
        EnrollmentDTO enrollment = enrollmentService.enrollCourse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentDTO dto) {
        log.info("Updating enrollment: {}", id);
        EnrollmentDTO updated = enrollmentService.updateEnrollment(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getStudentEnrollments(@PathVariable Long studentId) {
        log.info("Fetching enrollments for student: {}", studentId);
        List<EnrollmentDTO> enrollments = enrollmentService.getStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentDTO>> getCourseEnrollments(@PathVariable Long courseId) {
        log.info("Fetching enrollments for course: {}", courseId);
        List<EnrollmentDTO> enrollments = enrollmentService.getCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Enrollment Service is running");
    }
}

