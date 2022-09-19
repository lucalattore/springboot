package com.waveinformatica.demo.api;

import com.waveinformatica.demo.entities.Department;
import com.waveinformatica.demo.entities.Person;
import com.waveinformatica.demo.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class DepartmentApiController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments/{id}")
    public Department getDepartment(@PathVariable("id") long id) {
        Department d = departmentService.getDepartment(id);
        if (d == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        return d;
    }

    @GetMapping("/departments/{id}/people")
    public List<Person> listPeopleOfDep(@PathVariable("id") long id) {
        List<Person> people = departmentService.listDepartmentPeople(id);
        if (people == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }

        return people;
    }
}
