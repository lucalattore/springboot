package com.waveinformatica.demo.services;

import com.waveinformatica.demo.entities.Person;
import com.waveinformatica.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    @Autowired
    private PersonRepository personRepository;

    public void addPerson(Person p) {
        personRepository.save(p);
    }
}
