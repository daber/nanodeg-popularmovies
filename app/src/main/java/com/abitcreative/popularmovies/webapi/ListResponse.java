package com.abitcreative.popularmovies.webapi;

import java.util.List;

/**
 * Created by daber on 19/10/16.
 */

//data class ListResponse2(val page: Int, val results: List<ListResult>)
public class ListResponse {
    public int page;
    public List<ListResult> results;
}
