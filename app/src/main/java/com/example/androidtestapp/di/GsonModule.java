package com.example.androidtestapp.di;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class GsonModule {
    @Provides
    @Singleton
    public static Gson getGsonObject(){
        return new Gson();
    }
}
