package org.example.tripplanner.controller;

import org.example.tripplanner.services.booking.book_and_pay.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TripPlannerController {
    @Autowired
    private PaymentService paymentService;


    @GetMapping("/")
    public String apiRoot() {
        return "index";
    }

    @PostMapping("/payments")
    public String showPayment(Model model) {
//        model.addAttribute("payment", paymentService.orderAndPay(order));
        return "show-payment-details";
    }
}
