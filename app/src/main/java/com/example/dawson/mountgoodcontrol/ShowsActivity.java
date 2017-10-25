package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ShowsActivity extends AppCompatActivity implements View.OnTouchListener {
    private GestureDetector gestureDetector;
    private int numShows;
    private HashMap<Integer, String> titleIds = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        numShows = displayMetrics.widthPixels/displayMetrics.densityDpi;
        populateScrollView();

        gestureDetector = new GestureDetector(this,new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < MainActivity.SWIPE_THRESHOLD_VELOCITY) { return true; }
                if (direction == Direction.right) { startMovies(null); }
                else if (direction == Direction.left) { startRemote(null); }
                return true;
            }
        });
        findViewById(R.id.activity_shows).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void populateScrollView() {
        InputStream inputStream = this.getResources().openRawResource(R.raw.shows);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        LinearLayout layout = (LinearLayout) findViewById(R.id.showsLayout);

        try {
            boolean isDone = false;
            boolean isEmpty;
            while (true) {
                LinearLayout linLayImage = new LinearLayout(this);
                LinearLayout linLayText = new LinearLayout(this);
                linLayImage.setOrientation(LinearLayout.HORIZONTAL);
                linLayText.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int marginBottom = (int) getResources().getDimension(R.dimen.linear_margin_bottom);
                linLayoutParams.setMargins(0,0,0,marginBottom);
                linLayImage.setLayoutParams(linLayoutParams);
                linLayText.setLayoutParams(linLayoutParams);

                isEmpty = true;
                for(int i = 0; i < numShows; i++) {
                    String title = bufferedReader.readLine();
                    if (title == null) { isDone = true; break; }
                    isEmpty = false;
                    ImageView tempImage = new ImageView(this);
                    TextView tempText = new TextView(this);
                    tempImage.setId(View.generateViewId());
                    tempText.setId(View.generateViewId());
                    tempText.setText(title);
                    titleIds.put(tempImage.getId(), title);
                    titleIds.put(tempText.getId(), title);

                    int imageHeight = (int) getResources().getDimension(R.dimen.movie_image_height);
                    tempText.setLines(3);
                    tempText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                    tempImage.setLayoutParams(new LinearLayout.LayoutParams(0, imageHeight, 1f));
                    tempText.setGravity(Gravity.CENTER_HORIZONTAL);
                    tempText.setPadding(10, 0, 10, 0);
                    tempImage.setPadding(10, 0, 10, 0);
                    tempImage.setImageResource(getImage(title));

                    tempText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            displayShowInfo(v);
                        }
                    });
                    tempImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            displayShowInfo(v);
                        }
                    });

                    linLayImage.addView(tempImage);
                    linLayText.addView(tempText);
                }

                if (isEmpty) { break; }
                else {
                    if (linLayImage.getChildCount() < numShows) {
                        View fillerImage = new View(this);
                        View fillerText = new View(this);
                        float gravity = numShows - linLayImage.getChildCount();
                        fillerImage.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, gravity));
                        fillerText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, gravity));
                        linLayImage.addView(fillerImage);
                        linLayText.addView(fillerText);
                    }
                    layout.addView(linLayImage);
                    layout.addView(linLayText);
                    if (isDone) { break; }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error getting title from file");
        }
    }

    private int getImage(String title) {
        switch (title) {
            case "Avatar: The Last Airbender": return R.drawable.tv_small_avatar_the_last_airbender;
            case "The Big Bang Theory": return R.drawable.tv_small_the_big_bang_theory;
            case "Duck Dynasty": return R.drawable.tv_small_duck_dynasty;
            case "Friday Night Lights": return R.drawable.tv_small_friday_night_lights;
            case "Full House": return R.drawable.tv_small_full_house;
            case "NCIS": return R.drawable.tv_small_ncis;
            case "Reba": return R.drawable.tv_small_reba;
            case "White Collar": return R.drawable.tv_small_white_collar;
            default: return R.drawable.small_no_image;
        }
    }

    private void displayShowInfo(View v) {
        String title = titleIds.get(v.getId());
        Intent passIntent = new Intent(this, ShowInfoActivity.class);
        passIntent.putExtra("title", title);
        startActivity(passIntent);
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
    public void startMovies(View v) { startActivity(new Intent(this, MoviesActivity.class)); }
    public void startRemote(View v) { startActivity(new Intent(this, MovieRemoteActivity.class)); }
    public void startSettings(View v) { startActivity(new Intent(this, SettingsActivity.class)); }
}
