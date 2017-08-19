package com.example.dawson.mountgoodcontrol;

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
        String title = getIntent().getExtras().getString("title");

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
        switch (title) {
            case "Aladdin": return R.drawable.medium_aladdin;
            case "Angels in the Outfield": return R.drawable.medium_angels_in_the_outfield;
            case "The Avengers": return R.drawable.medium_avengers;
            case "Batman Begins": return R.drawable.medium_batman_begins;
            case "The Blind Side": return R.drawable.medium_the_blind_side;
            case "The Bourne Identity": return R.drawable.medium_the_bourne_identity;
            case "The Bourne Supremacy": return R.drawable.medium_the_bourne_supremacy;
            case "The Bourne Ultimatum": return R.drawable.medium_the_bourne_ultimatum;
            case "A Bug's Life": return R.drawable.medium_a_bugs_life;
            case "Butch Cassidy and the Sundance Kid": return R.drawable.medium_butch_cassidy_and_the_sundance_kid;
            case "Cars": return R.drawable.medium_cars;
            case "The Dark Knight": return R.drawable.medium_the_dark_knight;
            case "The Dark Knight Rises": return R.drawable.medium_the_dark_knight_rises;
            case "The Fast and the Furious": return R.drawable.medium_fast_and_furious_01;
            case "2 Fast 2 Furious": return R.drawable.medium_fast_and_furious_02;
            case "The Fast and the Furious: Tokyo Drift": return R.drawable.medium_fast_and_furious_03;
            case "Fast & Furious": return R.drawable.medium_fast_and_furious_04;
            case "Fast Five": return R.drawable.medium_fast_and_furious_05;
            case "Fast & Furious 6": return R.drawable.medium_fast_and_furious_06;
            case "Furious 7": return R.drawable.medium_fast_and_furious_07;
            case "Finding Nemo": return R.drawable.medium_finding_nemo;
            case "Frozen": return R.drawable.medium_frozen;
            case "Harry Potter and the Sorcerer's Stone": return R.drawable.medium_harry_potter_01;
            case "Harry Potter and the Chamber of Secrets": return R.drawable.medium_harry_potter_02;
            case "Harry Potter and the Prisoner of Azkaban": return R.drawable.medium_harry_potter_03;
            case "Harry Potter and the Goblet of Fire": return R.drawable.medium_harry_potter_04;
            case "Harry Potter and the Order of the Phoenix": return R.drawable.medium_harry_potter_05;
            case "Harry Potter and the Half-Blood Prince": return R.drawable.medium_harry_potter_06;
            case "Harry Potter and the Deathly Hallows part 1": return R.drawable.medium_harry_potter_0701;
            case "Harry Potter and the Deathly Hallows part 2": return R.drawable.medium_harry_potter_0702;
            case "Home Alone": return R.drawable.medium_home_alone;
            case "The Hunger Games": return R.drawable.medium_the_hunger_games_01;
            case "The Hunger Games: Catching Fire": return R.drawable.medium_the_hunger_games_02;
            case "The Hunger Games: Mockingjay part 1": return R.drawable.medium_the_hunger_games_0301;
            case "The Hunger Games: Mockingjay part 2": return R.drawable.medium_the_hunger_games_0302;
            case "The Incredibles": return R.drawable.medium_the_incredibles;
            case "Indiana Jones and the Raiders of the Lost Ark": return R.drawable.medium_indiana_jones_and_the_raiders_of_the_lost_ark;
            case "Indiana Jones and the Temple of Doom": return R.drawable.medium_indiana_jones_and_the_temple_of_doom;
            case "Indiana Jones and the Last Crusade": return R.drawable.medium_indiana_jones_and_the_last_crusade;
            case "Iron Man": return R.drawable.medium_iron_man;
            case "Iron Man 2": return R.drawable.medium_iron_man_2;
            case "It's a Wonderful Life": return R.drawable.medium_its_a_wonderful_life;
            case "Jurassic Park": return R.drawable.medium_jurassic_park;
            case "Jurassic Park 2": return R.drawable.medium_jurassic_park_2;
            case "Jurassic Park 3": return R.drawable.medium_jurassic_park_3;
            case "The Lion King": return R.drawable.medium_the_lion_king;
            case "The Lord of the Rings: The Fellowship of the Ring": return R.drawable.medium_lotr_01;
            case "The Lord of the Rings: The Two Towers": return R.drawable.medium_lotr_02;
            case "The Lord of the Rings: The Return of the King": return R.drawable.medium_lotr_03;
            case "The Mighty Ducks": return R.drawable.medium_the_mighty_ducks;
            case "The Mighty Ducks 2": return R.drawable.medium_the_mighty_ducks_2;
            case "The Mighty Ducks 3": return R.drawable.medium_the_mighty_ducks_3;
            case "Miracle on 34th Street": return R.drawable.medium_miracle_on_34th_street;
            case "Monsters, Inc.": return R.drawable.medium_monsters_inc;
            case "Pirates of the Caribbean: Curse of the Black Pearl": return R.drawable.medium_pirates_of_the_caribbean_curse_of_the_black_pearl;
            case "Remember the Titans": return R.drawable.medium_remember_the_titans;
            case "The Santa Clause": return R.drawable.medium_the_santa_clause;
            case "Star Wars: The Phantom Menace": return R.drawable.medium_star_wars_01;
            case "Star Wars: Attack of the Clones": return R.drawable.medium_star_wars_02;
            case "Star Wars: Revenge of the Sith": return R.drawable.medium_star_wars_03;
            case "Star Wars: A New Hope": return R.drawable.medium_star_wars_04;
            case "Star Wars: The Empire Strikes Back": return R.drawable.medium_star_wars_05;
            case "Star Wars: Return of the Jedi": return R.drawable.medium_star_wars_06;
            case "Toy Story": return R.drawable.medium_toy_story;
            case "Toy Story 2": return R.drawable.medium_toy_story_2;
            case "Transformers": return R.drawable.medium_transformers;
            case "White Christmas": return R.drawable.medium_white_christmas;
            case "X-Men": return R.drawable.medium_x_men;
            case "X-Men 2": return R.drawable.medium_x_men_2;
            case "X-Men 3": return R.drawable.medium_x_men_3;
            case "X-Men Origins: Wolverine": return R.drawable.medium_x_men_origins_wolverine;
            case "X-Men: First Class": return R.drawable.medium_x_men_first_class;
            case "X-Men: Days of Future Past": return R.drawable.medium_x_men_days_of_future_past;
            case "X-Men: Apocalypse": return R.drawable.medium_x_men_apocalypse;
            default: return R.drawable.medium_no_image;
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
                default: str.append(Character.toLowerCase(c));
            }
        }

        return str.toString();
    }

    public void playMovie(View v) {
        String title = ((TextView) findViewById(R.id.movieInfoTitle)).getText().toString();
        MainActivity.writeMovieData("movie/" + formatTitle(title));
    }
}
