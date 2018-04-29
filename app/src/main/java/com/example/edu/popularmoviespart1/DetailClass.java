package com.example.edu.popularmoviespart1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailClass extends AppCompatActivity {

    private ImageView mPoster;
    private TextView mTitle;
    private TextView mSynopsis;
    private TextView mUserRating;
    private TextView mReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        //On intent creation, we bind our Views to the ones in the movie_detail layout
        mPoster = (ImageView) findViewById(R.id.image);
        mTitle = (TextView) findViewById(R.id.title);
        mSynopsis = (TextView) findViewById(R.id.synopsis);
        mUserRating = (TextView) findViewById(R.id.rating);
        mReleaseDate = (TextView) findViewById(R.id.date);

        Intent DetailActivity = getIntent();
        //Now we want to use the Strings passed from the MainActivity intent to display the
        //movie details

        Picasso.with(this).load(DetailActivity.getStringExtra("Poster")).into(mPoster);
        mTitle.setText(DetailActivity.getStringExtra("Title"));
        mSynopsis.setText(DetailActivity.getStringExtra("Synopsis"));
        mUserRating.setText(DetailActivity.getStringExtra("UserRating"));
        mReleaseDate.setText(DetailActivity.getStringExtra("ReleaseDate"));

    }
}
