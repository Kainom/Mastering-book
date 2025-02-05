package kainom.migration.migrations_liquibase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kainom.com.testing.model.Author;
import kainom.com.testing.model.Publisher;
import kainom.com.testing.repository.AuthorRepository;
import kainom.com.testing.service.AuthorService;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

    private Author givenAuthor;
    private Author savedAuthor;


    @BeforeEach
    void setup(){
        Publisher defaultPublisher = Publisher.builder().name("Packt Publishing").build();
        Author savedAuthor = Author.builder()
                .id(1L)
                .name("Author Name")
                .publisher(defaultPublisher)
                .biography("Biography of Author")
                .build();
    }

    @Test
    void givenExistingAuthorId_whenGetAuthor_thenReturnAuthor() {
       
        when(authorRepository.findById(1L)).thenReturn(Optional.of(savedAuthor));

        Optional<Author> author = authorService.getAuthor(1L);

        assertTrue(author.isPresent(), "Author should be found");
        assertEquals(1L, author.get().getId(), "Author ID should match");

        
    }

}

