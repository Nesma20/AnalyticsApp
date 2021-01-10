package com.example.androidtestapp.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidtestapp.Utils.DateAndTimeUtils;
import com.example.androidtestapp.model.ChartPoint;
import com.example.androidtestapp.model.ColoringModel;
import com.example.androidtestapp.model.RandomData;
import com.example.androidtestapp.repository.Repository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private Repository repository;
    private static final String TAG = MainViewModel.class.getSimpleName();
    private MutableLiveData<RandomData> randomDataMutableLiveData = new MutableLiveData<>();
    private ArrayList<ColoringModel> rsrpColoringList = new ArrayList<>();
    private ArrayList<ColoringModel> rsrqColoringList = new ArrayList<>();
    private ArrayList<ColoringModel> sinrColoringList = new ArrayList<>();
    private MutableLiveData<String> rsrpProgressColor = new MutableLiveData<>();
    private MutableLiveData<String> rsrqProgressColor = new MutableLiveData<>();
    private MutableLiveData<String> sinrProgressColor = new MutableLiveData<>();
    private MutableLiveData<ChartPoint> rsrpValueLiveData = new MutableLiveData<>();
    private MutableLiveData<ChartPoint> rsrqValueLiveData = new MutableLiveData<>();
    private MutableLiveData<ChartPoint> sinrValueLiveData = new MutableLiveData<>();

    @ViewModelInject
    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<RandomData> getRandomDataMutableLiveData() {
        return randomDataMutableLiveData;
    }

    public MutableLiveData<String> getRsrpProgressColor() {
        return rsrpProgressColor;
    }

    public MutableLiveData<String> getRsrqProgressColor() {
        return rsrqProgressColor;
    }

    public MutableLiveData<String> getSinrProgressColor() {
        return sinrProgressColor;
    }

    public MutableLiveData<ChartPoint> getRsrpValueLiveData() {
        return rsrpValueLiveData;
    }

    public MutableLiveData<ChartPoint> getRsrqValueLiveData() {
        return rsrqValueLiveData;
    }

    public MutableLiveData<ChartPoint> getSinrValueLiveData() {
        return sinrValueLiveData;
    }

    public void getRandomDataFromAPI() {
        repository.getRandomData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            String time = DateAndTimeUtils.getCurrentTimeAsString();
                            randomDataMutableLiveData.setValue(result);
                            rsrpValueLiveData.setValue(new ChartPoint(result.getRsrp(), time));
                            rsrqValueLiveData.setValue(new ChartPoint(result.getRsrq(), time));
                            sinrValueLiveData.setValue(new ChartPoint(result.getSinr(), time));
                            rsrpProgressColor.setValue(getColorForSpecificRandomDataFromList(result.getRsrp(), rsrpColoringList));
                            rsrqProgressColor.setValue(getColorForSpecificRandomDataFromList(result.getRsrq(), rsrqColoringList));
                            sinrProgressColor.setValue(getColorForSpecificRandomDataFromList(result.getSinr(), sinrColoringList));
                        }
                        , error -> Log.i(TAG, "getDate :Error while getting data  " + error)
                );

    }

    public void getColoringListForValues() {
        repository.getDataFromJsomFileForColoring()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coloringListData -> {
                    rsrpColoringList = coloringListData.getColoringRSRPList();
                    rsrqColoringList = coloringListData.getColoringRSRQList();
                    sinrColoringList = coloringListData.getColoringSINRList();
                });
    }

    private String getColorForSpecificRandomDataFromList(float value, ArrayList<ColoringModel> coloringList) {
        for (int i = 0; i < coloringList.size(); i++) {
            if (i == 0) {
                if (value <= Float.parseFloat(coloringList.get(0).getTo())) {
                    return getColorFromData(coloringList.get(0));
                }

            } else if (i == coloringList.size() - 1) {
                if (value >= Float.parseFloat(coloringList.get(i).getFrom())) {
                    return getColorFromData(coloringList.get(i));
                }

            } else {
                if (value >= Float.parseFloat(coloringList.get(i).getFrom()) && value <= Float.parseFloat(coloringList.get(i).getTo())) {
                    return getColorFromData(coloringList.get(i));
                }
            }

        }
        return "";
    }

    private String getColorFromData(ColoringModel coloringModel) {
        return coloringModel.getColor();
    }

}
