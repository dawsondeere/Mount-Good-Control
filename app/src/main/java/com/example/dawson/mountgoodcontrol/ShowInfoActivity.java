package com.example.dawson.mountgoodcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);
        final String title = getIntent().getExtras().getString("title");

        ((TextView) findViewById(R.id.showInfoTitle)).setText(title);
        ((ImageView) findViewById(R.id.showInfoImage)).setImageResource(getImage(title));

        populateSpinner(title, getNumSeasons(title));
    }

    private int getNumSeasons(String title) {
        InputStream inputStream = this.getResources().openRawResource(R.raw.seasons);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(title)) {
                    return Integer.parseInt(line.substring(line.indexOf(" - ") + 3));
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error getting number of seasons from file");
        }

        return -1;
    }

    private void populateSpinner(final String title, final int numSeasons) {
        List<String> spinnerArray =  new ArrayList<String>();
        for (int i = 1; i <= numSeasons; i++) {
            if (i < 10) { spinnerArray.add("Season 0" + i); }
            else { spinnerArray.add("Season " + i); }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner seasonSpinner = (Spinner) findViewById(R.id.showSeasons);
        seasonSpinner.setAdapter(adapter);
        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id)
            {
                populateScrollView(title, (String) parentView.getItemAtPosition(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {}
        });
    }

    private void populateScrollView(final String title, final String season) {
        final String seasonNum = season.substring(7);
        final int resourceID = getEpisodeResource(title + " " + seasonNum);

        InputStream inputStream = this.getResources().openRawResource(resourceID);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        LinearLayout layout = (LinearLayout) findViewById(R.id.episodeLayout);
        layout.removeAllViews();

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                final int epNum = Integer.parseInt(line.substring(1,3));
                LinearLayout linLay = new LinearLayout(this);
                linLay.setOrientation(LinearLayout.HORIZONTAL);
                linLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView epTitle = new TextView(this);
                epTitle.setText(line);
                epTitle.setTextSize(20);
                epTitle.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                final int imageHeight = (int) getResources().getDimension(R.dimen.ep_play_height);
                final int imageWidth = (int) getResources().getDimension(R.dimen.ep_play_height);
                ImageView tempImage = new ImageView(this);
                tempImage.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
                tempImage.setId(View.generateViewId());
                tempImage.setImageResource(R.drawable.mtgd_play);
                tempImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filename = formatTitle(title) + "S" + seasonNum + "E" + epNum;
                        MainActivity.writeMovieData("shows/" + filename);
                    }
                });

                linLay.addView(epTitle);
                linLay.addView(tempImage);
                layout.addView(linLay);
            }
        }
        catch (IOException e) {
            System.out.println("Error getting episodes from file");
        }
    }

    private int getEpisodeResource(String title) {
        switch (title) {
            case "Avatar: The Last Airbender 01": return R.raw.avatar_the_last_airbender_s01;
            case "Avatar: The Last Airbender 02": return R.raw.avatar_the_last_airbender_s02;
            case "Avatar: The Last Airbender 03": return R.raw.avatar_the_last_airbender_s03;
            case "The Big Bang Theory 01": return R.raw.the_big_bang_theory_s01;
            case "The Big Bang Theory 02": return R.raw.the_big_bang_theory_s02;
            case "Duck Dynasty 01": return R.raw.duck_dynasty_s01;
            case "Duck Dynasty 02": return R.raw.duck_dynasty_s02;
            case "Duck Dynasty 03": return R.raw.duck_dynasty_s03;
            case "Friday Night Lights 01": return R.raw.friday_night_lights_s01;
            case "Full House 01": return R.raw.full_house_s01;
            case "NCIS 01": return R.raw.ncis_s01;
            case "Reba 01": return R.raw.reba_s01;
            case "White Collar 01": return R.raw.white_collar_s01;
            default: return -1;
        }
    }

    private int getImage(String title) {
        switch (title) { // TODO: change to medium
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
/*
    public void playMovie(View v) {
        String title = ((TextView) findViewById(R.id.showInfoTitle)).getText().toString();
        MainActivity.writeMovieData("shows/" + formatTitle(title));
    }
    */
}
