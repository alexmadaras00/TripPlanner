package org.example.finddestinationservice.service;

import okhttp3.*;
import org.example.finddestinationservice.data.Activity;
import org.example.finddestinationservice.data.Day;
import org.example.finddestinationservice.data.Schedule;
import org.example.finddestinationservice.data.TripForm;
import org.example.finddestinationservice.repository.TripRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleActivitiesServiceImpl implements ScheduleActivitiesService {

    @Value("${edenAPI.key}")
    private String apiKey;
    @Autowired
    private TripRepository tripRepository;
    @Override
    public List<Day> getActivities(TripForm tripForm, String destination) throws IOException, JSONException {
        String promptText = "You are an AI travel advisor assisting a group of " + tripForm.getNumberOfTravellers() + " friends who are planning a trip to " + destination + " from " + tripForm.getStartDate() + " to " + tripForm.getEndDate() + ". " +
                "They are looking for a " + tripForm.getMotivation() + " and " + tripForm.getBudget() + " budget schedule. " +
                "They have previously enjoyed places that are good for " + tripForm.getGroupType() + ". Based on this information, please provide a detailed schedule for their trip that aligns with their preferences and interests. Write per day, including only hours and the activity name as short as possible. No other text.  List them in one line, no delimitation.";
        String url = "https://api.edenai.run/v2/text/chat";
        String generatedText = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Set a longer timeout
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"providers\": \"openai\", \"text\": \"" + promptText + "\", \"chatbot_global_action\": \"Act as an AI travel advisor\", \"previous_history\": [], \"temperature\": 0.0, \"max_tokens\": 150, \"fallback_providers\": \"\"}", mediaType);
        Request request = new Request.Builder()
                .url("https://api.edenai.run/v2/text/chat")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + apiKey)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            System.out.println("Initial response: " + responseBody);
            JSONObject genTextObject = new JSONObject(responseBody);
            JSONArray messages = genTextObject.getJSONObject("openai").getJSONArray("message");
            System.out.println("Message: " + generatedText);
            generatedText = messages.getJSONObject(1).getString("message");
        } else {
            throw new JSONException("Error");
        }
        System.out.println("Response: " + generatedText);
        return parseResp(generatedText);
    }

    private List<Day> parseResp(String response) {
            try {
                Schedule schedule = new Schedule();
                List<Day> days = new ArrayList<>();
                String[] lines = response.split("\n");
                int dayNumber = 1;
                for (String line : lines) {
                    Day day = new Day();
                    day.setNumber(dayNumber++);
                    List<Activity> activityList = getActivities(line);
                    day.setActivityList(activityList);
                    days.add(day);
                }
                schedule.setNumberOfDays(days.size());
                schedule.setDays(days);
                return schedule.getDays();
            } catch (Exception e) {
                return null;
            }
        }

    @NotNull
    private static List<Activity> getActivities(String line) {
        String[] activities = line.split(", ");
        List<Activity> activityList = new ArrayList<>();
        for (String activityName : activities) {
            Activity activity = new Activity();
            activity.setName(activityName);
            // Assuming startTime is not provided in the response
            activity.setStartTime(""); // You can set this if needed
            activityList.add(activity);
        }
        return activityList;
    }

    public Mono<TripForm> saveTrip(TripForm trip) {
        return tripRepository.save(trip);
    }

    public Mono<TripForm> getTrip(String id) {
        return tripRepository.findById(id);
    }

    public Mono<Void> deleteTrip(String id) {
        return tripRepository.deleteById(id);
    }
}

