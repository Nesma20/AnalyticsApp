package com.example.androidtestapp.model;

/* to update charts we need points as model */
public class ChartPoint {
    float value;
    String timeInSeconds;

    public ChartPoint(float value, String timeInSeconds) {
        this.value = value;
        this.timeInSeconds = timeInSeconds;
    }

    public float getValue() {
        return value;
    }

    public String getTimeInSeconds() {
        return timeInSeconds;
    }
}
