package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(authorMapper::map)
                .toList();
    }

    public AuthorDTO getAuthorById(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        return authorMapper.map(author);
    }

    public AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.map(authorCreateDTO);
        author = authorRepository.save(author);

        return authorMapper.map(author);
    }

    public AuthorDTO updateAuthor(AuthorUpdateDTO authorUpdateDTO, Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        authorMapper.update(authorUpdateDTO, author);
        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    // END
}
