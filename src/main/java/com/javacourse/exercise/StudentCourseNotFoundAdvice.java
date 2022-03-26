package com.javacourse.exercise;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StudentCourseNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(StudentCourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String studentCourseNotFoundHandler(StudentCourseNotFoundException ex)
    {
        return ex.getMessage();
    }
    
}
