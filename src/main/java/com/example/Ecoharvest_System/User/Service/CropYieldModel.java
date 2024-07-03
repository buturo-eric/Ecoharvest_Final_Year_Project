package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.YieldRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CropYieldModel {

    @Value("${flask.api.url}")
    private String flaskApiUrl;

    private final RestTemplate restTemplate;

    public CropYieldModel(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double predict(YieldRequest request) {
        String url = flaskApiUrl + "/predictYield";
        return restTemplate.postForObject(url, request, Double.class);
    }
}
