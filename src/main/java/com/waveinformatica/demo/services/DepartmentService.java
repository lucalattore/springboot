package com.waveinformatica.demo.services;

import com.waveinformatica.demo.entities.Department;
import com.waveinformatica.demo.entities.Person;
import com.waveinformatica.demo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public Department getDepartment(long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Person> listDepartmentPeople(long id) {
        Department d = departmentRepository.findById(id).orElse(null);
        if (d == null) {
            return null;
        }

        return d.getPeople();
    }
}
