package com.example.dawson.mountgoodcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MoviesActivity extends AppCompatActivity implements View.OnTouchListener {
    private GestureDetector gestureDetector;
    private int numMovies;
    private HashMap<Integer, String> titleIds = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        numMovies = displayMetrics.widthPixels/displayMetrics.densityDpi;
        populateScrollView();

        gestureDetector = new GestureDetector(this,new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction, float velocityX, float velocityY) {
                if (Math.abs(velocityX) < MainActivity.SWIPE_THRESHOLD_VELOCITY) { return true; }
                if (direction == Direction.right) { startMusic(null); }
                else if (direction == Direction.left) { startShows(null); }
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
                for(int i = 0; i < numMovies; i++) {
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
                            displayMovieInfo(v);
                        }
                    });
                    tempImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            displayMovieInfo(v);
                        }
                    });

                    linLayImage.addView(tempImage);
                    linLayText.addView(tempText);
                }

                if (isEmpty) { break; }
                else {
                    if (linLayImage.getChildCount() < numMovies) {
                        View fillerImage = new View(this);
                        View fillerText = new View(this);
                        float gravity = numMovies - linLayImage.getChildCount();
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
            case "Aladdin": return R.drawable.small_aladdin;
            case "Angels in the Outfield": return R.drawable.small_angels_in_the_outfield;
            case "The Avengers": return R.drawable.small_avengers;
            case "Batman Begins": return R.drawable.small_batman_begins;
            case "The Blind Side": return R.drawable.small_the_blind_side;
            case "The Bourne Identity": return R.drawable.small_the_bourne_identity;
            case "The Bourne Supremacy": return R.drawable.small_the_bourne_supremacy;
            case "The Bourne Ultimatum": return R.drawable.small_the_bourne_ultimatum;
            case "Braveheart": return R.drawable.small_braveheart;
            case "A Bug's Life": return R.drawable.small_a_bugs_life;
            case "Butch Cassidy and the Sundance Kid": return R.drawable.small_butch_cassidy_and_the_sundance_kid;
            case "Cars": return R.drawable.small_cars;
            case "The Chronicles of Narnia: The Lion, the Witch and the Wardrobe": return R.drawable.small_the_chronicles_of_narnia_the_lion_the_witch_and_the_wardrobe;
            case "The Dark Knight": return R.drawable.small_the_dark_knight;
            case "The Dark Knight Rises": return R.drawable.small_the_dark_knight_rises;
            case "Dodgeball": return R.drawable.small_dodgeball;
            case "The Fast and the Furious": return R.drawable.small_fast_and_furious_01;
            case "2 Fast 2 Furious": return R.drawable.small_fast_and_furious_02;
            case "The Fast and the Furious: Tokyo Drift": return R.drawable.small_fast_and_furious_03;
            case "Fast & Furious": return R.drawable.small_fast_and_furious_04;
            case "Fast Five": return R.drawable.small_fast_and_furious_05;
            case "Fast & Furious 6": return R.drawable.small_fast_and_furious_06;
            case "Furious 7": return R.drawable.small_fast_and_furious_07;
            case "Finding Nemo": return R.drawable.small_finding_nemo;
            case "Friday Night Lights": return R.drawable.small_friday_night_lights;
            case "Frozen": return R.drawable.small_frozen;
            case "Harry Potter and the Sorcerer's Stone": return R.drawable.small_harry_potter_01;
            case "Harry Potter and the Chamber of Secrets": return R.drawable.small_harry_potter_02;
            case "Harry Potter and the Prisoner of Azkaban": return R.drawable.small_harry_potter_03;
            case "Harry Potter and the Goblet of Fire": return R.drawable.small_harry_potter_04;
            case "Harry Potter and the Order of the Phoenix": return R.drawable.small_harry_potter_05;
            case "Harry Potter and the Half-Blood Prince": return R.drawable.small_harry_potter_06;
            case "Harry Potter and the Deathly Hallows part 1": return R.drawable.small_harry_potter_0701;
            case "Harry Potter and the Deathly Hallows part 2": return R.drawable.small_harry_potter_0702;
            case "The Hobbit: An Unexpected Journey": return R.drawable.small_the_hobbit_01;
            case "The Hobbit: The Desolation of Smaug": return R.drawable.small_the_hobbit_02;
            case "The Hobbit: The Battle of the Five Armies": return R.drawable.small_the_hobbit_03;
            case "Home Alone": return R.drawable.small_home_alone;
            case "The Hunger Games": return R.drawable.small_the_hunger_games_01;
            case "The Hunger Games: Catching Fire": return R.drawable.small_the_hunger_games_02;
            case "The Hunger Games: Mockingjay part 1": return R.drawable.small_the_hunger_games_0301;
            case "The Hunger Games: Mockingjay part 2": return R.drawable.small_the_hunger_games_0302;
            case "Inception": return R.drawable.small_inception;
            case "The Incredibles": return R.drawable.small_the_incredibles;
            case "Independence Day": return R.drawable.small_independence_day;
            case "Indiana Jones and the Raiders of the Lost Ark": return R.drawable.small_indiana_jones_and_the_raiders_of_the_lost_ark;
            case "Indiana Jones and the Temple of Doom": return R.drawable.small_indiana_jones_and_the_temple_of_doom;
            case "Indiana Jones and the Last Crusade": return R.drawable.small_indiana_jones_and_the_last_crusade;
            case "Iron Man": return R.drawable.small_iron_man;
            case "Iron Man 2": return R.drawable.small_iron_man_2;
            case "It's a Wonderful Life": return R.drawable.small_its_a_wonderful_life;
            case "Jurassic Park": return R.drawable.small_jurassic_park;
            case "Jurassic Park 2": return R.drawable.small_jurassic_park_2;
            case "Jurassic Park 3": return R.drawable.small_jurassic_park_3;
            case "The League of Extraordinary Gentlemen": return R.drawable.small_the_league_of_extraordinary_gentlemen;
            case "The Lion King": return R.drawable.small_the_lion_king;
            case "The Lord of the Rings: The Fellowship of the Ring": return R.drawable.small_lotr_01;
            case "The Lord of the Rings: The Two Towers": return R.drawable.small_lotr_02;
            case "The Lord of the Rings: The Return of the King": return R.drawable.small_lotr_03;
            case "The Mighty Ducks": return R.drawable.small_the_mighty_ducks;
            case "The Mighty Ducks 2": return R.drawable.small_the_mighty_ducks_2;
            case "The Mighty Ducks 3": return R.drawable.small_the_mighty_ducks_3;
            case "Miracle on 34th Street": return R.drawable.small_miracle_on_34th_street;
            case "Monsters, Inc.": return R.drawable.small_monsters_inc;
            case "The Patriot": return R.drawable.small_the_patriot;
            case "The Pink Panther": return R.drawable.small_the_pink_panther;
            case "Pirates of the Caribbean: Curse of the Black Pearl": return R.drawable.small_pirates_of_the_caribbean_curse_of_the_black_pearl;
            case "The Princess Bride": return R.drawable.small_the_princess_bride;
            case "Radio": return R.drawable.small_radio;
            case "Remember the Titans": return R.drawable.small_remember_the_titans;
            case "Rush Hour": return R.drawable.small_rush_hour;
            case "Rush Hour 2": return R.drawable.small_rush_hour_2;
            case "The Sandlot": return R.drawable.small_the_sandlot;
            case "The Santa Clause": return R.drawable.small_the_santa_clause;
            case "Space Jam": return R.drawable.small_space_jam;
            case "Star Trek": return R.drawable.small_star_trek;
            case "Star Trek: Into Darkness": return R.drawable.small_star_trek_into_darkness;
            case "Star Wars: The Phantom Menace": return R.drawable.small_star_wars_01;
            case "Star Wars: Attack of the Clones": return R.drawable.small_star_wars_02;
            case "Star Wars: Revenge of the Sith": return R.drawable.small_star_wars_03;
            case "Star Wars: A New Hope": return R.drawable.small_star_wars_04;
            case "Star Wars: The Empire Strikes Back": return R.drawable.small_star_wars_05;
            case "Star Wars: Return of the Jedi": return R.drawable.small_star_wars_06;
            case "Star Wars: The Force Awakens": return R.drawable.small_star_wars_07;
            case "Rogue One": return R.drawable.small_rogue_one;
            case "Toy Story": return R.drawable.small_toy_story;
            case "Toy Story 2": return R.drawable.small_toy_story_2;
            case "Transformers": return R.drawable.small_transformers;
            case "We Are Marshall": return R.drawable.small_we_are_marshall;
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
        String title = titleIds.get(v.getId());
        Intent passIntent = new Intent(this, MovieInfoActivity.class);
        passIntent.putExtra("title", title);
        startActivity(passIntent);
    }

    public void startLights(View v) { startActivity(new Intent(this, LightsActivity.class)); finish(); }
    public void startMusic(View v) { startActivity(new Intent(this, MusicActivity.class)); finish(); }
    public void startShows(View v) { startActivity(new Intent(this, ShowsActivity.class)); finish(); }
    public void startRemote(View v) { startActivity(new Intent(this, MovieRemoteActivity.class)); finish(); }
    public void startSettings(View v) { startActivity(new Intent(this, SettingsActivity.class)); finish(); }
}
