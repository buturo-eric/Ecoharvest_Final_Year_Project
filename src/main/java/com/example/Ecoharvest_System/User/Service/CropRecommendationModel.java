package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.RecommendationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CropRecommendationModel {

    @Value("${flask.api.url}")
    private String flaskApiUrl;

    private final RestTemplate restTemplate;

    public CropRecommendationModel(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String recommend(RecommendationRequest request) {
        String url = flaskApiUrl + "/recommendCrop";
        return restTemplate.postForObject(url, request, String.class);
    }
}
