package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonMainLight: MainActivity.writeData("lights/living/toggle"); break;
            case R.id.buttonLamp: MainActivity.writeData("lights/lamp/toggle"); break;
            case R.id.buttonStorm: MainActivity.writeData("lights/storm/toggle"); break;
            case R.id.buttonAggies: MainActivity.writeData("lights/aggies/toggle"); break;
        }
    }

    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
}
