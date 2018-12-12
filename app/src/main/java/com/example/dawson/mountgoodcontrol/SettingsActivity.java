package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements View.OnTouchListener {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gestureDetector = new GestureDetector(this,new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < MainActivity.SWIPE_THRESHOLD_VELOCITY) { return true; }
                if (direction == Direction.right) { startRemote(null); }
                return true;
            }
        });
        findViewById(R.id.activity_settings).setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((EditText) findViewById(R.id.editIP)).setText(getSharedPreferences(MainActivity.PREF_NAME, 0).getString("IP", ""), TextView.BufferType.NORMAL);
        ((EditText) findViewById(R.id.editPortLights)).setText(getSharedPreferences(MainActivity.PREF_NAME, 0).getString("Light port", ""), TextView.BufferType.NORMAL);
        ((EditText) findViewById(R.id.editPortMusic)).setText(getSharedPreferences(MainActivity.PREF_NAME, 0).getString("Music port", ""), TextView.BufferType.NORMAL);
        ((EditText) findViewById(R.id.editPortMovies)).setText(getSharedPreferences(MainActivity.PREF_NAME, 0).getString("Movie port", ""), TextView.BufferType.NORMAL);
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREF_NAME, 0).edit();
        editor.putString("IP", ((EditText) findViewById(R.id.editIP)).getText().toString());
        editor.putString("Light port", ((EditText) findViewById(R.id.editPortLights)).getText().toString());
        editor.putString("Music port", ((EditText) findViewById(R.id.editPortMusic)).getText().toString());
        editor.putString("Movie port", ((EditText) findViewById(R.id.editPortMovies)).getText().toString());
        editor.commit();
        MainActivity.IP = getSharedPreferences(MainActivity.PREF_NAME, 0).getString("IP", "");
        MainActivity.portLights = getSharedPreferences(MainActivity.PREF_NAME, 0).getString("Light port", "");
        MainActivity.portMusic = getSharedPreferences(MainActivity.PREF_NAME, 0).getString("Music port", "");
        MainActivity.portMovies = getSharedPreferences(MainActivity.PREF_NAME, 0).getString("Movie port", "");
        super.onStop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); finish(); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); finish(); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); finish(); }
    public void startShows(View v) { startActivity(new Intent(this, ShowsActivity.class)); finish(); }
    public void startRemote(View v) { startActivity(new Intent(this, MovieRemoteActivity.class)); finish(); }
    public void shutdownJuke(View v) { MainActivity.writeMusicData("music/shutdown"); }
    public void restartJuke(View v) { MainActivity.writeMusicData("music/restart"); }
}