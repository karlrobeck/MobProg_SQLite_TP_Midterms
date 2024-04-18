package com.example.mobprogsqlitetpmidterms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
public class AddMovie extends AppCompatActivity {

    Button submitBtn;
    Button cancelBtn;
    TextInputEditText movieNameField;
    TextInputEditText movieDescriptionField;
    TextInputEditText movieDirectorField;
    TextInputEditText movieReleaseDateField;

    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHandler = new DbHandler(AddMovie.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        //get the views
        this.submitBtn = (Button) findViewById(R.id.submitBtn);
        this.cancelBtn = (Button) findViewById(R.id.cancelBtn);

        // form fields
        this.movieNameField = (TextInputEditText) findViewById(R.id.movieName);
        this.movieDescriptionField = (TextInputEditText) findViewById(R.id.movieDescription);
        this.movieDirectorField = (TextInputEditText) findViewById(R.id.movieDirector);
        this.movieReleaseDateField = (TextInputEditText) findViewById(R.id.movieReleaseDate);

        //set listeners
        submitBtn.setOnClickListener((View view) -> this.createMovie(view));
        cancelBtn.setOnClickListener((View view) -> this.goBackToMain(view));
    }

    public void goBackToMain(View view){
        finish(); // go back to main activity
    }
    public void createMovie(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddMovie.this);
        String name = this.movieNameField.getText().toString();
        String description = this.movieDescriptionField.getText().toString();
        String director = this.movieDirectorField.getText().toString();
        String releaseDate = this.movieReleaseDateField.getText().toString();
        alert.setMessage(
                "Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Director: " + director + "\n" +
                "Release Date: " + releaseDate + "\n"
        );

        alert.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            dbHandler.addMovie(name, description, director, releaseDate);
            finish();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        alert.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        alert.setTitle("Confirm Submission");
        alert.show();
    }
}