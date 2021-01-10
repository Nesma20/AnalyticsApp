package com.example.androidtestapp.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.androidtestapp.R;
import com.example.androidtestapp.databinding.ActivityMainBinding;
import com.example.androidtestapp.model.ChartPoint;
import com.example.androidtestapp.model.RandomData;
import com.example.androidtestapp.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    MainViewModel viewModel;
    ActivityMainBinding binding;
    Cartesian cartisian;
    List<DataEntry> seriesDataForRsrpValues = new ArrayList<>();
    List<DataEntry> seriesDataForRsrqValues = new ArrayList<>();
    List<DataEntry> seriesDataForSinrValues = new ArrayList<>();
    Line rsrpSeries, rsrqSeries, sinrSeries;
    Set setRsrp, setRsrq, setSinr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        cartisian = AnyChart.line();
        cartisian.animation(true);
        cartisian.crosshair().enabled(true);
        cartisian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartisian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartisian.yScale().minimum(-130.00);
        cartisian.yScale().maximum(30);
        seriesDataForRsrpValues = new ArrayList<>();
        seriesDataForRsrpValues.add(new ValueDataEntry("00:00", -110.0));
        seriesDataForRsrqValues.add(new ValueDataEntry("00:00", -20));
        seriesDataForSinrValues.add(new ValueDataEntry("00:00", -2));
        setRsrp = Set.instantiate();
        setRsrq = Set.instantiate();
        setSinr = Set.instantiate();
        setRsrp.data(seriesDataForRsrpValues);
        setRsrq.data(seriesDataForRsrqValues);
        setSinr.data(seriesDataForSinrValues);
        Mapping series1Mapping = setRsrp.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = setRsrq.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = setSinr.mapAs("{ x: 'x', value: 'value3' }");
        rsrpSeries = cartisian.line(series1Mapping);
        rsrpSeries.name(getString(R.string.rsrp_txt));
        rsrpSeries.hovered().markers().enabled(true);
        rsrpSeries.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        rsrpSeries.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        rsrqSeries = cartisian.line(series2Mapping);
        rsrqSeries.name(getString(R.string.rsrq_txt));
        rsrqSeries.hovered().markers().enabled(true);
        rsrqSeries.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        rsrqSeries.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        sinrSeries = cartisian.line(series3Mapping);
        sinrSeries.name(getString(R.string.rsrq_txt));
        sinrSeries.hovered().markers().enabled(true);
        sinrSeries.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        sinrSeries.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartisian.legend().enabled(true);
        cartisian.legend().fontSize(13d);
        cartisian.legend().padding(0d, 0d, 10d, 0d);
        binding.anyChartView.setChart(cartisian);
        binding.anyChartView.setProgressBar(binding.progressBarForChart);

        viewModel.getColoringListForValues();
        viewModel.getRandomDataFromAPI();

        // initiate observers for updating ui
        observeToUpdateData();
        observeToUpdateRSRPLineInChart();
        observeToUpdateRSRQLineInChart();
        observeToUpdateSINRLineInChart();
        observeForGettingRSRPProgressColor();
        observeForGettingRSRQProgressColor();
        observeForGettingSINRProgressColor();

    }

    private void observeToUpdateRSRPLineInChart() {
        viewModel.getRsrpValueLiveData().observe(this, new Observer<ChartPoint>() {
            @Override
            public void onChanged(ChartPoint point) {
                seriesDataForRsrpValues.add(new ValueDataEntry(point.getTimeInSeconds(), point.getValue()));
                setRsrp.data(seriesDataForRsrpValues);
            }

        });
    }

    private void observeToUpdateRSRQLineInChart() {
        viewModel.getRsrqValueLiveData().observe(this, new Observer<ChartPoint>() {
            @Override
            public void onChanged(ChartPoint point) {
                seriesDataForRsrqValues.add(new ValueDataEntry(point.getTimeInSeconds(), point.getValue()));
                setRsrq.data(seriesDataForRsrqValues);
            }

        });
    }

    private void observeToUpdateSINRLineInChart() {
        viewModel.getSinrValueLiveData().observe(this, new Observer<ChartPoint>() {
            @Override
            public void onChanged(ChartPoint point) {
                seriesDataForSinrValues.add(new ValueDataEntry(point.getTimeInSeconds(), point.getValue()));
                setSinr.data(seriesDataForSinrValues);
            }

        });
    }

    private void observeToUpdateData() {
        viewModel.getRandomDataMutableLiveData().observe(this, new Observer<RandomData>() {
            @Override
            public void onChanged(RandomData randomData) {
                new Handler(getMainLooper()).postDelayed(() -> {
                    viewModel.getRandomDataFromAPI();
                    setTextAndProgressForValue(binding.rsrpProgressTxtView, binding.rsrqProgressBar, randomData.getRsrp());
                    setTextAndProgressForValue(binding.rsrqProgressTxtView, binding.rsrqProgressBar, randomData.getRsrq());
                    setTextAndProgressForValue(binding.sinrProgressTxtView, binding.sinrProgressBar, randomData.getSinr());
                }, 2000);
            }
        });
    }

    private void setColorForProgressBar(ProgressBar progressBar, String color) {
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }

    private void setTextAndProgressForValue(TextView textView, ProgressBar progressBar, float value) {
        textView.setText(String.format("%s ", value));
        progressBar.setProgress((int) value);
    }

    private void observeForGettingRSRPProgressColor() {
        viewModel.getRsrpProgressColor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                setColorForProgressBar(binding.rsrpProgressBar, s);
            }
        });

    }

    private void observeForGettingRSRQProgressColor() {
        viewModel.getRsrqProgressColor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                setColorForProgressBar(binding.rsrqProgressBar, s);
            }
        });
    }

    private void observeForGettingSINRProgressColor() {
        viewModel.getSinrProgressColor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                setColorForProgressBar(binding.sinrProgressBar, s);
            }
        });
    }
}