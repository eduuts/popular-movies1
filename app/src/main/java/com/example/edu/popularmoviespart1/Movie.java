package com.example.edu.popularmoviespart1;

public class Movie {

    private final String imageLink;
    private final String title;
    private final String description;
    private final String rating;
    private final String date;
    //The movie class constructor
    public Movie(String mImageLink, String mTitle, String mDescription,
                 String mRating, String mDate) {

        this.imageLink = mImageLink;
        this.title = mTitle;
        this.description = mDescription;
        this.rating = mRating;
        this.date = mDate;

    }

    // The movie class getters
    public String getImage() {
        return  "http://image.tmdb.org/t/p/w342/" + imageLink;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

}