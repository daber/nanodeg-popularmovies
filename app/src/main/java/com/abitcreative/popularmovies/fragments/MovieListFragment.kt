package com.abitcreative.popularmovies.fragments

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abitcreative.popularmovies.R
import com.abitcreative.popularmovies.activites.DetailActivity
import com.abitcreative.popularmovies.adapters.MovieItemViewHolder
import com.abitcreative.popularmovies.adapters.MovieListAdapter
import com.abitcreative.popularmovies.async.NetworkAsync
import com.abitcreative.popularmovies.webapi.ListResponse
import com.abitcreative.popularmovies.webapi.ListResult
import com.abitcreative.popularmovies.webapi.TmdbApi
import retrofit2.Call

/**
 * Created by daber on 15/10/16.
 */
class MovieListFragment : Fragment() {

    internal lateinit var recyclerView: RecyclerView
    internal var adapter: RecyclerView.Adapter<MovieItemViewHolder>? = null
    internal var asyncTask: NetworkAsync<ListResponse>? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = v.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        return v;
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    override fun onStop() {
        asyncTask?.cancel(false)
        super.onStop()
    }

    fun onClick(movieId: Int) {
        val i = Intent(context, DetailActivity::class.java).putExtra(Intent.EXTRA_UID, movieId)
        activity.startActivity(i)
    }

    fun onResult(result: ListResponse) {
        adapter = MovieListAdapter(result.results, { onClick(it) })
        this@MovieListFragment.adapter = adapter
        recyclerView.adapter = adapter
    }

    private fun refresh() {
        val call = TmdbApi.getPopularMovies()
        val task = NetworkAsync(call, { onResult(it) })
        task.execute()
    }


}
