package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MoviesActivity extends AppCompatActivity implements View.OnTouchListener {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        populateScrollView();

        gestureDetector = new GestureDetector(this,new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < MainActivity.SWIPE_THRESHOLD_VELOCITY) { return true; }
                if (direction == Direction.right) { startMusic(null); }
                return true;
            }
        });
        findViewById(R.id.activity_movies).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void populateScrollView() {
        InputStream inputStream = this.getResources().openRawResource(R.raw.titles);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        LinearLayout layout = (LinearLayout) findViewById(R.id.movieLayout);

        try {
            boolean isDone = false;
            boolean isEmpty;
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

                    int height;
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
                    temp.setCompoundDrawablesWithIntrinsicBounds(0, getImage(title), 0, 0);

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
        switch (title) {
            case "Aladdin": return R.drawable.small_aladdin;
            case "Angels in the Outfield": return R.drawable.small_angels_in_the_outfield;
            case "The Avengers": return R.drawable.small_avengers;
            case "Batman Begins": return R.drawable.small_batman_begins;
            case "The Blind Side": return R.drawable.small_the_blind_side;
            case "The Bourne Identity": return R.drawable.small_the_bourne_identity;
            case "The Bourne Supremacy": return R.drawable.small_the_bourne_supremacy;
            case "The Bourne Ultimatum": return R.drawable.small_the_bourne_ultimatum;
            case "A Bug's Life": return R.drawable.small_a_bugs_life;
            case "Butch Cassidy and the Sundance Kid": return R.drawable.small_butch_cassidy_and_the_sundance_kid;
            case "Cars": return R.drawable.small_cars;
            case "The Dark Knight": return R.drawable.small_the_dark_knight;
            case "The Dark Knight Rises": return R.drawable.small_the_dark_knight_rises;
            case "The Fast and the Furious": return R.drawable.small_fast_and_furious_01;
            case "2 Fast 2 Furious": return R.drawable.small_fast_and_furious_02;
            case "The Fast and the Furious: Tokyo Drift": return R.drawable.small_fast_and_furious_03;
            case "Fast & Furious": return R.drawable.small_fast_and_furious_04;
            case "Fast Five": return R.drawable.small_fast_and_furious_05;
            case "Fast & Furious 6": return R.drawable.small_fast_and_furious_06;
            case "Furious 7": return R.drawable.small_fast_and_furious_07;
            case "Finding Nemo": return R.drawable.small_finding_nemo;
            case "Frozen": return R.drawable.small_frozen;
            case "Harry Potter and the Sorcerer's Stone": return R.drawable.small_harry_potter_01;
            case "Harry Potter and the Chamber of Secrets": return R.drawable.small_harry_potter_02;
            case "Harry Potter and the Prisoner of Azkaban": return R.drawable.small_harry_potter_03;
            case "Harry Potter and the Goblet of Fire": return R.drawable.small_harry_potter_04;
            case "Harry Potter and the Order of the Phoenix": return R.drawable.small_harry_potter_05;
            case "Harry Potter and the Half-Blood Prince": return R.drawable.small_harry_potter_06;
            case "Harry Potter and the Deathly Hallows part 1": return R.drawable.small_harry_potter_0701;
            case "Harry Potter and the Deathly Hallows part 2": return R.drawable.small_harry_potter_0702;
            case "Home Alone": return R.drawable.small_home_alone;
            case "The Hunger Games": return R.drawable.small_the_hunger_games_01;
            case "The Hunger Games: Catching Fire": return R.drawable.small_the_hunger_games_02;
            case "The Hunger Games: Mockingjay part 1": return R.drawable.small_the_hunger_games_0301;
            case "The Hunger Games: Mockingjay part 2": return R.drawable.small_the_hunger_games_0302;
            case "The Incredibles": return R.drawable.small_the_incredibles;
            case "Indiana Jones and the Raiders of the Lost Ark": return R.drawable.small_indiana_jones_and_the_raiders_of_the_lost_ark;
            case "Indiana Jones and the Temple of Doom": return R.drawable.small_indiana_jones_and_the_temple_of_doom;
            case "Indiana Jones and the Last Crusade": return R.drawable.small_indiana_jones_and_the_last_crusade;
            case "Iron Man": return R.drawable.small_iron_man;
            case "Iron Man 2": return R.drawable.small_iron_man_2;
            case "It's a Wonderful Life": return R.drawable.small_its_a_wonderful_life;
            case "Jurassic Park": return R.drawable.small_jurassic_park;
            case "Jurassic Park 2": return R.drawable.small_jurassic_park_2;
            case "Jurassic Park 3": return R.drawable.small_jurassic_park_3;
            case "The Lion King": return R.drawable.small_the_lion_king;
            case "The Lord of the Rings: The Fellowship of the Ring": return R.drawable.small_lotr_01;
            case "The Lord of the Rings: The Two Towers": return R.drawable.small_lotr_02;
            case "The Lord of the Rings: The Return of the King": return R.drawable.small_lotr_03;
            case "The Mighty Ducks": return R.drawable.small_the_mighty_ducks;
            case "The Mighty Ducks 2": return R.drawable.small_the_mighty_ducks_2;
            case "The Mighty Ducks 3": return R.drawable.small_the_mighty_ducks_3;
            case "Miracle on 34th Street": return R.drawable.small_miracle_on_34th_street;
            case "Monsters, Inc.": return R.drawable.small_monsters_inc;
            case "Pirates of the Caribbean: Curse of the Black Pearl": return R.drawable.small_pirates_of_the_caribbean_curse_of_the_black_pearl;
            case "Remember the Titans": return R.drawable.small_remember_the_titans;
            case "The Santa Clause": return R.drawable.small_the_santa_clause;
            case "Star Wars: The Phantom Menace": return R.drawable.small_star_wars_01;
            case "Star Wars: Attack of the Clones": return R.drawable.small_star_wars_02;
            case "Star Wars: Revenge of the Sith": return R.drawable.small_star_wars_03;
            case "Star Wars: A New Hope": return R.drawable.small_star_wars_04;
            case "Star Wars: The Empire Strikes Back": return R.drawable.small_star_wars_05;
            case "Star Wars: Return of the Jedi": return R.drawable.small_star_wars_06;
            case "Toy Story": return R.drawable.small_toy_story;
            case "Toy Story 2": return R.drawable.small_toy_story_2;
            case "Transformers": return R.drawable.small_transformers;
            case "White Christmas": return R.drawable.small_white_christmas;
            case "X-Men": return R.drawable.small_x_men;
            case "X-Men 2": return R.drawable.small_x_men_2;
            case "X-Men 3": return R.drawable.small_x_men_3;
            case "X-Men Origins: Wolverine": return R.drawable.small_x_men_origins_wolverine;
            case "X-Men: First Class": return R.drawable.small_x_men_first_class;
            case "X-Men: Days of Future Past": return R.drawable.small_x_men_days_of_future_past;
            case "X-Men: Apocalypse": return R.drawable.small_x_men_apocalypse;
            default: return R.drawable.small_no_image;
        }
    }

    private void displayMovieInfo(View v) {
        String title = ((TextView) findViewById(v.getId())).getText().toString();
        Intent passIntent = new Intent(this, MovieInfoActivity.class);
        passIntent.putExtra("title", title);
        startActivity(passIntent);
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); }
}
