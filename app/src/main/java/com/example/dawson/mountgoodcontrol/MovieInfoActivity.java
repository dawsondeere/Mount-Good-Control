package com.example.dawson.mountgoodcontrol;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MovieInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieinfo);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        TextView textTitle = (TextView) findViewById(R.id.movieInfoTitle);
        textTitle.setText(title);
        String descrtiption = getDesriptionFromTitle(title);
        TextView textDesc = (TextView) findViewById(R.id.movieInfoDescription);
        textDesc.setText(descrtiption);
    }

    private String getDesriptionFromTitle(String title) {
        String description = "Description for " + title;
        if (title.equals("Aladdin")) {

        }
        else if (title.equals("Angles in the Outfield")) {

        }
        return description;
    }

}
