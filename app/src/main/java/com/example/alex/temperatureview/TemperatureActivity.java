package com.example.alex.temperatureview;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TemperatureActivity extends AppCompatActivity {
    WestherView westherView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        westherView = (WestherView) findViewById(R.id.weather);
        ObjectAnimator.ofFloat(westherView,"temperature",0,30).setDuration(2000).start();

    }
}
