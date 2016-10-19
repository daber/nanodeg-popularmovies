package com.abitcreative.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by daber on 19/10/16.
 */

public class MovieItemViewHolder extends RecyclerView.ViewHolder {


    public ImageView imageView;
    public long movieId;

    public MovieItemViewHolder(ImageView imageView, final MovieListAdapter.MovieClickedListener movieClickedListener) {
        super(imageView);
        this.imageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickedListener.onMovieClicked(movieId);
            }
        });

    }

}
