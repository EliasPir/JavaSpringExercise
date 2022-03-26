package com.javacourse.exercise;

public class CourseNotFoundException extends RuntimeException {

    CourseNotFoundException(Long id)
    {
        super("Could not find course " + id);
    }
    
}