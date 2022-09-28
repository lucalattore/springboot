package com.waveinformatica.demo.repositories;

import com.waveinformatica.demo.entities.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {
    List<Author> findByLastNameAndFirstName(String lastName, String firstName);

}
