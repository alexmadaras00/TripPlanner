package org.example.reviewservice.controller;

import org.example.reviewservice.data.ReviewAfter;
import org.example.reviewservice.data.ReviewBefore;
import org.example.reviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviewBefore")
    public String getReviewBeforePage(
            @RequestParam(name = "tripId") String tripId,
            @RequestParam(name = "bookingId") String bookingId,
            Model model
    ) {
        System.out.println("tripId" + tripId + "bookingId" + bookingId);
        model.addAttribute("tripId", tripId);
        model.addAttribute("bookingId", bookingId);
        return "reviewBefore";
    }

    @PostMapping(value = "/reviewBefore")
    public ResponseEntity<String> postReviewBeforePage(
            @RequestBody MultiValueMap<String, String> formData
    ) {
        int destinationRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("destinations")));
        int routeRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("routes")));
        int scheduleRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("schedule")));
        int bookingRating = Integer.parseInt(Objects.requireNonNull(formData.getFirst("booking")));
        int trip_id = Integer.parseInt(formData.getFirst("tripId"));
        int booking_id = Integer.parseInt(formData.getFirst("bookingId"));
        System.out.println(destinationRating + " " + routeRating + " " + scheduleRating + " " + bookingRating);
        ReviewBefore rb = new ReviewBefore(booking_id, trip_id, destinationRating, routeRating, scheduleRating, bookingRating);
        reviewService.saveBefore(rb);
        return ResponseEntity.ok("Review submitted successfully");
    }

    @GetMapping("/reviewAfter")
    public String getReviewAfterPage(
            @RequestParam(name = "tripId") String tripId,
            @RequestParam(name = "bookingId") String bookingId,
            Model model
    ) {
        model.addAttribute("tripId", tripId);
        model.addAttribute("bookingId", bookingId);
        return "reviewAfter";
    }

    @PostMapping("/reviewAfter")
    public ResponseEntity<String> postReviewAfterPage(
            @RequestBody MultiValueMap<String, String> formData
    ) {
        int arrived = Integer.parseInt(Objects.requireNonNull(formData.getFirst("arrived")));
        int accommodation = Integer.parseInt(Objects.requireNonNull(formData.getFirst("accommodation")));
        int offers = Integer.parseInt(Objects.requireNonNull(formData.getFirst("offers")));
        int scheduleFollowed = Integer.parseInt(Objects.requireNonNull(formData.getFirst("schedule-followed")));
        int overallExperience = Integer.parseInt(Objects.requireNonNull(formData.getFirst("overall-experience")));
        int trip_id = Integer.parseInt(formData.getFirst("tripId"));
        int booking_id = Integer.parseInt(formData.getFirst("bookingId"));
        System.out.println(arrived + " " + scheduleFollowed + " " + accommodation + " " + offers + " " + overallExperience);
        ReviewAfter ra = new ReviewAfter(booking_id, trip_id, arrived, scheduleFollowed, accommodation, accommodation, overallExperience);
        reviewService.saveAfter(ra);


        return ResponseEntity.ok("Review submitted successfully");
    }

}
