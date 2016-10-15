package com.abitcreative.popularmovies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.abitcreative.popularmovies.R
import com.abitcreative.popularmovies.webapi.ListResult
import com.abitcreative.popularmovies.webapi.TmdbApi
import com.squareup.picasso.Picasso

/**
 * Created by daber on 15/10/16.
 */

class MovieListAdapter(val list: List<ListResult>, val onClick: (Int) -> Unit) : RecyclerView.Adapter<MovieItemViewHolder>() {
    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieItemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = inflater.inflate(R.layout.item_movie_list, parent, false) as ImageView

        return MovieItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder?, position: Int) {
        val context = holder!!.imageView.context
        val url = TmdbApi.getImageUrl(list[position].poster_path)
        holder.movieId = list[position].id
        Picasso.with(context).load(url).into(holder.imageView)

    }
}

class MovieItemViewHolder(val imageView: ImageView, val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(imageView) {
    var movieId: Int = 0

    init {
        imageView.setOnClickListener { onClick(movieId) }
    }

}