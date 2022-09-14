package com.waveinformatica.demo.repositories;

import com.waveinformatica.demo.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {
    Iterable<Person> findByLastNameStartsWith(String lastName);
}
