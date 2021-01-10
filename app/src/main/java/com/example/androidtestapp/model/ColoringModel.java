package com.example.androidtestapp.model;

import com.google.gson.annotations.SerializedName;

public class ColoringModel {
    @SerializedName("From")
    String from;
    @SerializedName("To")
    String to;
    @SerializedName("Color")
    String color;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
