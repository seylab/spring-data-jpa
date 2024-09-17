package com.example.controller;

import com.example.entities.Student;

import java.util.List;

public interface IStudentController {

    public Student saveStudent(Student student);
    public List<Student> getAllStudents();
    public Student getStudentById(Integer id);
    public void deleteStudent(Integer id);
}
