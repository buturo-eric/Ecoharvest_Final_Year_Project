package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.User.Model.RecommendationRequest;
import com.example.Ecoharvest_System.User.Model.YieldRequest;
import com.example.Ecoharvest_System.User.Service.CropRecommendationModel;
import com.example.Ecoharvest_System.User.Service.CropYieldModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private CropYieldModel cropYieldModel;

    @Autowired
    private CropRecommendationModel cropRecommendationModel;

    @PostMapping("/predictYield")
    public double predictYield(@RequestBody YieldRequest request) {
        return cropYieldModel.predict(request);
    }

    @PostMapping("/recommendCrop")
    public String recommendCrop(@RequestBody RecommendationRequest request) {
        return cropRecommendationModel.recommend(request);
    }
}
