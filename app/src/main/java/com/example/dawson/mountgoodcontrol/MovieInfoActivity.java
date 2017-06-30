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

        ((TextView) findViewById(R.id.movieInfoTitle)).setText(title);
        ((TextView) findViewById(R.id.movieInfoDescription)).setText(getDescription(title));
        ((ImageView) findViewById(R.id.movieInfoImage)).setImageResource(getImage(title));
    }

    private String getDescription(String title) {
        String description = "Description for " + title;

        InputStream inputStream = this.getResources().openRawResource(R.raw.descriptions);
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

        return description;
    }

    private int getImage(String title) {
        if (title.equals("Aladdin")) { return R.drawable.medium_aladdin; }
        else if (title.equals("Angels in the Outfield")) { return R.drawable.medium_angels_in_the_outfield; }
        else if (title.equals("The Avengers")) { return R.drawable.medium_avengers; }
        else if (title.equals("Batman Begins")) { return R.drawable.medium_batman_begins; }
        else if (title.equals("The Blind Side")) { return R.drawable.medium_the_blind_side; }
        else if (title.equals("The Bourne Identity")) { return R.drawable.medium_the_bourne_identity; }
        else if (title.equals("The Bourne Supremacy")) { return R.drawable.medium_the_bourne_supremacy; }
        else if (title.equals("The Bourne Ultimatum")) { return R.drawable.medium_the_bourne_ultimatum; }
        else if (title.equals("A Bug's Life")) { return R.drawable.medium_a_bugs_life; }
        else if (title.equals("Butch Cassidy and the Sundance Kid")) { return R.drawable.medium_butch_cassidy_and_the_sundance_kid; }
        else if (title.equals("Cars")) { return R.drawable.medium_cars; }
        else if (title.equals("The Dark Knight")) { return R.drawable.medium_the_dark_knight; }
        else if (title.equals("The Dark Knight Rises")) { return R.drawable.medium_the_dark_knight_rises; }
        else if (title.equals("The Fast and the Furious")) { return R.drawable.medium_fast_and_furious_01; }
        else if (title.equals("2 Fast 2 Furious")) { return R.drawable.medium_fast_and_furious_02; }
        else if (title.equals("The Fast and the Furious: Tokyo Drift")) { return R.drawable.medium_fast_and_furious_03; }
        else if (title.equals("Fast & Furious")) { return R.drawable.medium_fast_and_furious_04; }
        else if (title.equals("Fast Five")) { return R.drawable.medium_fast_and_furious_05; }
        else if (title.equals("Fast & Furious 6")) { return R.drawable.medium_fast_and_furious_06; }
        else if (title.equals("Furious 7")) { return R.drawable.medium_fast_and_furious_07; }
        else if (title.equals("Finding Nemo")) { return R.drawable.medium_finding_nemo; }
        else if (title.equals("Frozen")) { return R.drawable.medium_frozen; }
        else if (title.equals("Harry Potter and the Sorcerer's Stone")) { return R.drawable.medium_harry_potter_01; }
        else if (title.equals("Harry Potter and the Chamber of Secrets")) { return R.drawable.medium_harry_potter_02; }
        else if (title.equals("Harry Potter and the Prisoner of Azkaban")) { return R.drawable.medium_harry_potter_03; }
        else if (title.equals("Harry Potter and the Goblet of Fire")) { return R.drawable.medium_harry_potter_04; }
        else if (title.equals("Harry Potter and the Order of the Phoenix")) { return R.drawable.medium_harry_potter_05; }
        else if (title.equals("Harry Potter and the Half-Blood Prince")) { return R.drawable.medium_harry_potter_06; }
        else if (title.equals("Harry Potter and the Deathly Hallows part 1")) { return R.drawable.medium_harry_potter_0701; }
        else if (title.equals("Harry Potter and the Deathly Hallows part 2")) { return R.drawable.medium_harry_potter_0702; }
        else if (title.equals("Home Alone")) { return R.drawable.medium_home_alone; }
        else if (title.equals("The Hunger Games")) { return R.drawable.medium_the_hunger_games_01; }
        else if (title.equals("The Hunger Games: Catching Fire")) { return R.drawable.medium_the_hunger_games_02; }
        else if (title.equals("The Hunger Games: Mockingjay part 1")) { return R.drawable.medium_the_hunger_games_0301; }
        else if (title.equals("The Hunger Games: Mockingjay part 2")) { return R.drawable.medium_the_hunger_games_0302; }
        else if (title.equals("The Incredibles")) { return R.drawable.medium_the_incredibles; }
        else if (title.equals("Indiana Jones and the Raiders of the Lost Ark")) { return R.drawable.medium_indiana_jones_and_the_raiders_of_the_lost_ark; }
        else if (title.equals("Indiana Jones and the Temple of Doom")) { return R.drawable.medium_indiana_jones_and_the_temple_of_doom; }
        else if (title.equals("Indiana Jones and the Last Crusade")) { return R.drawable.medium_indiana_jones_and_the_last_crusade; }
        else if (title.equals("Iron Man")) { return R.drawable.medium_iron_man; }
        else if (title.equals("Iron Man 2")) { return R.drawable.medium_iron_man_2; }
        else if (title.equals("It's a Wonderful Life")) { return R.drawable.medium_its_a_wonderful_life; }
        else if (title.equals("Jurassic Park")) { return R.drawable.medium_jurassic_park; }
        else if (title.equals("Jurassic Park 2")) { return R.drawable.medium_jurassic_park_2; }
        else if (title.equals("Jurassic Park 3")) { return R.drawable.medium_jurassic_park_3; }
        else if (title.equals("The Lion King")) { return R.drawable.medium_the_lion_king; }
        else if (title.equals("The Lord of the Rings: The Fellowship of the Ring")) { return R.drawable.medium_lotr_01; }
        else if (title.equals("The Lord of the Rings: The Two Towers")) { return R.drawable.medium_lotr_02; }
        else if (title.equals("The Lord of the Rings: The Return of the King")) { return R.drawable.medium_lotr_03; }
        else if (title.equals("The Mighty Ducks")) { return R.drawable.medium_the_mighty_ducks; }
        else if (title.equals("The Mighty Ducks 2")) { return R.drawable.medium_the_mighty_ducks_2; }
        else if (title.equals("The Mighty Ducks 3")) { return R.drawable.medium_the_mighty_ducks_3; }
        else if (title.equals("Miracle on 34th Street")) { return R.drawable.medium_miracle_on_34th_street; }
        else if (title.equals("Monsters, Inc.")) { return R.drawable.medium_monsters_inc; }
        else if (title.equals("Pirates of the Caribbean: Curse of the Black Pearl")) { return R.drawable.medium_pirates_of_the_caribbean_curse_of_the_black_pearl; }
        else if (title.equals("Remember the Titans")) { return R.drawable.medium_remember_the_titans; }
        else if (title.equals("The Santa Clause")) { return R.drawable.medium_the_santa_clause; }
        else if (title.equals("Star Wars: The Phantom Menace")) { return R.drawable.medium_star_wars_01; }
        else if (title.equals("Star Wars: Attack of the Clones")) { return R.drawable.medium_star_wars_02; }
        else if (title.equals("Star Wars: Revenge of the Sith")) { return R.drawable.medium_star_wars_03; }
        else if (title.equals("Star Wars: A New Hope")) { return R.drawable.medium_star_wars_04; }
        else if (title.equals("Star Wars: The Empire Strikes Back")) { return R.drawable.medium_star_wars_05; }
        else if (title.equals("Star Wars: Return of the Jedi")) { return R.drawable.medium_star_wars_06; }
        else if (title.equals("Toy Story")) { return R.drawable.medium_toy_story; }
        else if (title.equals("Toy Story 2")) { return R.drawable.medium_toy_story_2; }
        else if (title.equals("Transformers")) { return R.drawable.medium_transformers; }
        else if (title.equals("White Christmas")) { return R.drawable.medium_white_christmas; }
        else if (title.equals("X-Men")) { return R.drawable.medium_x_men; }
        else if (title.equals("X-Men 2")) { return R.drawable.medium_x_men_2; }
        else if (title.equals("X-Men 3")) { return R.drawable.medium_x_men_3; }
        else if (title.equals("X-Men Origins: Wolverine")) { return R.drawable.medium_x_men_origins_wolverine; }
        else if (title.equals("X-Men: First Class")) { return R.drawable.medium_x_men_first_class; }
        else if (title.equals("X-Men: Days of Future Past")) { return R.drawable.medium_x_men_days_of_future_past; }
        else if (title.equals("X-Men: Apocalypse")) { return R.drawable.medium_x_men_apocalypse; }
        else { return R.drawable.medium_no_image; }
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
