package kainom.migration.migrations_liquibase.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import kainom.migration.migrations_liquibase.model.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {

}