package com.abitcreative.popularmovies.adapters;


import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

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
