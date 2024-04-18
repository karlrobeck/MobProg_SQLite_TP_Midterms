package com.example.mobprogsqlitetpmidterms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlexboxLayout contents;
    Button addMovieBtn;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHandler = new DbHandler(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contents = (FlexboxLayout) findViewById(R.id.contents);
        addMovieBtn = (Button) findViewById(R.id.addMovieActivityBtn);
        // event listeners
        addMovieBtn.setOnClickListener((View view) -> switchToAddMovieActivity(view));
        List <DbHandler.ShowTbl> movies = dbHandler.showMovies();
        for(DbHandler.ShowTbl movie : movies) {
            System.out.println("Movie: " + movie.movieName);
            this.contents.addView(movieCardComponent(movie));
        }
    }

    // helper functions
    public int pdToPx(int size){
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }

    // critical functions
    public void switchToAddMovieActivity(View view) {
        startActivity(new Intent(MainActivity.this,AddMovie.class));
    }

    public MaterialCardView movieCardComponent(DbHandler.ShowTbl movie) {
        // card properties
        MaterialCardView card = new MaterialCardView(MainActivity.this);
        card.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        FlexboxLayout content = new FlexboxLayout(MainActivity.this);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setPadding(pdToPx(20),pdToPx(20),pdToPx(20),pdToPx(20));
        // title
        TextView title = new TextView(MainActivity.this);
        title.setText(movie.movieName);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextSize(pdToPx(16));
        FlexboxLayout subHeading = new FlexboxLayout(MainActivity.this);
        subHeading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        subHeading.setFlexDirection(FlexDirection.COLUMN);
        //director component
        TextView director = new TextView(MainActivity.this);
        director.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        director.setText(movie.movieDirector);

        //release date component
        TextView releaseDate = new TextView(MainActivity.this);
        releaseDate.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        releaseDate.setText(movie.releaseDate);

        subHeading.addView(director);
        subHeading.addView(releaseDate);

        // description
        TextView description = new TextView(MainActivity.this);
        description.setText(movie.movieDescription);
        description.setPadding(0,pdToPx(12),0,pdToPx(12));

        //action buttons
        ExtendedFloatingActionButton goBtn = new ExtendedFloatingActionButton(MainActivity.this);
        goBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        goBtn.setText("View Movie");
        goBtn.setOnClickListener((View view) -> {
            Intent intent = new Intent(MainActivity.this,MovieActivity.class);
            intent.putExtra("name",movie.movieName);
            intent.putExtra("description",movie.movieDescription);
            intent.putExtra("director",movie.movieDirector);
            intent.putExtra("releaseDate",movie.releaseDate);
            startActivity(intent);
        });

        // combine all
        content.addView(title);
        content.addView(subHeading);
        content.addView(description);
        content.addView(goBtn);

        // add all to card
        card.addView(content);

        return card;
    }
}