package com.abitcreative.popularmovies.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.abitcreative.popularmovies.R
import com.abitcreative.popularmovies.activites.DetailActivity
import com.abitcreative.popularmovies.activites.SettingsActivity
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
    internal lateinit var prefs: SharedPreferences
    val prefListener = object : SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            refresh()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.app_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.settings -> {
                activity.startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            R.id.refresh -> {
                refresh()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = v.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        return v;
    }

    override fun onStart() {
        super.onStart()
        refresh()
        prefs.registerOnSharedPreferenceChangeListener(prefListener)
    }

    override fun onStop() {
        prefs.unregisterOnSharedPreferenceChangeListener(prefListener)
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
        recyclerView.swapAdapter(adapter, false)
    }

    fun refresh() {

        val sortOrder = prefs.getString("SORT_ORDER", null)
        //null is impossible as default values are created with application object
        val call = if (sortOrder == "POPULAR") {
            TmdbApi.getPopularMovies()
        } else {
            TmdbApi.getTopRated()
        }
        val task = NetworkAsync(call, { onResult(it) })
        task.execute()
    }


}
