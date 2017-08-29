package com.example.dawson.mountgoodcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MovieRemoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieremote);
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonMoviePlayPause: MainActivity.writeMovieData("remote/play"); break;
            case R.id.buttonPrevChapter: MainActivity.writeMovieData("remote/prevChapter"); break;
            case R.id.buttonNextChapter: MainActivity.writeMovieData("remote/nextChapter"); break;
            case R.id.buttonMedBack: MainActivity.writeMovieData("remote/medBack"); break;
            case R.id.buttonMedForward: MainActivity.writeMovieData("remote/medForward"); break;
            case R.id.buttonShortBack: MainActivity.writeMovieData("remote/shortBack"); break;
            case R.id.buttonShortForward: MainActivity.writeMovieData("remote/shortForward"); break;
            case R.id.buttonVeryShortBack: MainActivity.writeMovieData("remote/veryShortBack"); break;
            case R.id.buttonVeryShortForward: MainActivity.writeMovieData("remote/veryShortForward"); break;
            case R.id.buttonToggleSubtitles: MainActivity.writeMovieData("remote/toggleSubtitle"); break;
            case R.id.buttonMovieStop: MainActivity.writeMovieData("remote/stop"); break;
        }
    }
}