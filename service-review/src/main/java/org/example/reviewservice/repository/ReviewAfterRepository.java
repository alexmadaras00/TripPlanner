package org.example.reviewservice.repository;

import org.example.reviewservice.data.ReviewAfter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReviewAfterRepository   extends ReactiveCrudRepository<ReviewAfter, Long>  {
}
