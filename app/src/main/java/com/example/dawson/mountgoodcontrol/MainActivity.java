package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.getpebble.android.kit.PebbleKit;
//import com.getpebble.android.kit.util.PebbleDictionary;

//import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static RequestQueue queue;
    public static final String BASEURL = "http://50.24.190.215:5000/";
    public static final String BASEURLMOVIE = "http://50.24.190.215:5050/";
    public static final float SWIPE_THRESHOLD_VELOCITY = 200;
    private static final DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    //private static PebbleThread pebbleThread;
    //private static final UUID pebUUID = UUID.fromString("1786e94e-ad90-496f-819a-0c9db99c174b");

    public static void writeData(String url) {
        String finalurl = BASEURL + url;
        //System.out.println(finalurl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println("Response is: "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                    }
                }
        );

        stringRequest.setRetryPolicy(retryPolicy);
        queue.add(stringRequest);
    }

    public static void writeMovieData(String url) {
        String finalurl = BASEURLMOVIE + url;
        //System.out.println(finalurl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println("Response is: "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                    }
                }
        );

        stringRequest.setRetryPolicy(retryPolicy);
        queue.add(stringRequest);
    }
/*
    private class PebbleThread extends Thread {
        private boolean cont;

        private PebbleThread() { cont = true; }
        private void cancel() { cont = false; }

        public void run() {
            Intent batteryStatus = getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (batteryStatus != null) {
                while (cont) {
                    try {
                        PebbleDictionary dict = new PebbleDictionary();
                        final int AppKeyBatteryLevel = 0;
                        final int batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                        dict.addInt32(AppKeyBatteryLevel, batteryLevel);
                        PebbleKit.sendDataToPebble(getApplicationContext(), pebUUID, dict);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }
            }
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (PebbleKit.isWatchConnected(getApplicationContext())) {
            pebbleThread = new PebbleThread();
            pebbleThread.start();
        }
        */
        queue = Volley.newRequestQueue(this);
        startActivity(new Intent(this, LightsActivity.class));
    }
/*
    @Override
    protected void onDestroy() {
        if (pebbleThread != null) { pebbleThread.cancel(); }
        super.onDestroy();
    }
    */
}
