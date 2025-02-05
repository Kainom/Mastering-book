package kainom.com.testing.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kainom.com.testing.model.Publisher;


@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}