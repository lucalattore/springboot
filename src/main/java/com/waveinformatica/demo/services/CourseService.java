package com.waveinformatica.demo.services;

import com.waveinformatica.demo.entities.Course;
import com.waveinformatica.demo.entities.Department;
import com.waveinformatica.demo.entities.Person;
import com.waveinformatica.demo.exceptions.InvalidDataException;
import com.waveinformatica.demo.exceptions.ResourceNotFoundException;
import com.waveinformatica.demo.repositories.CourseRepository;
import com.waveinformatica.demo.repositories.DepartmentRepository;
import com.waveinformatica.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void addCourse(Course input) {
        Department d = departmentRepository
            .findById(input.getDepartment().getId())
            .orElseThrow(() -> new RuntimeException("Department not available"));

        Course c = new Course();
        c.setTitle(input.getTitle());
        c.setDepartment(d);
        courseRepository.save(c);
    }

    @Transactional
    public void addStudentToCourse(long studentId, long courseId) {
        Course c = courseRepository
            .findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not available"));

        Person p = personRepository
            .findById(studentId)
            .orElseThrow(() -> new InvalidDataException("Person not available"));

        c.getStudents().add(p);
        courseRepository.save(c);
    }
}
