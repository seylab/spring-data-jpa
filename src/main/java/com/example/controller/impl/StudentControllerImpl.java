package com.example.controller.impl;

import com.example.controller.IStudentController;
import com.example.dto.DtoStudent;
import com.example.dto.DtoStudentIU;
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
    public DtoStudent saveStudent(@RequestBody DtoStudentIU dtoStudentIU) {
        return studentService.saveStudent(dtoStudentIU);
    }

    @GetMapping(path= "/list")
    @Override
    public List<DtoStudent> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "list/{id}")
    @Override
    public DtoStudent getStudentById(@PathVariable(name = "id") Integer id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteStudent(@PathVariable(name = "id") Integer id) {
        studentService.deleteStudent(id);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public DtoStudent updateStudent(@PathVariable(name = "id") Integer id, @RequestBody DtoStudentIU dtoStudentIU) {
        return studentService.updateStudent(id, dtoStudentIU);
    }


}
