package com.javacourse.exercise;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String groupCode;

    Student() {}

    Student(String firstName, String lastName, String address, String groupCode)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.groupCode = groupCode;
    }

    Student(String firstName, String lastName, String address, String groupCode, Long id)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.groupCode = groupCode;
        this.id = id;
    }

    public Long getId()
    {
        return this.id;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getAddress()
    {
        return this.address;
    }

    public String getGroupCode()
    {
        return this.groupCode;
    }

    public void setId(Long id)
    {
        this.id= id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setGroupCode(String groupCode)
    {
        this.groupCode = groupCode;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.id, this.firstName, this.lastName, this.address, this.groupCode);
    }

    @Override
    public String toString()
    {
        return this.firstName + " " + this.lastName;
    }
}
