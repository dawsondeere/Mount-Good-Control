package com.example.dawson.mountgoodcontrol;

import android.media.Image;
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
        ((ImageView) findViewById(R.id.showInfoImage)).setImageResource(getSeasonImgResource(title + " 01"));

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
        final int epResID = getEpisodeResource(title + " " + seasonNum);
        final int seasImgID = getSeasonImgResource(title + " " + seasonNum);
        ((ImageView) findViewById(R.id.showInfoImage)).setImageResource(seasImgID);

        InputStream inputStream = this.getResources().openRawResource(epResID);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        LinearLayout layout = (LinearLayout) findViewById(R.id.episodeLayout);
        layout.removeAllViews();

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                final String epNum = line.substring(1,3);
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
                        String filename = formatTitle(title) + "-s" + seasonNum + "e" + epNum;
                        MainActivity.writeMovieData("show/" + filename);
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

    private int getSeasonImgResource(final String title) {
        switch (title) {
            case "Avatar: The Last Airbender 01": return R.drawable.tv_medium_avatar_the_last_airbender_01;
            case "Avatar: The Last Airbender 02": return R.drawable.tv_medium_avatar_the_last_airbender_02;
            case "Avatar: The Last Airbender 03": return R.drawable.tv_medium_avatar_the_last_airbender_03;
            case "The Big Bang Theory 01": return R.drawable.tv_medium_the_big_bang_theory_01;
            case "The Big Bang Theory 02": return R.drawable.tv_medium_the_big_bang_theory_02;
            case "The Big Bang Theory 03": return R.drawable.tv_medium_the_big_bang_theory_03;
            case "The Big Bang Theory 04": return R.drawable.tv_medium_the_big_bang_theory_04;
            case "The Big Bang Theory 05": return R.drawable.tv_medium_the_big_bang_theory_01;
            case "The Big Bang Theory 06": return R.drawable.tv_medium_the_big_bang_theory_01;
            case "The Big Bang Theory 07": return R.drawable.tv_medium_the_big_bang_theory_01;
            case "The Big Bang Theory 08": return R.drawable.tv_medium_the_big_bang_theory_08;
            case "Duck Dynasty 01": return R.drawable.tv_medium_duck_dynasty_01;
            case "Duck Dynasty 02": return R.drawable.tv_medium_duck_dynasty_02;
            case "Duck Dynasty 03": return R.drawable.tv_medium_duck_dynasty_03;
            case "Friday Night Lights 01": return R.drawable.tv_medium_friday_night_lights_01;
            case "Friday Night Lights 02": return R.drawable.tv_medium_friday_night_lights_02;
            case "Friday Night Lights 03": return R.drawable.tv_medium_friday_night_lights_03;
            case "Friday Night Lights 04": return R.drawable.tv_medium_friday_night_lights;
            case "Friday Night Lights 05": return R.drawable.tv_medium_friday_night_lights_05;
            case "Full House 01": return R.drawable.tv_medium_full_house;
            case "Full House 02": return R.drawable.tv_medium_full_house;
            case "Full House 03": return R.drawable.tv_medium_full_house;
            case "Full House 04": return R.drawable.tv_medium_full_house;
            case "Full House 05": return R.drawable.tv_medium_full_house;
            case "Full House 06": return R.drawable.tv_medium_full_house;
            case "Full House 07": return R.drawable.tv_medium_full_house;
            case "Full House 08": return R.drawable.tv_medium_full_house;
            case "NCIS 01": return R.drawable.tv_medium_ncis_01;
            case "NCIS 02": return R.drawable.tv_medium_ncis_02;
            case "NCIS 03": return R.drawable.tv_medium_ncis_03;
            case "NCIS 04": return R.drawable.tv_medium_ncis_04;
            case "NCIS 05": return R.drawable.tv_medium_ncis_05;
            case "NCIS 06": return R.drawable.tv_medium_ncis_06;
            case "NCIS 07": return R.drawable.tv_medium_ncis_07;
            case "NCIS 08": return R.drawable.tv_medium_ncis_08;
            case "NCIS 09": return R.drawable.tv_medium_ncis_09;
            case "NCIS 10": return R.drawable.tv_medium_ncis_10;
            case "Reba 01": return R.drawable.tv_medium_reba_01;
            case "Reba 02": return R.drawable.tv_medium_reba_02;
            case "Reba 03": return R.drawable.tv_medium_reba_03;
            case "Reba 04": return R.drawable.tv_medium_reba_04;
            case "Reba 05": return R.drawable.tv_medium_reba_05;
            case "Reba 06": return R.drawable.tv_medium_reba_06;
            case "White Collar 01": return R.drawable.tv_medium_white_collar_01;
            case "White Collar 02": return R.drawable.tv_medium_white_collar;
            case "White Collar 03": return R.drawable.tv_medium_white_collar;
            case "White Collar 04": return R.drawable.tv_medium_white_collar;
            case "White Collar 05": return R.drawable.tv_medium_white_collar;
            case "White Collar 06": return R.drawable.tv_medium_white_collar;
            default: return -1;
        }
    }

    private int getEpisodeResource(String title) {
        switch (title) {
            case "Avatar: The Last Airbender 01": return R.raw.avatar_the_last_airbender_s01;
            case "Avatar: The Last Airbender 02": return R.raw.avatar_the_last_airbender_s02;
            case "Avatar: The Last Airbender 03": return R.raw.avatar_the_last_airbender_s03;
            case "The Big Bang Theory 01": return R.raw.the_big_bang_theory_s01;
            case "The Big Bang Theory 02": return R.raw.the_big_bang_theory_s02;
            case "The Big Bang Theory 03": return R.raw.the_big_bang_theory_s03;
            case "The Big Bang Theory 04": return R.raw.the_big_bang_theory_s04;
            case "The Big Bang Theory 05": return R.raw.the_big_bang_theory_s05;
            case "The Big Bang Theory 06": return R.raw.the_big_bang_theory_s06;
            case "The Big Bang Theory 07": return R.raw.the_big_bang_theory_s07;
            case "The Big Bang Theory 08": return R.raw.the_big_bang_theory_s08;
            case "Duck Dynasty 01": return R.raw.duck_dynasty_s01;
            case "Duck Dynasty 02": return R.raw.duck_dynasty_s02;
            case "Duck Dynasty 03": return R.raw.duck_dynasty_s03;
            case "Friday Night Lights 01": return R.raw.friday_night_lights_s01;
            case "Friday Night Lights 02": return R.raw.friday_night_lights_s02;
            case "Friday Night Lights 03": return R.raw.friday_night_lights_s03;
            case "Friday Night Lights 04": return R.raw.friday_night_lights_s04;
            case "Friday Night Lights 05": return R.raw.friday_night_lights_s05;
            case "Full House 01": return R.raw.full_house_s01;
            case "Full House 02": return R.raw.full_house_s02;
            case "Full House 03": return R.raw.full_house_s03;
            case "Full House 04": return R.raw.full_house_s04;
            case "Full House 05": return R.raw.full_house_s05;
            case "Full House 06": return R.raw.full_house_s06;
            case "Full House 07": return R.raw.full_house_s07;
            case "Full House 08": return R.raw.full_house_s08;
            case "NCIS 01": return R.raw.ncis_s01;
            case "NCIS 02": return R.raw.ncis_s02;
            case "NCIS 03": return R.raw.ncis_s03;
            case "NCIS 04": return R.raw.ncis_s04;
            case "NCIS 05": return R.raw.ncis_s05;
            case "NCIS 06": return R.raw.ncis_s06;
            case "NCIS 07": return R.raw.ncis_s07;
            case "NCIS 08": return R.raw.ncis_s08;
            case "NCIS 09": return R.raw.ncis_s09;
            case "NCIS 10": return R.raw.ncis_s10;
            case "Reba 01": return R.raw.reba_s01;
            case "Reba 02": return R.raw.reba_s02;
            case "Reba 03": return R.raw.reba_s03;
            case "Reba 04": return R.raw.reba_s04;
            case "Reba 05": return R.raw.reba_s05;
            case "Reba 06": return R.raw.reba_s06;
            case "White Collar 01": return R.raw.white_collar_s01;
            case "White Collar 02": return R.raw.white_collar_s02;
            case "White Collar 03": return R.raw.white_collar_s03;
            case "White Collar 04": return R.raw.white_collar_s04;
            case "White Collar 05": return R.raw.white_collar_s05;
            case "White Collar 06": return R.raw.white_collar_s06;
            default: return -1;
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
