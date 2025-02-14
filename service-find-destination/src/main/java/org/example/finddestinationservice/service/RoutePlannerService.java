package org.example.finddestinationservice.service;

import com.google.maps.errors.ApiException;
import org.example.finddestinationservice.data.Route;
import org.example.finddestinationservice.data.TripForm;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

public interface RoutePlannerService {
    List<Route> getRoutes(String source, String destination) throws IOException, InterruptedException, ApiException;
    Mono<TripForm> saveTrip(TripForm trip);
    Mono<TripForm> getTrip(String id);
    Mono<Void> deleteTrip(String id);
}
