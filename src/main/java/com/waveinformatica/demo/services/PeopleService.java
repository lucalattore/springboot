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

    public Person getPerson(long id) {
//        Optional<Person> p = personRepository.findById(id);
//        return p.isPresent() ? p.get() : null;
        return personRepository.findById(id).orElse(null);
    }
}
