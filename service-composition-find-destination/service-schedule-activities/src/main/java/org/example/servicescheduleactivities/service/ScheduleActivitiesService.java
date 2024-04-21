package org.example.servicescheduleactivities.service;

import org.example.servicescheduleactivities.data.TripForm;
import org.example.servicescheduleactivities.domain.Day;
import org.example.servicescheduleactivities.domain.Schedule;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface ScheduleActivitiesService {
    List<Day> getActivities(TripForm tripForm, String destination) throws IOException, JSONException;
}
