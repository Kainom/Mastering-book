package kainom.com.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import kainom.com.security.model.Review;


public interface ReviewRepository extends MongoRepository<Review, String> {

}