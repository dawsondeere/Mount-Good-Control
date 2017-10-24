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
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREF_NAME, 0).edit();
        editor.putString("IP", ((EditText) findViewById(R.id.editIP)).getText().toString());
        editor.commit();
        MainActivity.IP = getSharedPreferences(MainActivity.PREF_NAME, 0).getString("IP", "");
        super.onStop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
    public void startRemote(View v) { startActivity(new Intent(this, MovieRemoteActivity.class)); }
}