package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MusicActivity extends AppCompatActivity implements View.OnTouchListener {
    public static MusicActivity ma;
    private static UpdateThread upThread;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ma = MusicActivity.this;
        setContentView(R.layout.activity_music);

        gestureDetector = new GestureDetector(this,new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < MainActivity.SWIPE_THRESHOLD_VELOCITY) { return true; }
                if (direction == Direction.right) { startLights(null); }
                else if (direction == Direction.left) { startMovies(null); }
                return true;
            }
        });
        findViewById(R.id.activity_music).setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        upThread = new UpdateThread();
        upThread.start();
    }

    @Override
    protected void onStop() {
        upThread.cancel();
        super.onStop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private class UpdateThread extends Thread {
        private boolean cont;

        private UpdateThread() {
            cont = true;
        }
        private void cancel() {
            cont = false;
        }

        public void run() {
            while (cont) {
                try {
                    ma.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getData("music/data");
                        }
                    });
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
        }
    }

    public void getData(String url) {
        String finalurl = "http://" + MainActivity.IP + ":5000/" + url;
        //System.out.println(finalurl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                    }
                }
        );

        stringRequest.setRetryPolicy(MainActivity.retryPolicy);
        MainActivity.queue.add(stringRequest);
    }

    private void parseData(String buffer) {
        int pos1 = buffer.indexOf("!");
        int pos2 = buffer.indexOf("@");
        int pos3 = buffer.indexOf("#");
        String songName = buffer.substring(pos1+1, pos2);
        String songArtist = buffer.substring(pos2+1, pos3);
        String songVolume = "Volume: " + buffer.substring(pos3+1);

        if (songName.contains("media player")) { songName = "No song"; }
        else if (songName.equals("1 ")) { songName = "No song"; }

        if (songArtist.contains("media player")) { songArtist = "is playing"; }
        else if (songArtist.contains("playlist")) { songArtist = "is playing"; }

        if (!songArtist.contains("is playing")) { songArtist = "by " + songArtist; }

        ((TextView) findViewById(R.id.songName)).setText(songName);
        ((TextView) findViewById(R.id.songArtist)).setText(songArtist);
        ((TextView) findViewById(R.id.songVolume)).setText(songVolume);
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonPlayPause: MainActivity.writeData("music/play"); break;
            case R.id.buttonPrevSong: MainActivity.writeData("music/prev"); break;
            case R.id.buttonNextSong: MainActivity.writeData("music/next"); break;
            case R.id.buttonVolDown: MainActivity.writeData("music/volDown"); break;
            case R.id.buttonVolUp: MainActivity.writeData("music/volUp"); break;
            case R.id.buttonStartMusic: if (((TextView) findViewById(R.id.songName)).getText().toString().contains("No song")) { MainActivity.writeData("music/start"); } break;
            case R.id.buttonStopMusic: MainActivity.writeData("music/stop"); break;
        }
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
    public void startShows(View v) { startActivity(new Intent(this, ShowsActivity.class)); }
    public void startRemote(View v) { startActivity(new Intent(this, MovieRemoteActivity.class)); }
    public void startSettings(View v) { startActivity(new Intent(this, SettingsActivity.class)); }
}
