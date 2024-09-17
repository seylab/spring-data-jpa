package com.example.services.impl;

import com.example.entities.Student;
import com.example.repository.StudentRepository;
import com.example.services.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Integer id) {
        Optional<Student> optional = studentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public void deleteStudent(Integer id) {
        Student dbStudent = getStudentById(id);
        if (dbStudent != null) {
            studentRepository.delete(dbStudent);
        }
    }
}
