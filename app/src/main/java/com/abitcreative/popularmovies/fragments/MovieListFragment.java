package com.abitcreative.popularmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abitcreative.popularmovies.R;
import com.abitcreative.popularmovies.activites.DetailActivity;
import com.abitcreative.popularmovies.activites.SettingsActivity;
import com.abitcreative.popularmovies.adapters.MovieItemViewHolder;
import com.abitcreative.popularmovies.adapters.MovieListAdapter;
import com.abitcreative.popularmovies.async.GetFavoritesAsyncTask;
import com.abitcreative.popularmovies.async.NetworkAsync;
import com.abitcreative.popularmovies.webapi.ListResponse;
import com.abitcreative.popularmovies.webapi.ListResult;
import com.abitcreative.popularmovies.webapi.TmdbApi;

import java.util.List;

import retrofit2.Call;

/**
 * Created by daber on 19/10/16.
 */

public class MovieListFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener, MovieListAdapter.MovieClickedListener, NetworkAsync.OnNetworkResponseListener<ListResponse>,GetFavoritesAsyncTask.OnFavoritesResult {

    private static final String TAG = MovieListFragment.class.getSimpleName();
    RecyclerView recyclerView;
    RecyclerView.Adapter<MovieItemViewHolder> adapter;
    AsyncTask asyncTask;
    SharedPreferences prefs;

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        refresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                getActivity().startActivity(new Intent(getContext(), SettingsActivity.class));
                return true;

            case R.id.refresh:
                refresh();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        prefs.unregisterOnSharedPreferenceChangeListener(this);
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        super.onDestroyView();
    }

    @Override
    public void onMovieClicked(long movieId) {
        Intent i = new Intent(getContext(), DetailActivity.class).putExtra(Intent.EXTRA_UID, movieId);
        getActivity().startActivity(i);
    }

    @Override
    public void onNetworkResponse(ListResponse response) {
        if (response == null) {
            Log.e(TAG, "Returned response was null");
            Toast.makeText(getContext(), R.string.could_not_get_data, Toast.LENGTH_LONG).show();
            return;
        }
        adapter = new MovieListAdapter(response.results, this);
        recyclerView.swapAdapter(adapter, false);
    }
    @SuppressWarnings("unchecked")
    public void refresh() {

        String sortOrder = prefs.getString("SORT_ORDER", null);
        //null is impossible as default values are created with application object
        Call<ListResponse> call;

        switch(sortOrder){
            case "POPULAR":
                call = TmdbApi.INSTANCE.getPopularMovies();
                asyncTask = new NetworkAsync<>(call, this);
                break;
            case "TOP_RATED":
                call = TmdbApi.INSTANCE.getTopRated();
                asyncTask = new NetworkAsync<>(call, this);
                break;
            case "FAVORITES":
                asyncTask = new GetFavoritesAsyncTask(getContext(),this);
                break;
        }

        asyncTask.execute(new Void[0]);
    }

    @Override
    public void onFavoritesResult(List<ListResult> movies) {
        adapter = new MovieListAdapter(movies, this);
        recyclerView.swapAdapter(adapter, false);
    }
}
