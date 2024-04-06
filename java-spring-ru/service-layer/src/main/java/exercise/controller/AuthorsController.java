package exercise.controller;

import exercise.dto.AuthorDTO;
import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AuthorDTO>> index() {
        var authors = authorService.getAllAuthors();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(authors.size()))
                .body(authors);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorDTO> show(@PathVariable Long id) {
        var author = authorService.getAuthorById(id);

        return ResponseEntity.ok(author);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorDTO> create(@RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        var author = authorService.createAuthor(authorCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid AuthorUpdateDTO authorUpdateDTO) {
        var author = authorService.updateAuthor(authorUpdateDTO, id);

        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }
    // END
}
