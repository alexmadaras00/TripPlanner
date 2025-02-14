package org.example.finddestinationservice.repository;

import org.example.finddestinationservice.data.TripForm;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TripRepository  extends ReactiveCrudRepository<TripForm, String> {

}
