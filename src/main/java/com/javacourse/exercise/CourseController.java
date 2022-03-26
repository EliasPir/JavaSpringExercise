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
public class CourseController {
    
        
    private final CourseRepository repository;

    CourseController(CourseRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/courses")
    List<Course> all()
    {
        return repository.findAll();
    }

    @PostMapping("/courses")
    Course newCourse(@RequestBody Course newCourse)
    {
        System.out.println(newCourse.toString());
        return repository.save(newCourse);
    }

    @GetMapping("/courses/{id}")
    Course one(@PathVariable Long id)
    {
        return repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @PutMapping("/courses/{id}")
    Course replaceCourse(@RequestBody Course newCourse, @PathVariable Long id)
    {
        return repository.findById(id).map(course -> { course.setName(newCourse.getName());
        course.setCredit(newCourse.getCredit());
        course.setType(newCourse.getType());
        return repository.save(course);
    })
    .orElseGet(() ->
    {
        newCourse.setCourseId(id);
        return repository.save(newCourse);
    });
    }

    @DeleteMapping("/courses/{id}")
    String deleteCourse(@PathVariable Long id)
    {
        repository.deleteById(id);
        return "Deleted id: " + id;
    }
}
