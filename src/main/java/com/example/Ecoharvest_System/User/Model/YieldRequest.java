package com.example.Ecoharvest_System.User.Model;

public class YieldRequest {
    private double rain_fall;
    private double fertilizer;
    private double temperature;
    private double nitrogen;
    private double phosphorus;
    private double potassium;

    public double getRain_fall() {
        return rain_fall;
    }

    public void setRain_fall(double rain_fall) {
        this.rain_fall = rain_fall;
    }

    public double getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(double fertilizer) {
        this.fertilizer = fertilizer;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
    }

    public double getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(double phosphorus) {
        this.phosphorus = phosphorus;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }
}
