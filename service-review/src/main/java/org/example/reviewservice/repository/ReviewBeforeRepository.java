package org.example.reviewservice.repository;

import org.example.reviewservice.data.ReviewBefore;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReviewBeforeRepository  extends ReactiveCrudRepository<ReviewBefore, Long> {

}
