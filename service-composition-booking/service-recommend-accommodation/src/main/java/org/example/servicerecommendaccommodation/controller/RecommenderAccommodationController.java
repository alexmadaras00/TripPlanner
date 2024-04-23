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
    @GetMapping("/recommend")
    public ModelAndView showInit(Model model, HttpServletRequest request) {
        String city = "Warsaw";
        String cityCode = recommenderService.getCityCode(city);
        ArrayList<Hotel> hotels = recommenderService.searchHotelsByCityCode(cityCode);
        model.addAttribute("recommendationList", hotels);
        request.getSession().setAttribute("city", city);
        return new ModelAndView("show-recommendations", model.asMap());
    }
    @SneakyThrows
    @PostMapping("/recommend")
    public ModelAndView showRecommendations(@RequestParam("city") String city, Model model, HttpServletRequest request) {
        String cityCode = recommenderService.getCityCode(city);
        System.out.println("City code: " + cityCode);
        model.addAttribute("cityCode", cityCode);
        model.addAttribute("recommendationList", recommenderService.searchHotelsByCityCode(cityCode));
        request.getSession().setAttribute("city", city);
        return new ModelAndView("show-recommendations", model.asMap());
    }
    @RequestMapping("/view-accommodation")
    public String performAction(@ModelAttribute("hotelId") String hotelId ) {
        return "redirect:http://localhost:8082/view-accommodation?hotelID="+hotelId;
    }
}
