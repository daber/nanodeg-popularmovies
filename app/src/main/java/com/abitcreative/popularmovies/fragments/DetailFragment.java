package com.abitcreative.popularmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abitcreative.popularmovies.R;
import com.abitcreative.popularmovies.async.NetworkAsync;
import com.abitcreative.popularmovies.webapi.DetailResponse;
import com.abitcreative.popularmovies.webapi.TmdbApi;
import com.squareup.picasso.Picasso;

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
    private NetworkAsync<DetailResponse> asyncTask = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        poster = (ImageView) v.findViewById(R.id.poster_image);
        title = (TextView) v.findViewById(R.id.title_text);
        overview = (TextView) v.findViewById(R.id.overview_text);
        userrating = (TextView) v.findViewById(R.id.user_rating);
        releasedate = (TextView) v.findViewById(R.id.relase_date);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        long movie_id = getActivity().getIntent().getLongExtra(Intent.EXTRA_UID, 0);
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
        if(response == null){
            Log.e(TAG,"Response returend null");
            Toast.makeText(getContext(), R.string.could_not_get_data, Toast.LENGTH_LONG).show();
            return;
        }
        String posterUrl = TmdbApi.INSTANCE.getImageUrl(response.poster_path);
        String titleText = response.original_title;
        String overviewText = response.overview;
        Double userratingDouble = response.vote_average;
        String releasedateText = response.release_date;

        Picasso.with(getContext()).load(posterUrl).into(poster);
        title.setText(titleText);
        overview.setText(overviewText);
        userrating.setText( String.format("%.2f",userratingDouble));
        releasedate.setText(releasedateText);
    }
}
