package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MovieRemoteActivity extends AppCompatActivity implements View.OnTouchListener {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieremote);

        gestureDetector = new GestureDetector(this,new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < MainActivity.SWIPE_THRESHOLD_VELOCITY) { return true; }
                if (direction == Direction.right) { startShows(null); }
                else if (direction == Direction.left) { startSettings(null); }
                return true;
            }
        });
        findViewById(R.id.activity_movieremote).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
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
            case R.id.buttonNextEpisode: MainActivity.writeMovieData("remote/nextEpisode"); break;
            case R.id.buttonNextSeason: MainActivity.writeMovieData("remote/nextSeason"); break;
            case R.id.buttonToggleSubtitles: MainActivity.writeMovieData("remote/toggleSubtitle"); break;
            case R.id.buttonMoveMouse: MainActivity.writeMovieData("remote/moveMouse"); break;
            case R.id.buttonMovieStop: MainActivity.writeMovieData("remote/stop"); break;
            case R.id.buttonTVPower: MainActivity.writeMovieData("tv-remote/power"); break;
            case R.id.buttonTVInput: MainActivity.writeMovieData("tv-remote/input"); break;
            case R.id.buttonTVMute: MainActivity.writeMovieData("tv-remote/mute"); break;
            case R.id.buttonTVVolDown: MainActivity.writeMovieData("tv-remote/vol-down"); break;
            case R.id.buttonTVVolUp: MainActivity.writeMovieData("tv-remote/vol-up"); break;
            case R.id.buttonTVOk: MainActivity.writeMovieData("tv-remote/ok"); break;
            case R.id.buttonTVArrowUp: MainActivity.writeMovieData("tv-remote/arrow-up"); break;
            case R.id.buttonTVArrowDown: MainActivity.writeMovieData("tv-remote/arrow-down"); break;
            case R.id.buttonTVArrowLeft: MainActivity.writeMovieData("tv-remote/arrow-left"); break;
            case R.id.buttonTVArrowRight: MainActivity.writeMovieData("tv-remote/arrow-right"); break;
        }
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); finish(); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); finish(); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); finish(); }
    public void startShows(View v) { startActivity(new Intent(this, ShowsActivity.class)); finish(); }
    public void startSettings(View v) { startActivity(new Intent(this, SettingsActivity.class)); finish(); }
}