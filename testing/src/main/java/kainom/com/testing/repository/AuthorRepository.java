package kainom.com.testing.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kainom.com.testing.model.Author;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}