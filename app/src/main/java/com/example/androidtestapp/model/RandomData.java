package com.example.androidtestapp.model;

import com.google.gson.annotations.SerializedName;

public class RandomData {
    @SerializedName("RSRP")
   private float rsrp;
    @SerializedName("RSRQ")
    private float rsrq;
    @SerializedName("SINR")
   private float sinr;

    public float getRsrp() {
        return rsrp;
    }

    public float getRsrq() {
        return rsrq;
    }

    public float getSinr() {
        return sinr;
    }

    public void setRsrp(float rsrp) {
        this.rsrp = rsrp;
    }

    public void setRsrq(float rsrq) {
        this.rsrq = rsrq;
    }

    public void setSinr(float sinr) {
        this.sinr = sinr;
    }
}
