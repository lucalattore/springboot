package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.AuthorDTO;
import com.waveinformatica.demo.dto.AuthorResultSet;
import com.waveinformatica.demo.dto.BookDTO;
import com.waveinformatica.demo.exceptions.InvalidDataException;
import com.waveinformatica.demo.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LibraryApiController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody BookDTO book) {
        try {
            libraryService.addBook(book);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@RequestBody AuthorDTO author) {
        try {
            libraryService.addAuthor(author);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/authors")
    public AuthorResultSet findAuthors(@RequestParam(value = "lastName", required = false) String lastName) {
        List<AuthorDTO> foundAuthors = new ArrayList<>(); //TODO: implementare ricerca nel servizio library (lastName)

        AuthorResultSet result = new AuthorResultSet();
        result.getValues().addAll(foundAuthors);
        return result;
    }
}
