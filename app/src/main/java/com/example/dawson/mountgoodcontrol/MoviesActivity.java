package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    private void writeData(int digit1, int digit2) {
        byte[] data = {(byte) digit1, (byte) digit2};
        MainActivity.ConnectedThread.write(data);
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case 0: writeData(1,1);
            default: writeData(0,0);
        }
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
}
