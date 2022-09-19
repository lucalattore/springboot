package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.EditablePersonDTO;
import com.waveinformatica.demo.entities.Person;
import com.waveinformatica.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Person> findPeople(String lastName) {
        List<Person> foundPeople = new ArrayList<>();
        if (lastName == null) {
            personRepository
                .findAll()
                .forEach(foundPeople::add);
                //equivalente a .forEach(p -> foundPeople.add(p));
        } else {
            personRepository
                .findByLastNameStartsWith(lastName)
                .forEach(foundPeople::add);
        }
        return foundPeople;
    }

    public boolean deletePerson(long id) {
        //personRepository.deleteById(id);
        return personRepository
            .findById(id)
            .map(p -> {
                personRepository.delete(p);
                return true;
            })
            .orElse(false);
    }

    public boolean update(Person p) {
        Optional<Person> foundPerson = personRepository.findById(p.getId());
        if (!foundPerson.isPresent()) {
            return false;
        }

        // Questo è sbagliato perché vuole fare un nuovo insert
        // personRepository.save(p);

        Person q = foundPerson.get();
        // nell'oggetto recuperato con jpa dal db aggiorno i valori
        q.setFirstName(p.getFirstName());
        q.setLastName(p.getLastName());

        // update
        personRepository.save(q);
        return true;
    }

    public boolean partialUpdate(long id, EditablePersonDTO p) {
        Optional<Person> foundPerson = personRepository.findById(id);
        if (!foundPerson.isPresent()) {
            return false;
        }

        Person q = foundPerson.get();

        // aggiornare nell'oggetto recuperato da db i soli attributi da modificare
        if (p.getFirstName() != null) {
            q.setFirstName(p.getFirstName().orElse(null));
        }

        if (p.getLastName() != null) {
            q.setLastName(p.getLastName().orElse(null));
        }

        // update
        personRepository.save(q);
        return false;
    }
}
