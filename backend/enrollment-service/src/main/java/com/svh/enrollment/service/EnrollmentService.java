package com.svh.enrollment.service;

import com.svh.enrollment.dto.EnrollmentDTO;
import com.svh.enrollment.entity.Enrollment;
import com.svh.enrollment.repository.EnrollmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public EnrollmentDTO enrollCourse(EnrollmentDTO dto) {
        Optional<Enrollment> existing = enrollmentRepository.findByStudentIdAndCourseId(
                dto.getStudentId(), dto.getCourseId());

        if (existing.isPresent()) {
            throw new RuntimeException("Already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .studentId(dto.getStudentId())
                .courseId(dto.getCourseId())
                .build();

        enrollment = enrollmentRepository.save(enrollment);
        log.info("Student {} enrolled in course {}", dto.getStudentId(), dto.getCourseId());
        return mapEntityToDTO(enrollment);
    }

    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO dto) {
        Optional<Enrollment> opt = enrollmentRepository.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Enrollment not found");

        Enrollment enrollment = opt.get();
        if (dto.getStatus() != null) {
            enrollment.setStatus(Enrollment.EnrollmentStatus.valueOf(dto.getStatus()));
        }
        if (dto.getCompletionPercentage() != null) {
            enrollment.setCompletionPercentage(dto.getCompletionPercentage());
        }

        enrollment = enrollmentRepository.save(enrollment);
        return mapEntityToDTO(enrollment);
    }

    public List<EnrollmentDTO> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<EnrollmentDTO> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    private EnrollmentDTO mapEntityToDTO(Enrollment enrollment) {
        return EnrollmentDTO.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudentId())
                .courseId(enrollment.getCourseId())
                .status(enrollment.getStatus().name())
                .completionPercentage(enrollment.getCompletionPercentage())
                .build();
    }
}

