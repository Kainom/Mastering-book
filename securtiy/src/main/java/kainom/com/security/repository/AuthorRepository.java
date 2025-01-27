package kainom.com.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kainom.com.security.model.Author;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}