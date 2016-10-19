package com.abitcreative.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abitcreative.popularmovies.R;
import com.abitcreative.popularmovies.webapi.ListResult;
import com.abitcreative.popularmovies.webapi.TmdbApi;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by daber on 19/10/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieItemViewHolder> {
    private final List<ListResult> list;
    private final MovieClickedListener onMovieClickListener;

    public interface MovieClickedListener {
        void onMovieClicked(long movieId);
    }

    public MovieListAdapter(List<ListResult> list, MovieClickedListener onMovieClickListener) {
        this.list = list;
        this.onMovieClickListener = onMovieClickListener;
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageView view = (ImageView) inflater.inflate(R.layout.item_movie_list, parent, false);
        MovieItemViewHolder holder = new MovieItemViewHolder(view,onMovieClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder holder, int position) {
        holder.movieId = list.get(position).id;
        Context ctx = holder.imageView.getContext();
        String poster = TmdbApi.INSTANCE.getImageUrl(list.get(position).poster_path);
        Picasso.with(ctx).load(poster).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
