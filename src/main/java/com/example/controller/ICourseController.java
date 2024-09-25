package com.example.controller;

import com.example.dto.DtoCourse;

import java.util.List;

public interface ICourseController {
    public DtoCourse getCourseById(Long id);
    public List<DtoCourse> getAllCourses();
}
