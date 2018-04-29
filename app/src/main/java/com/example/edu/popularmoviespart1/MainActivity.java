package com.example.edu.popularmoviespart1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private String sortCriteria = "top_rated";
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        //Using the GridLayoutManager with the recyclerView will help us position the views
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        movieList = new ArrayList<>();
        //Calling this function will attempt to access the movie database, retrieve the movies
        // and their info and populate the views

        if(isNetworkAvailable())getMovieDatabase(sortCriteria);
            else Toast.makeText(getApplicationContext(),
                "No internet connection. Please restart the application",
                Toast.LENGTH_LONG).show();
    }

    //overriding this function will give us the possibility of creating our menu for selecting the
    //criteria for sorting the movies
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //this simple override reacts on an user click on one of the two options. For example, if the
    //user wants to see the top rated movies, while these movies are already shown, nothing will
    //happen. In the other case, the sort criteria will be changed, and the getMovieDatabase
    //will be called again
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.popular:

                if (sortCriteria == "popular") {
                    return true;
                } else {
                    movieList.clear();
                    sortCriteria = "popular";
                    getMovieDatabase(sortCriteria);
                    return true;
                }

            case R.id.rating:


                if (sortCriteria == "top_rated") {
                    return true;
                } else {
                    movieList.clear();
                    sortCriteria = "top_rated";
                    getMovieDatabase(sortCriteria);
                    return true;
                }

        }
        return super.onOptionsItemSelected(item);
    }

    //when the user clicks one of the movies, the movie details from the movieList will be passed
    //to the new intent using the putExtra().
    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(this, DetailClass.class);

        intent.putExtra("Poster", movieList.get(clickedItemIndex).getImage());
        intent.putExtra("Title", movieList.get(clickedItemIndex).getTitle());
        intent.putExtra("Synopsis", movieList.get(clickedItemIndex).getDescription());
        intent.putExtra("UserRating", movieList.get(clickedItemIndex).getRating());
        intent.putExtra("ReleaseDate", movieList.get(clickedItemIndex).getDate());

        startActivity(intent);

    }

    private void getMovieDatabase(String sortCriteria) {
            //we first build the url from which we will get the database, depending on the
            //types of movies the user wants to see
            URL movieDatabaseSearchUrl = Network.buildUrl(sortCriteria);
            //afterwards, calling the FetchMoviesAsyncTask(), which will process the database,
            //saving the movies in our movieList
            new FetchMoviesAsyncTask().execute(movieDatabaseSearchUrl);

    }


    private class FetchMoviesAsyncTask extends AsyncTask<URL, Void, String> {

        // in background, we attempt on getting the database in the form of a String
        // and return it for later use in PostExecute
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String s = null;
            try {
                s = Network.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.contains("{") || s != null) {// we check if the string is not empty

                try {// if it is not empty, we save it in a JSONObject, from which we save the
                    // results in an array, in which we have stored the movies
                    JSONObject moviesJSON = new JSONObject(s);
                    JSONArray movieArray = moviesJSON.getJSONArray("results");

                    for (int i = 0; i < movieArray.length(); i++) {
                        //we then go through the array, adding each movie to our movieList
                        JSONObject movieJson = movieArray.getJSONObject(i);

                        movieList.add(new Movie(movieJson.getString("poster_path"),
                                                movieJson.getString("title"),
                                                movieJson.getString("overview"),
                                                movieJson.getString("vote_average"),
                                                movieJson.getString("release_date")
                                    ));
                    }
                    //then adding the movieList to our adapter
                    adapter = new MovieAdapter(movieList, getApplicationContext(), MainActivity.this);
                    mRecyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}




