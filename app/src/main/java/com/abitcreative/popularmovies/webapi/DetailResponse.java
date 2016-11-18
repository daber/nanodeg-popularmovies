package com.abitcreative.popularmovies.webapi;

import java.util.List;

/**
 * Created by daber on 19/10/16.
 */
//data class DetailResponse2(val id: Int, val original_title: String, val tagline: String, val overview: String, val poster_path: String, val release_date: String, val vote_average: Double)
public class DetailResponse {
    public Long id;
    public String original_title;
    public String tagline;
    public String overview;
    public String poster_path;
    public String release_date;
    public Double vote_average;
    public List<Review> reviews;
    public List<Video> videos;
}
