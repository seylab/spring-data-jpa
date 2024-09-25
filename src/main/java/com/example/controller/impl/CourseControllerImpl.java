package com.example.controller.impl;

import com.example.controller.ICourseController;
import com.example.dto.DtoCourse;
import com.example.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/course")
public class CourseControllerImpl implements ICourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping(path= "/list")
    @Override
    public List<DtoCourse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path= "/list/{id}")
    @Override
    public DtoCourse getCourseById(@PathVariable(name = "id") Long id) {
        return courseService.getCourseById(id);
    }
}
