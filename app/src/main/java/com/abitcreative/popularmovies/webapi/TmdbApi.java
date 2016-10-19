package com.abitcreative.popularmovies.webapi;

import com.abitcreative.popularmovies.BuildConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by daber on 19/10/16.
 */

public enum TmdbApi {
    INSTANCE;

    final private TmdbInterface delegate;

    TmdbApi() {
        GsonConverterFactory converter = GsonConverterFactory.create();
        delegate = new Retrofit.Builder().addConverterFactory(converter).baseUrl(BuildConfig.BASE_URL).build().create(TmdbInterface.class);
    }


    public Call<ListResponse> getPopularMovies() {
        return delegate.getPopularMovies(BuildConfig.API_KEY_V3);
    }


    public Call<ResponseBody> getImage(String image) {
        return delegate.getImage(image, "w154");
    }

    public Call<ListResponse> getTopRated() {
        return delegate.getTopRated(BuildConfig.API_KEY_V3);
    }

    public Call<DetailResponse> getMovieDetails(Long movie_id) {
        return delegate.getMovieDetails(movie_id, BuildConfig.API_KEY_V3);
    }

    public String getImageUrl(String image) {
        String width = "w154";
        StringBuilder builder = new StringBuilder(BuildConfig.BASE_IMAGE_URL);
        builder.append("/t/p/");
        builder.append(width);
        builder.append(image);
        return builder.toString();
    }
}
