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

    private void writeData(int digit1, int digit2) {
        byte[] data = {(byte) digit1, (byte) digit2};
        MainActivity.ConnectedThread.write(data);
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonMainLight: writeData(49,57); break;
            case R.id.buttonLamp: writeData(50,48); break;
            case R.id.buttonStorm: writeData(50,49); break;
            case R.id.buttonAggies: writeData(50,50); break;
            //case R.id.buttonFan0: writeData(50,51); break;
            //case R.id.buttonFan1: writeData(50,52); break;
            //case R.id.buttonFan2: writeData(50,53); break;
            //case R.id.buttonFan3: writeData(50,54); break;
            //case R.id.buttonStartClap: writeData(51,52); break;
            //case R.id.buttonStopClap: writeData(51,53); break;
        }
    }

    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
}
