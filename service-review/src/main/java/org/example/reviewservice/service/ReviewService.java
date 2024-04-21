package org.example.reviewservice.service;

import org.example.reviewservice.data.ReviewAfter;
import org.example.reviewservice.data.ReviewBefore;
import org.example.reviewservice.repository.ReviewAfterRepository;
import org.example.reviewservice.repository.ReviewBeforeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReviewService {
    private final ReviewAfterRepository reviewAfterRepository;
    private final ReviewBeforeRepository reviewBeforeRepository;

    @Autowired
    public ReviewService(ReviewAfterRepository reviewAfterRepository, ReviewBeforeRepository reviewBeforeRepository) {
        this.reviewAfterRepository = reviewAfterRepository;
        this.reviewBeforeRepository = reviewBeforeRepository;
    }

    public void saveBefore(ReviewBefore rb) {
        Mono<ReviewBefore> rbs = reviewBeforeRepository.save(rb);
        rbs.subscribe(rbsSaved -> {}, error -> {});
    }

    public void saveAfter(ReviewAfter ra) {
        Mono<ReviewAfter> ras = reviewAfterRepository.save(ra);
        ras.subscribe(rasSaved -> {}, error -> {});

    }
}
