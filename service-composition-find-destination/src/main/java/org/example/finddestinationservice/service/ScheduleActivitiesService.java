package org.example.finddestinationservice.service;

import org.example.finddestinationservice.data.Day;
import org.example.finddestinationservice.data.TripForm;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface ScheduleActivitiesService {
    List<Day> getActivities(TripForm tripForm, String destination) throws IOException, JSONException;
}
