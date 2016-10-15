package com.abitcreative.popularmovies.webapi

import com.abitcreative.popularmovies.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by daber on 15/10/16.
 */

interface TmdbApi {
    @GET("3/movie/popular")
    fun  getPopularMovies(@Query("api_key") apikey:String = BuildConfig.API_KEY_V3)

    @GET("t/p/{width}/{image}")
    fun getImage(@Path("image")image:String, @Path("width")width:String ="w158")

    @GET("3/movie/top_rated")
    fun  getTopRated(@Query("api_key") apikey:String = BuildConfig.API_KEY_V3)



}
