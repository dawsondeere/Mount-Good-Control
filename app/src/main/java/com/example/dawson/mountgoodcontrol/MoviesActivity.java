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
        if (title.equals("Aladdin")) { return R.drawable.small_aladdin; }
        else if (title.equals("Angels in the Outfield")) { return R.drawable.small_angels_in_the_outfield; }
        else if (title.equals("The Avengers")) { return R.drawable.small_avengers; }
        else if (title.equals("Batman Begins")) { return R.drawable.small_batman_begins; }
        else if (title.equals("The Blind Side")) { return R.drawable.small_the_blind_side; }
        else if (title.equals("The Bourne Identity")) { return R.drawable.small_the_bourne_identity; }
        else if (title.equals("The Bourne Supremacy")) { return R.drawable.small_the_bourne_supremacy; }
        else if (title.equals("The Bourne Ultimatum")) { return R.drawable.small_the_bourne_ultimatum; }
        else if (title.equals("A Bug's Life")) { return R.drawable.small_a_bugs_life; }
        else if (title.equals("Butch Cassidy and the Sundance Kid")) { return R.drawable.small_butch_cassidy_and_the_sundance_kid; }
        else if (title.equals("Cars")) { return R.drawable.small_cars; }
        else if (title.equals("The Dark Knight")) { return R.drawable.small_the_dark_knight; }
        else if (title.equals("The Dark Knight Rises")) { return R.drawable.small_the_dark_knight_rises; }
        else if (title.equals("The Fast and the Furious")) { return R.drawable.small_fast_and_furious_01; }
        else if (title.equals("2 Fast 2 Furious")) { return R.drawable.small_fast_and_furious_02; }
        else if (title.equals("The Fast and the Furious: Tokyo Drift")) { return R.drawable.small_fast_and_furious_03; }
        else if (title.equals("Fast & Furious")) { return R.drawable.small_fast_and_furious_04; }
        else if (title.equals("Fast Five")) { return R.drawable.small_fast_and_furious_05; }
        else if (title.equals("Fast & Furious 6")) { return R.drawable.small_fast_and_furious_06; }
        else if (title.equals("Furious 7")) { return R.drawable.small_fast_and_furious_07; }
        else if (title.equals("Finding Nemo")) { return R.drawable.small_finding_nemo; }
        else if (title.equals("Frozen")) { return R.drawable.small_frozen; }
        else if (title.equals("Harry Potter and the Sorcerer's Stone")) { return R.drawable.small_harry_potter_01; }
        else if (title.equals("Harry Potter and the Chamber of Secrets")) { return R.drawable.small_harry_potter_02; }
        else if (title.equals("Harry Potter and the Prisoner of Azkaban")) { return R.drawable.small_harry_potter_03; }
        else if (title.equals("Harry Potter and the Goblet of Fire")) { return R.drawable.small_harry_potter_04; }
        else if (title.equals("Harry Potter and the Order of the Phoenix")) { return R.drawable.small_harry_potter_05; }
        else if (title.equals("Harry Potter and the Half-Blood Prince")) { return R.drawable.small_harry_potter_06; }
        else if (title.equals("Harry Potter and the Deathly Hallows part 1")) { return R.drawable.small_harry_potter_0701; }
        else if (title.equals("Harry Potter and the Deathly Hallows part 2")) { return R.drawable.small_harry_potter_0702; }
        else if (title.equals("Home Alone")) { return R.drawable.small_home_alone; }
        else if (title.equals("The Hunger Games")) { return R.drawable.small_the_hunger_games_01; }
        else if (title.equals("The Hunger Games: Catching Fire")) { return R.drawable.small_the_hunger_games_02; }
        else if (title.equals("The Hunger Games: Mockingjay part 1")) { return R.drawable.small_the_hunger_games_0301; }
        else if (title.equals("The Hunger Games: Mockingjay part 2")) { return R.drawable.small_the_hunger_games_0302; }
        else if (title.equals("The Incredibles")) { return R.drawable.small_the_incredibles; }
        else if (title.equals("Indiana Jones and the Raiders of the Lost Ark")) { return R.drawable.small_indiana_jones_and_the_raiders_of_the_lost_ark; }
        else if (title.equals("Indiana Jones and the Temple of Doom")) { return R.drawable.small_indiana_jones_and_the_temple_of_doom; }
        else if (title.equals("Indiana Jones and the Last Crusade")) { return R.drawable.small_indiana_jones_and_the_last_crusade; }
        else if (title.equals("Iron Man")) { return R.drawable.small_iron_man; }
        else if (title.equals("Iron Man 2")) { return R.drawable.small_iron_man_2; }
        else if (title.equals("It's a Wonderful Life")) { return R.drawable.small_its_a_wonderful_life; }
        else if (title.equals("Jurassic Park")) { return R.drawable.small_jurassic_park; }
        else if (title.equals("Jurassic Park 2")) { return R.drawable.small_jurassic_park_2; }
        else if (title.equals("Jurassic Park 3")) { return R.drawable.small_jurassic_park_3; }
        else if (title.equals("The Lion King")) { return R.drawable.small_the_lion_king; }
        else if (title.equals("The Lord of the Rings: The Fellowship of the Ring")) { return R.drawable.small_lotr_01; }
        else if (title.equals("The Lord of the Rings: The Two Towers")) { return R.drawable.small_lotr_02; }
        else if (title.equals("The Lord of the Rings: The Return of the King")) { return R.drawable.small_lotr_03; }
        else if (title.equals("The Mighty Ducks")) { return R.drawable.small_the_mighty_ducks; }
        else if (title.equals("The Mighty Ducks 2")) { return R.drawable.small_the_mighty_ducks_2; }
        else if (title.equals("The Mighty Ducks 3")) { return R.drawable.small_the_mighty_ducks_3; }
        else if (title.equals("Miracle on 34th Street")) { return R.drawable.small_miracle_on_34th_street; }
        else if (title.equals("Monsters, Inc.")) { return R.drawable.small_monsters_inc; }
        else if (title.equals("Pirates of the Caribbean: Curse of the Black Pearl")) { return R.drawable.small_pirates_of_the_caribbean_curse_of_the_black_pearl; }
        else if (title.equals("Remember the Titans")) { return R.drawable.small_remember_the_titans; }
        else if (title.equals("The Santa Clause")) { return R.drawable.small_the_santa_clause; }
        else if (title.equals("Star Wars: The Phantom Menace")) { return R.drawable.small_star_wars_01; }
        else if (title.equals("Star Wars: Attack of the Clones")) { return R.drawable.small_star_wars_02; }
        else if (title.equals("Star Wars: Revenge of the Sith")) { return R.drawable.small_star_wars_03; }
        else if (title.equals("Star Wars: A New Hope")) { return R.drawable.small_star_wars_04; }
        else if (title.equals("Star Wars: The Empire Strikes Back")) { return R.drawable.small_star_wars_05; }
        else if (title.equals("Star Wars: Return of the Jedi")) { return R.drawable.small_star_wars_06; }
        else if (title.equals("Toy Story")) { return R.drawable.small_toy_story; }
        else if (title.equals("Toy Story 2")) { return R.drawable.small_toy_story_2; }
        else if (title.equals("Transformers")) { return R.drawable.small_transformers; }
        else if (title.equals("White Christmas")) { return R.drawable.small_white_christmas; }
        else if (title.equals("X-Men")) { return R.drawable.small_x_men; }
        else if (title.equals("X-Men 2")) { return R.drawable.small_x_men_2; }
        else if (title.equals("X-Men 3")) { return R.drawable.small_x_men_3; }
        else if (title.equals("X-Men Origins: Wolverine")) { return R.drawable.small_x_men_origins_wolverine; }
        else if (title.equals("X-Men: First Class")) { return R.drawable.small_x_men_first_class; }
        else if (title.equals("X-Men: Days of Future Past")) { return R.drawable.small_x_men_days_of_future_past; }
        else if (title.equals("X-Men: Apocalypse")) { return R.drawable.small_x_men_apocalypse; }
        else { return R.drawable.small_no_image; }
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
