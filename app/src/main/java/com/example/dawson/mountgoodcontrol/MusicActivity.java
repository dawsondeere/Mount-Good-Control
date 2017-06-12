package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MusicActivity extends AppCompatActivity {
    private static TextView songN;
    private static TextView songA;
    private static TextView songV;
    public static MusicActivity mn;

    private static void updateText() {
        songN.setText(MainActivity.newSongName);
        songA.setText(MainActivity.newSongArtist);
        songV.setText(MainActivity.newSongVolume);
    }

    public static void runUpdate() {
        new Thread() {
            public void run() {
                try {
                    mn.runOnUiThread(new Runnable() {
                        @Override
                        public void run() { updateText(); }
                    });
                    Thread.sleep(300);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mn = MusicActivity.this;
        setContentView(R.layout.activity_music);
        songN = (TextView) findViewById(R.id.songName);
        songA = (TextView) findViewById(R.id.songArtist);
        songV = (TextView) findViewById(R.id.songVolume);
        updateText();
    }

    private void writeData(int digit1, int digit2) {
        byte[] data = {(byte) digit1, (byte) digit2};
        MainActivity.ConnectedThread.write(data);
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonPlayPause: writeData(50,55); break;
            case R.id.buttonPrevSong: writeData(50,56); break;
            case R.id.buttonNextSong: writeData(50,57); break;
            case R.id.buttonVolDown: writeData(51,48); break;
            case R.id.buttonVolUp: writeData(51,49); break;
            case R.id.buttonStartMusic: if (MainActivity.newSongName.contains("No song")) { writeData(51,50); } break;
            case R.id.buttonStopMusic: writeData(51,51); break;
        }
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
}
