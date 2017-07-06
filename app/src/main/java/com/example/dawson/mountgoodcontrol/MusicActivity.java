package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MusicActivity extends AppCompatActivity {
    private static TextView songN;
    private static TextView songA;
    private static TextView songV;
    public static MusicActivity mn;
    private static UpdateThread upThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mn = MusicActivity.this;
        setContentView(R.layout.activity_music);
        songN = (TextView) findViewById(R.id.songName);
        songA = (TextView) findViewById(R.id.songArtist);
        songV = (TextView) findViewById(R.id.songVolume);
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
                    mn.runOnUiThread(new Runnable() {
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

    public static void getData(String url) {
        String finalurl = MainActivity.BASEURL + url;
        System.out.println(finalurl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalurl,
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

        MainActivity.queue.add(stringRequest);
    }

    private static void parseData(String buffer) {
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

        songN.setText(songName);
        songA.setText(songArtist);
        songV.setText(songVolume);
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonPlayPause: MainActivity.writeData("music/play"); break;
            case R.id.buttonPrevSong: MainActivity.writeData("music/prev"); break;
            case R.id.buttonNextSong: MainActivity.writeData("music/next"); break;
            case R.id.buttonVolDown: MainActivity.writeData("music/volDown"); break;
            case R.id.buttonVolUp: MainActivity.writeData("music/volUp"); break;
            case R.id.buttonStartMusic: if (songN.getText().toString().contains("No song")) { MainActivity.writeData("music/start"); } break;
            case R.id.buttonStopMusic: MainActivity.writeData("music/stop"); break;
        }
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
}
