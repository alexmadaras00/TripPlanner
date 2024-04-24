package org.example.servicedestinationrecommender.service;

import org.example.servicedestinationrecommender.data.Trip;
import org.example.servicedestinationrecommender.data.TripForm;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TripRepository  extends ReactiveCrudRepository<Trip, Long> {
}
