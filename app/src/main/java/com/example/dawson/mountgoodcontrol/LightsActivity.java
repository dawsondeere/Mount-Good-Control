package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class LightsActivity extends AppCompatActivity implements View.OnTouchListener {
    private static UpdateThread upThread;
    public static LightsActivity la;
    private static Button buttonLiving;
    private static Button buttonLamp;
    private static Button buttonStorm;
    private static Button buttonAggies;
    private static TextView textWhosHome;
    private static Drawable buttonBackground;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);

        la = LightsActivity.this;
        buttonLiving = (Button) findViewById(R.id.buttonMainLight);
        buttonLamp = (Button) findViewById(R.id.buttonLamp);
        buttonStorm = (Button) findViewById(R.id.buttonStorm);
        buttonAggies = (Button) findViewById(R.id.buttonAggies);
        textWhosHome = (TextView) findViewById(R.id.textWhosHome);
        buttonBackground = buttonLiving.getBackground();

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
        MainActivity.IP = getPreferences(0).getString("IP", "");
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

            if (sw.equals("living")) { updateLights(buttonLiving, stat); }
            else if (sw.equals("lamp")) { updateLights(buttonLamp, stat); }
            else if (sw.equals("storm")) { updateLights(buttonStorm, stat); }
            else if (sw.equals("aggies")) { updateLights(buttonAggies, stat); }
        }
    }

    private void updateLights(Button button, String stat) {
        if (stat.equals("ON")) {
            button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
            button.setTextColor(Color.WHITE);
        }
        else if (stat.equals("OFF")) {
            button.setBackground(buttonBackground);
            button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        }
    }

    private void parseWhosHomeData(String buffer) {
        if (buffer.equals("")) { textWhosHome.setText("Nobody is home");}
        else { textWhosHome.setText(buffer); }
    }

    public void sendData(View v) {
        switch (v.getId()) {
            case R.id.buttonMainLight: MainActivity.writeData("lights/living/toggle"); break;
            case R.id.buttonLamp: MainActivity.writeData("lights/lamp/toggle"); break;
            case R.id.buttonStorm: MainActivity.writeData("lights/storm/toggle"); break;
            case R.id.buttonAggies: MainActivity.writeData("lights/aggies/toggle"); break;
        }
    }

    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
    public void startSettings(View v) { startActivity(new Intent(this, SettingsActivity.class)); }
}