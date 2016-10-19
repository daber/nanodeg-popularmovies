package com.abitcreative.popularmovies.webapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by daber on 19/10/16.
 */


interface TmdbInterface {
    @GET("3/movie/popular")
    Call<ListResponse> getPopularMovies(@Query("api_key") String apikey);

    @GET("t/p/{width}{image}")
    Call<ResponseBody> getImage(@Path("image") String image, @Path("width") String width);


    @GET("3/movie/top_rated")
    Call<ListResponse> getTopRated(@Query("api_key") String apikey);


    @GET("3/movie/{movie_id}")
    Call<DetailResponse> getMovieDetails(@Path("movie_id") Long movie_id, @Query("api_key") String apikey);


}

