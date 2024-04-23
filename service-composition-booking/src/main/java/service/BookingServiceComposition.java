package service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@Controller
public class BookingServiceComposition {
    String serviceRecommendAccommodationUrl = "http://localhost:8083/recommend";
    String serviceViewAccommodationOffersUrl = "http://localhost:8084/selectAccommodation";
    String serviceBookAccommodationUrl = "http://localhost:8085/book";

    @RequestMapping("/recommend-accommodation")
    public String recommendAccommodation(Model model) {
        model.addAttribute("serviceUrl", serviceRecommendAccommodationUrl);
        return "booking";
    }

    @RequestMapping("/view-accommodation")
    public String viewAccommodation(Model model) {
        model.addAttribute("serviceUrl", serviceViewAccommodationOffersUrl);
        return "booking";
    }

    @RequestMapping("/book-accommodation")
    public String bookAccommodation(Model model) {
        model.addAttribute("serviceUrl", serviceBookAccommodationUrl);
        return "booking";
    }
}

