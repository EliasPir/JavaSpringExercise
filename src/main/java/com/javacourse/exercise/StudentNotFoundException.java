package com.javacourse.exercise;

public class StudentNotFoundException extends RuntimeException {

    StudentNotFoundException(Long id)
    {
        super("Could not find student " + id);
    }
    
}
