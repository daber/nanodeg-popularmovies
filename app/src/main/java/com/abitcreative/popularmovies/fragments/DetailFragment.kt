package com.abitcreative.popularmovies.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.abitcreative.popularmovies.R
import com.abitcreative.popularmovies.async.NetworkAsync
import com.abitcreative.popularmovies.webapi.DetailResponse
import com.abitcreative.popularmovies.webapi.TmdbApi
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

/**
 * Created by daber on 15/10/16.
 */

class DetailFragment : Fragment() {
    val TAG = DetailFragment::class.java.simpleName
    lateinit var poster: ImageView
    lateinit var title: TextView
    lateinit var overview: TextView
    lateinit var userrating: TextView
    lateinit var releasedate: TextView
    var asyncTask: NetworkAsync<DetailResponse>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_detail, container, false)
        poster = v.findViewById(R.id.poster_image) as ImageView
        title = v.findViewById(R.id.title_text) as TextView
        overview = v.findViewById(R.id.overview_text) as TextView
        userrating = v.findViewById(R.id.user_rating) as TextView
        releasedate = v.findViewById(R.id.relase_date) as TextView



        return v;
    }

    override fun onStart() {
        super.onStart()

        val movie_id = activity.intent!!.getIntExtra(Intent.EXTRA_UID, 0)
        if (movie_id != 0) {
            val call = TmdbApi.getMovieDetails(movie_id);
            asyncTask = NetworkAsync(call, { onDetail(it) })
            asyncTask?.execute()
        }

    }

    override fun onStop() {
        asyncTask?.cancel(false)
        super.onStop()
    }


    fun onDetail(detailResponse: DetailResponse?) {
        if (detailResponse == null) {
            Log.e(TAG, "Returned response was null")
            Toast.makeText(context, R.string.could_not_get_data, Toast.LENGTH_LONG).show()
            return
        }
        val posterUrl = TmdbApi.getImageUrl(detailResponse.poster_path)
        val titleText = detailResponse.original_title
        val overviewText = detailResponse.overview
        val userratingDouble = detailResponse.vote_average
        val releasedateText = detailResponse.release_date

        Picasso.with(context).load(posterUrl).into(poster)
        title.text = titleText
        overview.text = overviewText
        userrating.text = DecimalFormat("#.00").format(userratingDouble)
        releasedate.text = releasedateText

    }
}