package com.svh.course.repository;

import com.svh.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByIsPublishedTrue(Pageable pageable);
    Page<Course> findByCategory(String category, Pageable pageable);
    Page<Course> findByLevel(Course.CourseLevel level, Pageable pageable);
    List<Course> findByInstructorId(Long instructorId);
}

