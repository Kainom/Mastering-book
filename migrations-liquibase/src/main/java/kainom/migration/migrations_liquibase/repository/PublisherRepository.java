package kainom.migration.migrations_liquibase.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kainom.migration.migrations_liquibase.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}