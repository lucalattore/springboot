package com.waveinformatica.demo.repositories;

import com.waveinformatica.demo.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {
    Iterable<Person> findByLastNameStartsWith(String lastName);

    @Query(value = "select * from people where last_name = :ln", nativeQuery = true)
    List<Person> findWithLastName(@Param("ln") String lastName);
}
