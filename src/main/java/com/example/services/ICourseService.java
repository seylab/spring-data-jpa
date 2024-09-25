package com.example.services;

import com.example.dto.DtoCourse;

import java.util.List;

public interface ICourseService {
    List<DtoCourse> getAllCourses();
    DtoCourse getCourseById(Long id);
}
