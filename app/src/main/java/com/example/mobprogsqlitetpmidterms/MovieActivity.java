package com.example.mobprogsqlitetpmidterms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MovieActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView movieDescription;
    TextView movieDirector;
    TextView movieReleaseDate;
    Button goMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);
        // initialize the components
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieDescription = (TextView) findViewById(R.id.movieDescription);
        movieDirector = (TextView) findViewById(R.id.movieDirector);
        movieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        goMainBtn = (Button) findViewById(R.id.goMainBtn);
        goMainBtn.setOnClickListener((View view) -> {
            finish();
        });
        // get the intent and place it every component template
        Intent intent = getIntent();
        movieTitle.setText(intent.getStringExtra("name"));
        movieDescription.setText(intent.getStringExtra("description"));
        movieDirector.setText(intent.getStringExtra("director"));
        movieReleaseDate.setText(intent.getStringExtra("releaseDate"));
    }
}