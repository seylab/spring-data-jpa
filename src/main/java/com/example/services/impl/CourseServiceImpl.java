package com.example.services.impl;

import com.example.dto.DtoCourse;
import com.example.dto.DtoStudent;
import com.example.entities.Course;
import com.example.entities.Student;
import com.example.repository.CourseRepository;
import com.example.services.ICourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<DtoCourse> getAllCourses() {
        List<DtoCourse> courses = new ArrayList<>();
        List<Course> courseList = courseRepository.findAll();

        for (Course course : courseList) {
            DtoCourse dtoCourse = new DtoCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            courses.add(dtoCourse);
        }

        return courses;
    }


    @Override
    public DtoCourse getCourseById(Long id) {
        DtoCourse dtoCourse = new DtoCourse();

        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        Course dbCourse = optional.get();
        List<Student> studentList = dbCourse.getStudents();
        BeanUtils.copyProperties(dbCourse, dtoCourse);

        if (studentList != null && !studentList.isEmpty()) {
            for (Student student : studentList) {
                DtoStudent dtoStudent = new DtoStudent();
                dtoStudent.setId(student.getId());
                dtoStudent.setFirstName(student.getFirstName());
                dtoStudent.setLastName(student.getLastName());
                dtoCourse.getStudents().add(dtoStudent);
            }
        }
        return dtoCourse;
    }
}
