package com.javacourse.exercise;

public class StudentCourseNotFoundException  extends RuntimeException {

    StudentCourseNotFoundException(Long id)
    {
        super("Could not find studentCourse " + id);
    }
    
}
