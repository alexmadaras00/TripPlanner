package org.example.servicedestinationrecommender.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;

import org.json.JSONException;



import java.io.IOException;
import java.util.List;

public interface DestinationRecommenderService {
    List<Destination> getRecommendations(TripForm tripForm) throws IOException;
}
