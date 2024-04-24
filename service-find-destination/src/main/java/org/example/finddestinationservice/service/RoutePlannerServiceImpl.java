package org.example.finddestinationservice.service;


import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import org.example.finddestinationservice.data.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoutePlannerServiceImpl implements RoutePlannerService {
    @Value("${google.directionsAPI.key}")
    private String apiKey;
    @Override
    public List<Route> getRoutes(String start, String destination) throws IOException, InterruptedException, ApiException {
        String url = "https://maps.googleapis.com/maps/api/directions/" + "origin= " + start + "&destination=" + destination;

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
;
        DirectionsResult result = DirectionsApi.newRequest(context)
                .origin(start)
                .destination(destination)
                .await();
        List<Route> routes = new ArrayList<>();

        System.out.println(start);
        System.out.println(destination);
        System.out.println(Arrays.toString(result.routes));
        DirectionsRoute[] routesJson = result.routes;

        return routes;
    }

}
