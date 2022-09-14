package com.waveinformatica.demo.api;

import com.waveinformatica.demo.entities.Person;
import com.waveinformatica.demo.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PeopleApiController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody Person p) {
        if (p.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID must be empty");
        }

        peopleService.addPerson(p);
    }
}
