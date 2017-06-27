package com.example.dawson.mountgoodcontrol;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MovieInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieinfo);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        TextView textTitle = (TextView) findViewById(R.id.movieInfoTitle);
        textTitle.setText(title);
        setDescription(title);
        setImage(title);
    }

    private void setDescription(String title) {
        String description = "Description for " + title;

        InputStream inputStream = this.getResources().openRawResource(R.raw.description);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(title)) {
                    description = line;
                }
            }
            if (!description.equals("Description for " + title)) {
                int descIndex = description.indexOf(" - ") + 3;
                description = description.substring(descIndex);
            }
        }
        catch (IOException e) {
            description = "Error getting description from file";
        }

        TextView textDesc = (TextView) findViewById(R.id.movieInfoDescription);
        textDesc.setText(description);
    }

    private void setImage(String title) {
        ImageView imageTitle = (ImageView) findViewById(R.id.movieInfoImage);

        if (title.equals("Aladdin")) {
            imageTitle.setImageResource(R.drawable.aladdin_336x480);
        }
        else {
            imageTitle.setImageResource(R.drawable.batman_begins_336x480);
        }
    }

    public void playMovie(View v) {
        TextView textTitle = (TextView) findViewById(R.id.movieInfoTitle);
        String title = (String) textTitle.getText();
        byte[] data = new byte[title.length()+3];

        data[0] = (byte) 57;
        data[1] = (byte) 57;

        for (int i = 0; i < title.length(); i++) {
            data[i+2] = (byte) title.charAt(i);
        }

        data[data.length - 1] = (byte) '\n';

        MainActivity.ConnectedThread.write(data);
    }
}
