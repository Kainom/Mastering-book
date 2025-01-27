package kainom.com.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kainom.com.security.model.Publisher;


@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}