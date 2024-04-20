package org.example.reviewservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class ReviewController {

    @GetMapping("/reviewBefore")
    public String getReviewBeforePage(@RequestParam(name = "tripId") String tripId, @RequestParam(name = "bookingId") String bookingId) {
        System.out.println("tripId" + tripId + "bookingId" + bookingId);
        return "reviewBefore";
    }

    @PostMapping(value = "/reviewBefore")
    public ResponseEntity<String> postReviewBeforePage(@RequestBody MultiValueMap<String, String> formData) {
        int destinationRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("destinations")));
        int routeRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("routes")));
        int scheduleRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("schedule")));
        int bookingRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("booking")));

        System.out.println(destinationRating + " " + routeRating + " " + scheduleRating + " " + bookingRating);

        return ResponseEntity.ok("Review submitted successfully");
    }

    @GetMapping("/reviewAfter")
    public String getReviewAfterPage() {
        return "reviewAfter";
    }

    @PostMapping("/reviewAfter")
    public ResponseEntity<String> postReviewAfterPage(@RequestBody MultiValueMap<String, String> formData) {
        int arrived = Integer.parseInt(Objects.requireNonNull(formData.getFirst("arrived")));
        int scheduleFollowed = Integer.parseInt(Objects.requireNonNull(formData.getFirst("schedule-followed")));
        int accommodation = Integer.parseInt(Objects.requireNonNull(formData.getFirst("accommodation")));
        int offers = Integer.parseInt(Objects.requireNonNull(formData.getFirst("offers")));
        int overallExperience = Integer.parseInt(Objects.requireNonNull(formData.getFirst("overall-experience")));

        System.out.println(arrived + " " + scheduleFollowed + " " + accommodation + " " + offers + " " + overallExperience);

        return ResponseEntity.ok("Review submitted successfully");
    }

}
