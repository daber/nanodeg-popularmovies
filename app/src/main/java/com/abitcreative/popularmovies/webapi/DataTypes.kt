package com.abitcreative.popularmovies.webapi

/**
 * Created by daber on 15/10/16.
 */

data class ListResponse(val page: Int, val results: List<ListResult>)

data class ListResult(val id: Int, val poster_path: String)

data class DetailResponse(val id: Int, val original_title: String, val tagline: String, val overview: String, val poster_path: String, val release_date: String, val vote_average: Double)



