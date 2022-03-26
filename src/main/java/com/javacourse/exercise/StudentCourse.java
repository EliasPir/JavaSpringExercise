package com.javacourse.exercise;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class StudentCourse {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Long studentId;
    private Long courseId;
    public Long tempStudentId;
    public Long tempCourseId;

    StudentCourse() {}

    StudentCourse(Long studentId, Long courseId)
    {
        this.setStudentId(studentId);
        this.setCourseId(courseId);
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.studentId, this.courseId);
    }

    @Override
    public String toString()
    {
        return "{" + "studentId=" + this.studentId + ", courseId='" + this.courseId + '}';
    }
}
