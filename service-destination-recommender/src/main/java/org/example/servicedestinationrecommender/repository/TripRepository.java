package org.example.servicedestinationrecommender.repository;


import org.example.servicedestinationrecommender.data.TripForm;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TripRepository extends ReactiveCrudRepository<TripForm, String> {

}
