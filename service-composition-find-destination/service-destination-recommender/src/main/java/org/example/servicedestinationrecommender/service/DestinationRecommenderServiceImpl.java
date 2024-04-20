package org.example.servicedestinationrecommender.service;

import okhttp3.*;
import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DestinationRecommenderServiceImpl implements DestinationRecommenderService {
    @Value("${edenAPI.key}")
    private String apiKey;

    @Override
    public List<Destination> getRecommendations(TripForm tripForm) throws IOException, JSONException {
        String promptText = "You are a seasoned travel advisor assisting a group of enthusiastic travelers. They've provided the following details for their upcoming trip: " +
                "1. **Home**:" + tripForm.getHomeLocation() + " " +
                "2. **Travel Period**:" + tripForm.getStartDate() + " to +" + tripForm.getEndDate() + " " +
                "3. **Number of Travelers**: " + tripForm.getNumberOfTravellers() + " " +
                "4. **Group Type**: " + tripForm.getGroupType() + " " +
                "5. **Budget**: " + tripForm.getBudget() + " " +
                "6. **Motivation**: " + tripForm.getMotivation() + " " +
                "7. **Previous Reviews**: " + tripForm.getReviewList() + " " +
                " " +
                "Based on this information, recommend top 5 ideal travel destination that aligns with their preferences and interests. Give them a very short answer. Just list the destinations, no more text.";
        String url = "https://api.edenai.run/v2/text/chat";
        String generatedText = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Set a longer timeout
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"providers\": \"openai\", \"text\": \"" + promptText + "\", \"chatbot_global_action\": \"Act as a seasoned travel advisor assisting a group of enthusiastic travelers.\", \"previous_history\": [], \"temperature\": 0.0, \"max_tokens\": 150, \"fallback_providers\": \"\"}", mediaType);
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
        }
        else{
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

    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }
}
