package com.javacourse.exercise;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
    
    
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long courseId;
    private String name;
    private Integer credit;
    private String type;
    

    Course() {
        name = "";
        credit = 0;
        type = "";
    }
    
    Course(String name, Integer credit, String type)
    {
        this.name = name;
        this.credit = credit;
        this.type = type;
    }

    public void setCourseId(Long courseId)
    {
        this.courseId = courseId;
    }

    public Long getCourseId()
    {
        return this.courseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.courseId, this.name, this.credit, this.type);
    }

    @Override
    public String toString()
    {
        return "Course{" + "id=" + this.courseId + ", name='" + this.name + '\'' + ", credit='" + this.credit + '\'' + ", type='" + this.type + '\'' + '}';
    }

}
