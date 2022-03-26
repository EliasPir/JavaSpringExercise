package com.javacourse.exercise;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentCourseController {
    private final StudentCourseRepository repository;

    StudentCourseController(StudentCourseRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/studentcourses")
    List<StudentCourse> all()
    {
        return repository.findAll();
    }

    @PostMapping("/studentcourses")
    StudentCourse newStudentCourse(@RequestBody StudentCourse newStudentCourse)
    {
        System.out.println(newStudentCourse.toString());
        return repository.save(newStudentCourse);
    }

    @GetMapping("/studentcourses/{id}")
    StudentCourse one(@PathVariable Long id)
    {
        return repository.findById(id).orElseThrow(() -> new StudentCourseNotFoundException(id));
    }

    @PutMapping("/studentcourses/{id}")
    StudentCourse replaceStudentCourse(@RequestBody StudentCourse newStudentCourse, @PathVariable Long id)
    {
        return repository.findById(id).map(studentCourse -> { studentCourse.setStudentId(newStudentCourse.getStudentId());
        studentCourse.setCourseId(newStudentCourse.getCourseId());
        return repository.save(studentCourse);
    })
    .orElseGet(() ->
    {
        newStudentCourse.setId(id);
        return repository.save(newStudentCourse);
    });
    }

    @DeleteMapping("/studentcourses/{id}")
    String deleteStudentCourse(@PathVariable Long id)
    {
        repository.deleteById(id);
        return "Deleted id: " + id;
    }
}
