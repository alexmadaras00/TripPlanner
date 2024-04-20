package org.example.servicerecommendaccommodation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.example.servicerecommendaccommodation.data.TripForm;
import org.example.servicerecommendaccommodation.service.RecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RecommenderAccommodationController {
    @Autowired
    private RecommenderService recommenderService;
    @SneakyThrows
    @PostMapping("/recommend")
    public ModelAndView showRecommendations(@ModelAttribute TripForm tripForm, Model model, HttpServletRequest request) {
        String city = tripForm.getCity();
        String cityCode = recommenderService.getCityCode(tripForm.getCity());
        System.out.println("City code: " + cityCode);
        model.addAttribute("cityCode", cityCode);
        model.addAttribute("recommendationList", recommenderService.searchHotelsByCityCode(cityCode));
        request.getSession().setAttribute("city", city);
        return new ModelAndView("show_recommendations", model.asMap());
    }
}
