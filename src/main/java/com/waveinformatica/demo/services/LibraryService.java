package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.AuthorDTO;
import com.waveinformatica.demo.dto.BookDTO;
import com.waveinformatica.demo.entities.Author;
import com.waveinformatica.demo.entities.Book;
import com.waveinformatica.demo.exceptions.InvalidDataException;
import com.waveinformatica.demo.repositories.AuthorRepository;
import com.waveinformatica.demo.repositories.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void addBook(BookDTO book) {
        if (book.getOptionalISBN() == null || book.getOptionalISBN().isEmpty()) {
            throw new InvalidDataException("ISBN cannot be empty");
        }

        if (book.getAuthor() == null) {
            throw new InvalidDataException("Author must be present");
        }

        Author author;
        if (book.getAuthor().getId() == null) {
            if (book.getAuthor().getFirstName() == null || book.getAuthor().getFirstName().isEmpty()) {
                throw new InvalidDataException("Both author's firstName and lastName cannot be empty or null");
            }

            if (book.getAuthor().getLastName() == null || book.getAuthor().getLastName().isEmpty()) {
                throw new InvalidDataException("Both author's firstName and lastName cannot be empty or null");
            }

            String authorLastName = book.getAuthor().getLastName().get();
            String authorFirstName = book.getAuthor().getFirstName().get();

            author = authorRepository
                .findByLastNameAndFirstName(authorLastName, authorFirstName)
                .stream()
                .findFirst()
                .orElse(null);
            /** EQUIVALENTE A
             List<Author> foundAuthors = authorRepository.findByLastNameAndFirstName(book.getAuthor().getLastName().get(), book.getAuthor().getFirstName().get());
             Author author = foundAuthors.isEmpty() ? null : foundAuthors.get(0);
             */

            if (author == null) {
                author = new Author();
                author.setFirstName(authorFirstName);
                author.setLastName(authorLastName);
                author.setNationality(book.getAuthor().getNationality().orElse(null));

                // inserisco su db il nuovo autore
                authorRepository.save(author);
            } else if (book.getAuthor().getNationality() != null) {
                if (book.getAuthor().getNationality().isPresent() && !StringUtils.equals(book.getAuthor().getNationality().get(), author.getNationality())) {
                    // aggiorno nazionalità
                    author.setNationality(book.getAuthor().getNationality().orElse(null));
                    authorRepository.save(author);
                }
                //TODO: controllare il caso in cui voglio rimuovere la nazionalità
            }
        } else {
            author = authorRepository
                .findById(book.getAuthor().getId())
                .orElseThrow(() -> new InvalidDataException("Unable to find author with code " + book.getAuthor().getId()));
        }

        Book b = new Book();
        b.setIsbn(book.getOptionalISBN().get());
        b.setAuthor(author);

        b.setTitle(book.getOptionalTitle() == null ? null : book.getOptionalTitle().orElse(null));
        /** EQUIVALENTE A
        String title;
        if (book.getOptionalTitle() == null) {
            title = null;
        } else {
            if (book.getOptionalTitle().isPresent()) {
                title = book.getOptionalTitle().get();
            } else {
                title = null;
            }
        }
        b.setTitle(title);
        */

        bookRepository.save(b);
    }

    public void addAuthor(AuthorDTO author) {
        if (author.getFirstName() == null || author.getFirstName().isEmpty()) {
            throw new InvalidDataException("Both author's firstName and lastName cannot be empty or null");
        }

        if (author.getLastName() == null || author.getLastName().isEmpty()) {
            throw new InvalidDataException("Both author's firstName and lastName cannot be empty or null");
        }

        Author a = new Author();
        a.setFirstName(author.getFirstName().get());
        a.setLastName(author.getLastName().get());
        a.setNationality(author.getNationality() == null ? null : author.getNationality().orElse(null));
        authorRepository.save(a);
    }
}
