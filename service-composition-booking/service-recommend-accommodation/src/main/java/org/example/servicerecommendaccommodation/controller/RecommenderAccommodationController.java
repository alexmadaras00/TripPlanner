package org.example.servicerecommendaccommodation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.example.servicerecommendaccommodation.data.Hotel;
import org.example.servicerecommendaccommodation.data.TripForm;
import org.example.servicerecommendaccommodation.service.RecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecommenderAccommodationController {
    @Autowired
    private RecommenderService recommenderService;
    @GetMapping("/recommend-accommodation")
    public ModelAndView showInit(Model model, HttpServletRequest request) {
        String city = "London";
        String cityCode = recommenderService.getCityCode(city);
        ArrayList<Hotel> hotels = recommenderService.searchHotelsByCityCode(cityCode);
        model.addAttribute("recommendationList", hotels);
        request.getSession().setAttribute("city", city);
        System.out.println("List: "+recommenderService.searchHotelsByCityCode(cityCode));
        return new ModelAndView("show-recommendations", model.asMap());
    }

    @RequestMapping("/view-accommodation/{hotelID}")
    public String performAction(@PathVariable(required = false) String hotelID ) {
        System.out.println("id: "+hotelID);
        return "redirect:http://localhost:8082/view-accommodation/"+hotelID;
    }
}
