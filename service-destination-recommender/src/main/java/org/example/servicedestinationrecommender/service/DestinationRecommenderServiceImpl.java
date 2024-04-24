package org.example.servicedestinationrecommender.service;

import org.example.servicedestinationrecommender.data.Trip;
import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DestinationRecommenderServiceImpl implements DestinationRecommenderService {
    @Value("${edenAPI.key}")
    private String apiKey;
    private final TripRepository tripRepository;

    @Autowired
    public DestinationRecommenderServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Destination> getRecommendations(TripForm tripForm) throws IOException {
        String promptText = "You are a seasoned travel advisor assisting a group of enthusiastic travelers. They've provided the following details for their upcoming trip: " +
                "1. **Home**:" + tripForm.getHomeLocation() + " " +
                "2. **Travel Period**:" + tripForm.getStartDate() + " to +" + tripForm.getEndDate() + " " +
                "3. **Number of Travelers**: " + tripForm.getNumberOfTravellers() + " " +
                "4. **Group Type**: " + tripForm.getGroupType() + " " +
                "5. **Budget**: " + tripForm.getBudget() + " " +
                "6. **Motivation**: " + tripForm.getMotivation() + " " +
                " " +
                "Based on this information, recommend top 5 ideal travel destination that aligns with their preferences and interests. Give them a very short answer. Just list the destinations, no more text.";

        String url = "https://api.edenai.run/v2/text/chat";
        String generatedText = "";

        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("content-type", "application/json");
        connection.setRequestProperty("authorization", "Bearer " + apiKey);
        connection.setConnectTimeout(30000); // Set a longer timeout
        connection.setReadTimeout(30000);
        connection.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("providers", "openai");
            requestBody.put("text", promptText);
            requestBody.put("chatbot_global_action", "Act as a seasoned travel advisor assisting a group of enthusiastic travelers.");
            requestBody.put("previous_history", new JSONArray());
            requestBody.put("temperature", 0.0);
            requestBody.put("max_tokens", 150);
            requestBody.put("fallback_providers", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                JSONObject responseObject = new JSONObject(response.toString());
                JSONArray messages = responseObject.getJSONObject("openai").getJSONArray("message");
                generatedText = messages.getJSONObject(1).getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            getErrorPage();
        }

        return parse(generatedText);
    }

    private ArrayList<Destination> parse(String response) {
        ArrayList<Destination> destinations = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)\\.\\s*(\\w+),\\s*(\\w+)");
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            String city = matcher.group(2);
            String country = matcher.group(3);
            destinations.add(new Destination(city, country));
        }
        return destinations;
    }

    public void saveTrip(Trip trip) {
        Mono<Trip> t = tripRepository.save(trip);
        t.subscribe(System.out::println, System.out::println);
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }
}
