package com.abitcreative.popularmovies.webapi

import com.abitcreative.popularmovies.BuildConfig
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by daber on 15/10/16.
 */
class TmdbApiTest{
    @Test
    fun testRequestTopRated(){
        val call = TmdbApi.getTopRated()
        val res =  call.execute().body()
        assertNotNull(res)
        assertEquals(1,res.page)
        assertFalse(res.results.isEmpty())

    }

    @Test
    fun testRequestPopular(){
        val call = TmdbApi.getPopularMovies()
        val res =  call.execute().body()
        assertNotNull(res)
        assertEquals(1,res.page)
        assertFalse(res.results.isEmpty())

    }

    @Test
    fun testRequestDetail(){
        val call = TmdbApi.getMovieDetails(505);
        val res =  call.execute().body()
        assertNotNull(res)
        assertEquals(505,res.id)
        assertEquals("Johnny Handsome",res.original_title)

    }


    @Test
    fun testGetImagePath(){
        val imagePath = TmdbApi.getImageUrl("/8NVYY7BH8OhPCRiIREB0SBu9Ly5.jpg");

        assertEquals("${BuildConfig.BASE_IMAGE_URL}/t/p/w154/8NVYY7BH8OhPCRiIREB0SBu9Ly5.jpg",imagePath)

    }
}