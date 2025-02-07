package org.example.servicedestinationrecommender.service;

import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;

import java.io.IOException;
import java.util.List;

public interface DestinationRecommenderService {
    List<Destination> getRecommendations(TripForm tripForm) throws IOException;
    void saveTrip(TripForm trip);
}
