package com.example.androidtestapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
/* Model to get all lists' data from file */
public class ColoringListData {
    @SerializedName("RSRP")
    private ArrayList<ColoringModel> coloringRSRPList = new ArrayList<>();
    @SerializedName("RSRQ")
    private ArrayList<ColoringModel> coloringRSRQList = new ArrayList<>();
    @SerializedName("SINR")
    private ArrayList<ColoringModel> coloringSINRList = new ArrayList<>();

    public ArrayList<ColoringModel> getColoringRSRPList() {
        return coloringRSRPList;
    }

    public void setColoringRSRPList(ArrayList<ColoringModel> coloringRSRPList) {
        this.coloringRSRPList = coloringRSRPList;
    }

    public ArrayList<ColoringModel> getColoringRSRQList() {
        return coloringRSRQList;
    }

    public void setColoringRSRQList(ArrayList<ColoringModel> coloringRSRQList) {
        this.coloringRSRQList = coloringRSRQList;
    }

    public ArrayList<ColoringModel> getColoringSINRList() {
        return coloringSINRList;
    }

    public void setColoringSINRList(ArrayList<ColoringModel> coloringSINRList) {
        this.coloringSINRList = coloringSINRList;
    }
}
