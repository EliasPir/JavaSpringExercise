package com.javacourse.exercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    
    @Query("SELECT t FROM StudentCourse t WHERE t.courseId = ?1")
    List<StudentCourse> findByCourseId(Long courseId);
}
