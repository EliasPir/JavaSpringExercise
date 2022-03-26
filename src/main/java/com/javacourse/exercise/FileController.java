package com.javacourse.exercise;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController  {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository scRepository;

    FileController(CourseRepository courseRepository, StudentRepository studentRepository, StudentCourseRepository scRepository)
    {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.scRepository = scRepository;
    }

    public void saveToFile()
    {
        List<Student> studentList = studentRepository.findAll();
        List<Course> courseList = courseRepository.findAll();
        List<StudentCourse> scList = scRepository.findAll();

        SaveFile saveFile = new SaveFile();
        saveFile.studentList = studentList;
        saveFile.courseList = courseList;
        saveFile.scList = scList;

        String saveFileJson = new Gson().toJson(saveFile);
        System.out.println(saveFileJson);
        try {
            File myObj = new File("save.txt");
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
        try {
            FileWriter myWriter = new FileWriter("save.txt");
            myWriter.write(saveFileJson);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    
}
