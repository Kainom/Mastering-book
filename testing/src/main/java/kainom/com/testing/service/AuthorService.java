package kainom.com.testing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import kainom.com.testing.model.Author;
import kainom.com.testing.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthor(long id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> updateAuthor(Long id, Author author) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    existingAuthor.setName(author.getName());
                    existingAuthor.setBiography(author.getBiography());
                    existingAuthor.setPublisher(author.getPublisher());
                    return authorRepository.save(existingAuthor);
                });
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }

}