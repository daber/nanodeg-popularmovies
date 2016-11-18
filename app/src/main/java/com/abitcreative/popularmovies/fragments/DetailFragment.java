package com.abitcreative.popularmovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abitcreative.popularmovies.R;
import com.abitcreative.popularmovies.adapters.ReviewRecyclerViewAdapter;
import com.abitcreative.popularmovies.adapters.VideosRecyclerViewAdapter;
import com.abitcreative.popularmovies.async.CheckFavoriteAsyncTask;
import com.abitcreative.popularmovies.async.NetworkAsync;
import com.abitcreative.popularmovies.async.ToggleFavoriteAsyncTask;
import com.abitcreative.popularmovies.persistence.CupboardOpenHelper;
import com.abitcreative.popularmovies.webapi.DetailResponse;
import com.abitcreative.popularmovies.webapi.Review;
import com.abitcreative.popularmovies.webapi.TmdbApi;
import com.abitcreative.popularmovies.webapi.Video;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;

/**
 * Created by daber on 19/10/16.
 */

public class DetailFragment extends Fragment implements NetworkAsync.OnNetworkResponseListener<DetailResponse> {
    private final static String TAG = DetailFragment.class.getSimpleName();

    private ImageView poster = null;
    private TextView title = null;
    private TextView overview = null;
    private TextView userrating = null;
    private TextView releasedate = null;
    private RecyclerView reviews = null;
    private RecyclerView videos = null;
    private FloatingActionButton fab = null;

    private NetworkAsync<DetailResponse> asyncTask = null;
    private long movie_id;
    private String posterPath;
    private boolean isFavorite;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        poster = (ImageView) v.findViewById(R.id.poster_image);
        title = (TextView) v.findViewById(R.id.title_text);
        overview = (TextView) v.findViewById(R.id.overview_text);
        userrating = (TextView) v.findViewById(R.id.user_rating);
        releasedate = (TextView) v.findViewById(R.id.relase_date);
        reviews = (RecyclerView) v.findViewById(R.id.reviews);
        videos = (RecyclerView) v.findViewById(R.id.videos);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);


        reviews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        videos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return v;
    }

    private void checkIfFavorite() {
        CheckFavoriteAsyncTask task = new CheckFavoriteAsyncTask(getContext()) {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                isFavorite = aBoolean;
                updateFabState();
            }
        };
        task.execute(movie_id);
    }

    private void updateFabState() {
        fab.setVisibility(View.VISIBLE);
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        } else {

            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });
    }

    private void toggleFavorite() {
        boolean targetState = !isFavorite;
        ToggleFavoriteAsyncTask task = new ToggleFavoriteAsyncTask(getContext(), movie_id, posterPath) {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                isFavorite = aBoolean;
                updateFabState();
            }
        };
        task.execute(targetState);
    }


    @Override
    public void onStart() {
        super.onStart();

        movie_id = getActivity().getIntent().getLongExtra(Intent.EXTRA_UID, 0);
        if (movie_id != 0) {
            Call<DetailResponse> call = TmdbApi.INSTANCE.getMovieDetails(movie_id);
            asyncTask = new NetworkAsync<DetailResponse>(call, this);
            asyncTask.execute();
        }
    }

    @Override
    public void onStop() {
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        super.onStop();
    }

    @Override
    public void onNetworkResponse(DetailResponse response) {
        if (response == null) {
            Log.e(TAG, "Response returend null");
            Toast.makeText(getContext(), R.string.could_not_get_data, Toast.LENGTH_LONG).show();
            return;
        }
        posterPath = response.poster_path;
        String posterUrl = TmdbApi.INSTANCE.getImageUrl(response.poster_path);
        String titleText = response.original_title;
        String overviewText = response.overview;
        Double userratingDouble = response.vote_average;
        String releasedateText = response.release_date;
        List<Video> videoList = response.videos.results;
        List<Review> reviewList = response.reviews.results;

        videos.setAdapter(new VideosRecyclerViewAdapter(getContext(), videoList));
        reviews.setAdapter(new ReviewRecyclerViewAdapter(reviewList));

        Picasso.with(getContext()).load(posterUrl).into(poster);
        title.setText(titleText);
        overview.setText(overviewText);

        userrating.setText(String.format(Locale.getDefault(), "%.2f", userratingDouble));
        releasedate.setText(releasedateText);
        checkIfFavorite();
    }
}
