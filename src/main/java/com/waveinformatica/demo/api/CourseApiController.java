package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.IdDTO;
import com.waveinformatica.demo.entities.Course;
import com.waveinformatica.demo.exceptions.InvalidDataException;
import com.waveinformatica.demo.exceptions.ResourceNotFoundException;
import com.waveinformatica.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CourseApiController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourse(@RequestBody final Course c) {
        courseService.addCourse(c);
    }

    @PostMapping("/courses/{id}/students")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStudent(@PathVariable("id") long id, @RequestBody final IdDTO student) {
        try {
            courseService.addStudentToCourse(student.getId(), id);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }
}
