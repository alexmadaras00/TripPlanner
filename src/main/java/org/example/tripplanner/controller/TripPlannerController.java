package org.example.tripplanner.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.example.tripplanner.data.booking.TripForm;
import org.example.tripplanner.services.booking.recommender.RecommenderService;
import org.example.tripplanner.services.booking.select_accommodation.SelectAccommodation;
import org.example.tripplanner.utils.Print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TripPlannerController {
    @Autowired
    private RecommenderService recommenderService;
    @Autowired
    private SelectAccommodation selectAccommodation;
    Print print = new Print();

    private void print(String message) {
        print.print(this.getClass(), message);
    }

    @GetMapping("/")
    public String apiRoot(Model model) {
        model.addAttribute("tripForm", new TripForm());
        return "index";
    }

    @SneakyThrows
    @PostMapping("/recommend")
    public ModelAndView showRecommendations(@RequestParam String city, Model model, HttpServletRequest request) {
        String cityCode = recommenderService.getCityCode(city);
        model.addAttribute("cityCode", cityCode);
        model.addAttribute("recommendationList", recommenderService.searchHotelsByCityCode(cityCode));
        request.getSession().setAttribute("city", city);
        return new ModelAndView("show-recommendations", model.asMap());
    }

    @PostMapping("/showProperty")
    public String showOffer(@RequestParam String hotelId, Model model) {
        model.addAttribute("hotelOfferResponse", selectAccommodation.receiveOffer(hotelId));
        return "show-property";
    }

    @PostMapping("/error")
    public String showError(Exception e, Model model) {
        model.addAttribute("exception", e);
        return "error";
    }
}

