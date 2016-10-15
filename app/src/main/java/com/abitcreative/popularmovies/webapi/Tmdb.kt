package com.abitcreative.popularmovies.webapi

import com.abitcreative.popularmovies.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by daber on 15/10/16.
 */

private val jsonConverter = GsonConverterFactory.create()
private val delegate = Retrofit.Builder().addConverterFactory(jsonConverter).baseUrl(BuildConfig.BASE_URL).build().create(TmdbInterface::class.java)



object TmdbApi:TmdbInterface by delegate{
    //image comes with leading slash from api
    fun getImageUrl(image:String, width:String ="w158"):String = "${BuildConfig.BASE_URL}/t/p/$width$image";
}

interface TmdbInterface {
    @GET("3/movie/popular")
    fun  getPopularMovies(@Query("api_key") apikey:String = BuildConfig.API_KEY_V3): Call<ListResponse>

    @GET("t/p/{width}{image}")
    fun getImage(@Path("image")image:String, @Path("width")width:String ="w158"):Call<ResponseBody>


    @GET("3/movie/top_rated")
    fun  getTopRated(@Query("api_key") apikey:String = BuildConfig.API_KEY_V3):Call<ListResponse>

    @GET("3/movie/{movie_id}")
    fun  getMovieDetails(@Path("movie_id") movie_id:Int, @Query("api_key") apikey:String = BuildConfig.API_KEY_V3):Call<DetailResponse>

}
