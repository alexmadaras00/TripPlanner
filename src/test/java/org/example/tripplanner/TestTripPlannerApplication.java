package org.example.tripplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTripPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.from(TripPlannerApplication::main).with(TestTripPlannerApplication.class).run(args);
    }

}
