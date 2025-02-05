package kainom.com.testing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import kainom.com.testing.model.Review;


public interface ReviewRepository extends MongoRepository<Review, String> {

}