package com.abitcreative.popularmovies.async

import android.os.AsyncTask
import android.util.Log
import retrofit2.Call

/**
 * Created by daber on 15/10/16.
 */
open class NetworkAsync<T>(val call: Call<T>, val onResult: (T?) -> Unit) : AsyncTask<Void, Void, T?>() {
    val TAG = javaClass.simpleName
    override fun doInBackground(vararg params: Void?): T? {
        try {
            return call.execute().body()
        } catch (e: Exception) {
            Log.e(TAG, "Exception while making request", e)
            return null
        }
    }

    override fun onPostExecute(result: T?) {
        onResult(result)
    }

}