package org.example.reviewservice.controller;

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
        model.addAttribute("tripId", tripId);
        model.addAttribute("bookingId", bookingId);
        return "reviewBefore";
    }

//    @GetMapping("/rating")
//    public  ResponseEntity<String> getRating(@RequestParam(name = "column") String column) {
//       // return  ResponseEntity.ok(String.format("%.2f", reviewService.average(column)));
//    }

    @PostMapping(value = "/reviewBefore")
    public String postReviewBeforePage(
            @RequestBody MultiValueMap<String, String> formData
    ) {
        reviewService.saveBefore(formData);
        return "redirect:http://localhost:8081/recommendation-list";
    }
}
