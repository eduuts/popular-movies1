package com.example.edu.popularmoviespart1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesViewHolder> {

    private final List<Movie> movies;
    private final Context context;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(List<Movie> movies, Context context, ListItemClickListener listener) {
        this.movies = movies;
        this.context = context;
        mOnClickListener = listener;
    }
    //this function is called upon creation of viewholders, using the movie_item layout
    //and then simply returning the view
    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_item, viewGroup, false);

        return new MoviesViewHolder(v);

    }
    //override that deals with setting the movie images using Picasso
    @Override
    public void onBindViewHolder(MoviesViewHolder viewHolder, int position) {
        Movie movie = movies.get(position);

        Picasso.with(context).load(movie.getImage()).into(viewHolder.imageViewPoster);
    }

    //in this class we set the onClickListener on each movie view
    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imageViewPoster;

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();

            mOnClickListener.onListItemClick(clickedPosition);
        }

        public MoviesViewHolder(View itemView) {
            super(itemView);

            imageViewPoster = itemView.findViewById(R.id.movie_image);

            itemView.setOnClickListener(this);

        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
