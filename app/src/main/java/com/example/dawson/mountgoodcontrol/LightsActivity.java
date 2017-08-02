package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class LightsActivity extends AppCompatActivity {
    private static UpdateThread upThread;
    public static LightsActivity la;
    private static Button buttonLiving;
    private static Button buttonLamp;
    private static Button buttonStorm;
    private static Button buttonAggies;
    private static Drawable buttonBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        la = LightsActivity.this;
        setContentView(R.layout.activity_lights);
        buttonLiving = (Button) findViewById(R.id.buttonMainLight);
        buttonLamp = (Button) findViewById(R.id.buttonLamp);
        buttonStorm = (Button) findViewById(R.id.buttonStorm);
        buttonAggies = (Button) findViewById(R.id.buttonAggies);
        buttonBackground = buttonLiving.getBackground();
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
                            getData("lights/status");
                        }
                    });
                    Thread.sleep(1500);
                }
                catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
        }
    }

    public void getData(String url) {
        String finalurl = MainActivity.BASEURL + url;
        //System.out.println(finalurl);
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

    private void parseData(String buffer) {
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
}