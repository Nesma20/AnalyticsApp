package com.example.androidtestapp.repository;

import android.content.Context;
import android.util.Log;

import com.example.androidtestapp.Utils.Utils;
import com.example.androidtestapp.model.ColoringListData;
import com.example.androidtestapp.model.RandomData;
import com.example.androidtestapp.network.ApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private ApiService apiService;
    private Gson gson;
    private Context context;

    @Inject
    public Repository(ApiService apiService, @ApplicationContext Context context, Gson gson) {
        this.apiService = apiService;
        this.context = context;
        this.gson = gson;
    }

    public Observable<RandomData> getRandomData() {
        return apiService.getData();
    }

    public Observable<ColoringListData> getDataFromJsomFileForColoring() {
        String jsonFileString = Utils.getJsonFromAssets(context, "Legend.json");
        Log.i("data", "   " + jsonFileString);
        Type coloringListType = new TypeToken<ColoringListData>(){}.getType();
        ColoringListData data = gson.fromJson(jsonFileString, coloringListType);
        Log.i("data", "   " + data.getColoringRSRPList().size());
        Observable<ColoringListData> observable = Observable.just(data);
        return observable;
    }
}
