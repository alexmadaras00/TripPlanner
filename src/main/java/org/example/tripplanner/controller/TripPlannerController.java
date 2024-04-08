package org.example.tripplanner.controller;

import com.google.maps.errors.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.example.tripplanner.data.booking.TripForm;
import org.example.tripplanner.services.booking.recommender.RecommenderService;
import org.example.tripplanner.services.booking.select_accommodation.SelectAccommodation;
import org.example.tripplanner.services.city2coordinates.CityToCoordinatesService;
import org.example.tripplanner.utils.Print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TripPlannerController {
    @Autowired
    private RecommenderService recommenderService;
    @Autowired
    private SelectAccommodation selectAccommodation;
    @Autowired
    private CityToCoordinatesService cityToCoordinatesService;
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
        print("City code: "+cityCode);
        List<String> cityCoordinates = cityToCoordinatesService.getCoordinatesFromCity(city);
        print("Latitude: "+ cityCoordinates.get(0));
        print("Longitute: "+cityCoordinates.get(1));
        model.addAttribute("cityCode", cityCode);
        model.addAttribute("recommendationList", recommenderService.searchHotelsByCityCode(cityCode));
        model.addAttribute("coordinates", cityCoordinates);
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

