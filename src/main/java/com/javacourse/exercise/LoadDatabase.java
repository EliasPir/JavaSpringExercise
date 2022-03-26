package com.javacourse.exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(StudentRepository repository, CourseRepository cRepository, StudentCourseRepository scRepository)
    {
    	boolean fileFound = false;
    	
    	try {
    		Path fileName = Path.of("save.txt");
			String saveString = Files.readString(fileName);
			if (saveString != null && !saveString.isEmpty())
			{
		        SaveFile saveFile = new Gson().fromJson(saveString, SaveFile.class);
		        
		        if(saveFile != null)
		        {
		        	fileFound = true;
		        	if(saveFile.studentList != null)
		        	{
		        		for(int i = 0; i < saveFile.studentList.size(); i++)
		        		{
		        			Student student = saveFile.studentList.get(i);
		        			
		        			Student newStudent = repository.save(new Student(student.getFirstName(),student.getLastName(), student.getAddress(), student.getGroupCode()));
		        		
		        			for(int x = 0; x < saveFile.scList.size(); x++)
		        			{
		        				StudentCourse studentCourse = saveFile.scList.get(x);
		        				if(studentCourse.getStudentId() == student.getId())
		        				{
		        					studentCourse.tempStudentId = newStudent.getId();
		        				}
		        			}
		        		}
		        	}
		        	if(saveFile.courseList != null)
		        	{
		        		for(int i = 0; i < saveFile.courseList.size(); i++)
		        		{
		        			Course course = saveFile.courseList.get(i);
		        			
		        			Course newCourse = cRepository.save(new Course(course.getName(), course.getCredit(), course.getType()));
		        		
		        			for(int x = 0; x < saveFile.scList.size(); x++)
		        			{
		        				StudentCourse studentCourse = saveFile.scList.get(x);
		        				if(studentCourse.getCourseId() == course.getCourseId())
		        				{
		        					studentCourse.tempCourseId = newCourse.getCourseId();
		        				}
		        			}
		        		}
		        	}
		        	
		        	if(saveFile.scList != null)
		        	{
		        		for(int i = 0; i < saveFile.scList.size(); i++)
		        		{
		        			StudentCourse studentCourse = saveFile.scList.get(i);
		        			
		        			StudentCourse newStudentCourse = scRepository.save(new StudentCourse(studentCourse.tempStudentId, studentCourse.tempCourseId));
		        		
		        		}
		        	}

		        }
		        
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	if (!fileFound)
    	{
            log.info("Preloading " + repository.save(new Student("Erkki","Esimerkki", "Esimerkkikatu 5 B 2", "TVT99SPO", 100L)));
            log.info("Preloading " + repository.save(new Student("Mikko","Mallikas", "Mallitie 189", "TVT90KMO")));
            log.info("Preloading " + cRepository.save(new Course("Web Project", 15, "OnlineCourse")));
            log.info("Preloading " + cRepository.save(new Course("Java programming", 5, "ClassRoomCourse")));
            log.info("Preloading " + scRepository.save(new StudentCourse(2L,1L)));
            log.info("Preloading " + scRepository.save(new StudentCourse(1L,2L)));
    	}
        return args -> {

        };
    }
}
