package com.example.androidtestapp.network;

import com.example.androidtestapp.model.RandomData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/random")
    Observable<RandomData> getData();


}
