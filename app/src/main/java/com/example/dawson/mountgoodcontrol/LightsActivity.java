package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class LightsActivity extends AppCompatActivity implements View.OnTouchListener {
    private static UpdateThread upThread;
    public static LightsActivity la;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);

        la = LightsActivity.this;

        gestureDetector = new GestureDetector(this,new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < MainActivity.SWIPE_THRESHOLD_VELOCITY) { return true; }
                if (direction == Direction.left) { startMusic(null); }
                return true;
            }
        });
        findViewById(R.id.activity_lights).setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        upThread = new LightsActivity.UpdateThread();
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
                    la.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getLightData("lights/status");
                        }
                    });
                    Thread.sleep(1000*2);
                    la.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWhosHomeData("whos-home");
                        }
                    });
                    Thread.sleep(1000*2);
                }
                catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
        }
    }

    public void getLightData(String url) {
        String finalurl = "http://" + MainActivity.IP + ":5000/" + url;
        //System.out.println(finalurl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseLightData(response);
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

    public void getWhosHomeData(String url) {
        String finalurl = "http://" + MainActivity.IP + ":5000/" + url;
        //System.out.println(finalurl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseWhosHomeData(response);
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

    private void parseLightData(String buffer) {
        int swStart = -1;
        int swEnd = -1;
        int stStart = -1;
        int stEnd = -1;
        for (int i = 0; i < 4; i++) {
            swStart = stEnd + 1;
            swEnd = buffer.indexOf(':', swStart);
            stStart = swEnd + 2;
            stEnd = buffer.indexOf('\n', stStart);
            if (stEnd == -1) { stEnd = buffer.length() - 1; }

            String sw = buffer.substring(swStart, swEnd);
            String stat = buffer.substring(stStart, stEnd);

            switch (sw) {
                case "living": updateLights((ImageView) findViewById(R.id.buttonMainLight), stat); break;
                case "lamp": updateLights((ImageView) findViewById(R.id.buttonLamp), stat); break;
                case "storm": updateLights((ImageView) findViewById(R.id.buttonStorm), stat); break;
                case "aggies": updateLights((ImageView) findViewById(R.id.buttonAggies), stat); break;
                case "back-door": updateLights((ImageView) findViewById(R.id.buttonBackDoor), stat); break;
            }
        }
    }

    private void updateLights(ImageView button, String stat) {
        if (stat.equals("ON")) {
            switch (button.getId()) {
                case R.id.buttonMainLight: button.setImageResource(R.drawable.mtgd_living_ma); break;
                case R.id.buttonLamp: button.setImageResource(R.drawable.mtgd_lamp_ma); break;
                case R.id.buttonStorm: button.setImageResource(R.drawable.mtgd_storm_ma); break;
                case R.id.buttonAggies: button.setImageResource(R.drawable.mtgd_aggies_ma); break;
                case R.id.buttonBackDoor: button.setImageResource(R.drawable.mtgd_back_door_ma); break;
            }
        }
        else if (stat.equals("OFF")) {
            switch (button.getId()) {
                case R.id.buttonMainLight: button.setImageResource(R.drawable.mtgd_living_bl); break;
                case R.id.buttonLamp: button.setImageResource(R.drawable.mtgd_lamp_bl); break;
                case R.id.buttonStorm: button.setImageResource(R.drawable.mtgd_storm_bl); break;
                case R.id.buttonAggies: button.setImageResource(R.drawable.mtgd_aggies_bl); break;
                case R.id.buttonBackDoor: button.setImageResource(R.drawable.mtgd_back_door_bl); break;
            }
        }
    }

    private void parseWhosHomeData(String buffer) {
        if (buffer.equals("")) { ((TextView) findViewById(R.id.textWhosHome)).setText("Nobody is home");}
        else { ((TextView) findViewById(R.id.textWhosHome)).setText(buffer); }
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonMainLight: MainActivity.writeData("lights/living/toggle"); break;
            case R.id.buttonLamp: MainActivity.writeData("lights/lamp/toggle"); break;
            case R.id.buttonStorm: MainActivity.writeData("lights/storm/toggle"); break;
            case R.id.buttonAggies: MainActivity.writeData("lights/aggies/toggle"); break;
            case R.id.buttonBackDoor: MainActivity.writeData("lights/back-door/toggle"); break;
        }
    }

    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); finish(); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); finish(); }
    public void startShows(View v) { startActivity(new Intent(this, ShowsActivity.class)); finish(); }
    public void startRemote(View v) { startActivity(new Intent(this, MovieRemoteActivity.class)); finish(); }
    public void startSettings(View v) { startActivity(new Intent(this, SettingsActivity.class)); finish(); }
}