package com.example.controller.impl;

import com.example.controller.IStudentController;
import com.example.entities.Student;
import com.example.services.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/student")

public class StudentControllerImpl implements IStudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping(path = "/save")
    @Override
    public Student saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping(path= "/list")
    @Override
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "list/{id}")
    @Override
    public Student getStudentById(@PathVariable(name = "id") Integer id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteStudent(@PathVariable(name = "id") Integer id) {
        studentService.deleteStudent(id);
    }


}
