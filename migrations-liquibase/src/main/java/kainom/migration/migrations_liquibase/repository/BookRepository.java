package kainom.migration.migrations_liquibase.repository;

import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.lang.NonNull;

import kainom.migration.migrations_liquibase.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @NonNull
    @Override
    @Cacheable( "books")
    Optional<Book> findById(Long id);
}