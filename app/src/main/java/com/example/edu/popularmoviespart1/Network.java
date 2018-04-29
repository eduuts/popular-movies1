package com.example.edu.popularmoviespart1;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Network{

    final static String MOVIES_POPULAR_URL = "https://api.themoviedb.org";
    final static String API_KEY = ""; //insert api key here

    final static String PARAM_API_KEY = "api_key";
    //this function simply builds the url from which we request the movie dbm depending on the
    //user's sort criteria
    public static URL buildUrl(String sortCriteria) {
        Uri builtUri = Uri.parse(MOVIES_POPULAR_URL).buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(sortCriteria)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    //this function is used to get the response from the url built with the prior function.
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        //first,open the Http Connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            //using inputstream, we check if the scanner received any data from the db using the
            //hasInput boolean
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            //using the \\A delimiter forces the scanner to take the whole input once, instead of
            // breaking it into smaller parts. In this way, we will be sure the scanner will contain
            //the json db.
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                //in the case in which the scanner is not empty, we return the movie database
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
