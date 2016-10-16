package com.abitcreative.popularmovies.async

import android.os.AsyncTask
import retrofit2.Call

/**
 * Created by daber on 15/10/16.
 */
open class NetworkAsync<T>(val call: Call<T>, val onResult: (T?) -> Unit) : AsyncTask<Void, Void, T?>() {
    override fun doInBackground(vararg params: Void?): T {
        return call.execute().body()
    }

    override fun onPostExecute(result: T?) {
        onResult(result)
    }

}