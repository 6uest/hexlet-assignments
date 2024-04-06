package exercise.controller;

import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.service.AuthorService;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> index() {
        var books = bookService.getAllBooks();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(books.size()))
                .body(books);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> show(@PathVariable Long id) {
        var book = bookService.findBookById(id);

        return ResponseEntity.ok(book);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> create(@RequestBody @Valid BookCreateDTO book) {
        try {
            authorService.getAuthorById(book.getAuthorId());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        var bookCreated = bookService.createBook(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookCreated);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody @Valid BookUpdateDTO book) {
        try {
            authorService.getAuthorById(book.getAuthorId().get());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        var updateBook = bookService.updateBook(book, id);

        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    // END
}
