package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity implements View.OnTouchListener {
    public static MusicActivity ma;
    private static UpdateThread upThread;
    private GestureDetector gestureDetector;
    private String playlistName;

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

        populateSpinner();
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

    private void populateSpinner() {
        List<String> spinnerArray =  new ArrayList<String>();
        InputStream inputStream = this.getResources().openRawResource(R.raw.playlists);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                spinnerArray.add(line);
            }
        }
        catch (IOException e) {
            System.out.println("Error getting playlists from file");
        }

        System.out.println("SpinnerArray size: " + spinnerArray.size());
        for (int i = 0; i < spinnerArray.size(); i++) {
            System.out.println(spinnerArray.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner playlistSpinner = (Spinner) findViewById(R.id.spinnerPlaylists);
        playlistSpinner.setAdapter(adapter);
        playlistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id)
            {
                playlistName = (String) parentView.getItemAtPosition(pos);
                //MainActivity.writeData("music/start/" + formatTitle(playlistName));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {}
        });
    }

    public void getData(String url) {
        String finalurl = "http://" + MainActivity.IP + ":" + MainActivity.portMusic + "/" + url;
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
            case R.id.buttonPlayPause: MainActivity.writeMusicData("music/play"); break;
            case R.id.buttonPrevSong: MainActivity.writeMusicData("music/prev"); break;
            case R.id.buttonNextSong: MainActivity.writeMusicData("music/next"); break;
            case R.id.buttonVolDown: MainActivity.writeMusicData("music/volDown"); break;
            case R.id.buttonVolUp: MainActivity.writeMusicData("music/volUp"); break;
            case R.id.buttonStopMusic: MainActivity.writeMusicData("music/stop"); break;
            case R.id.buttonStartMusic: if (((TextView) findViewById(R.id.songName)).getText().toString().contains("No song")) { MainActivity.writeMusicData("music/start/" + formatTitle(playlistName)); } break;
        }
    }

    private String formatTitle(String title) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            switch (c) {
                case ' ': str.append("-"); break;
                case '\'': break;
                case ':': break;
                case ',': break;
                case '.': break;
                default: str.append(Character.toLowerCase(c));
            }
        }

        return str.toString();
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); finish(); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); finish(); }
    public void startShows(View v) { startActivity(new Intent(this, ShowsActivity.class)); finish(); }
    public void startRemote(View v) { startActivity(new Intent(this, MovieRemoteActivity.class)); finish(); }
    public void startSettings(View v) { startActivity(new Intent(this, SettingsActivity.class)); finish(); }
}
