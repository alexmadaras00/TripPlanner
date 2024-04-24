package org.example.reviewservice.service;

import org.example.reviewservice.data.ReviewBefore;
import org.example.reviewservice.repository.ReviewBeforeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReviewService {
    private final ReviewBeforeRepository reviewBeforeRepository;
    private final WebSocketService webSocketService;

    @Autowired
    public ReviewService(ReviewBeforeRepository reviewBeforeRepository, WebSocketService webSocketService) {
        this.reviewBeforeRepository = reviewBeforeRepository;
        this.webSocketService = webSocketService;
    }

    public void saveBefore(MultiValueMap<String, String> formData) {
        int trip_id = Integer.parseInt(Objects.requireNonNull(formData.getFirst("tripId")));
        int booking_id = Integer.parseInt(Objects.requireNonNull(formData.getFirst("bookingId")));
        int destinationRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("destinations")));
        int routeRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("routes")));
        int scheduleRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("schedule")));
        int bookingRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("booking")));
        ReviewBefore rb = new ReviewBefore(booking_id, trip_id, destinationRating, routeRating, scheduleRating, bookingRating);
        Mono<ReviewBefore> rbs = reviewBeforeRepository.save(rb);
        rbs.subscribe(rbsSaved -> {}, error -> {});
        sendAverage();
    }

    public void sendAverage() {
        webSocketService.sendMessageToTopicDestinations(String.format("%.2f", average("destinations")));
        webSocketService.sendMessageToTopicRoutes(String.format("%.2f", average("routes")));
        webSocketService.sendMessageToTopicSchedule(String.format("%.2f", average("schedule")));
        webSocketService.sendMessageToTopicBooking(String.format("%.2f", average("booking")));
    }

    public Mono<Integer> calculateSum(String columnName) {
        return reviewBeforeRepository.findAll()
                .map(reviewBefore -> {
                    return switch (columnName) {
                        case "destinations" -> reviewBefore.getDestination_rating();
                        case "routes" -> reviewBefore.getRoute_rating();
                        case "schedule" -> reviewBefore.getSchedule_rating();
                        case "booking" -> reviewBefore.getBooking_rating();
                        default -> 0;
                    };
                })
                .reduce(Integer::sum);
    }

    public double average(String columnName) {
        double count = (double) reviewBeforeRepository.count().block();
        double sum = (double) calculateSum(columnName).block();
        return (sum/count);
    }
}
