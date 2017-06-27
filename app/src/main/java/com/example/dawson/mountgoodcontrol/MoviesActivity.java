package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        populateScrollView();
    }

    private void populateScrollView() {
        InputStream inputStream = this.getResources().openRawResource(R.raw.titles);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        LinearLayout layout = (LinearLayout) findViewById(R.id.movieLayout);

        try {
            boolean isDone = false;
            boolean isEmpty = true;
            while (true) {
                LinearLayout linLay = new LinearLayout(this);
                linLay.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int marginBottom = (int) getResources().getDimension(R.dimen.linear_margin_bottom);
                linLayoutParams.setMargins(0,0,0,marginBottom);
                linLay.setLayoutParams(linLayoutParams);

                isEmpty = true;
                for(int i = 0; i < 6; i++) {
                    String title = bufferedReader.readLine();
                    if (title == null) { isDone = true; break; }
                    isEmpty = false;
                    TextView temp = new TextView(this);
                    temp.setId(View.generateViewId());
                    temp.setText(title);

                    int height = 0;
                    if (title.length() > 27) {
                        height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        temp.setLines(3);
                    }
                    else {
                        height = (int) getResources().getDimension(R.dimen.movie_height);
                    }
                    temp.setLayoutParams(new LinearLayout.LayoutParams(0, height, 1f));
                    temp.setGravity(Gravity.CENTER);
                    temp.setPadding(10, 0, 10, 0);

                    int image = getImage(title);
                    temp.setCompoundDrawablesWithIntrinsicBounds(0, image, 0, 0);

                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            displayMovieInfo(v);
                        }
                    });

                    linLay.addView(temp);
                }
                
                if (isEmpty) { break; }
                else {
                    if (linLay.getChildCount() < 6) {
                        View filler = new View(this);
                        float gravity = 6 - linLay.getChildCount();
                        filler.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, gravity));
                        linLay.addView(filler);
                    }
                    layout.addView(linLay);
                    if (isDone) { break; }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error getting title from file");
        }
    }

    private int getImage(String title) {
        if (title.equals("Aladdin")) {
            return R.drawable.aladdin_140x200;
        }
        else {
            return R.drawable.batman_begins_140x200;
        }
    }

    public void displayMovieInfo(View v) {
        TextView textTitle = (TextView) findViewById(v.getId());
        String title = textTitle.getText().toString();
        Intent passIntent = new Intent(this, MovieInfoActivity.class);
        passIntent.putExtra("title", title);
        startActivity(passIntent);
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
}
