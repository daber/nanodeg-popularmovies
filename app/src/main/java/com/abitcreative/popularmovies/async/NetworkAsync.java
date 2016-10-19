package com.abitcreative.popularmovies.async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by daber on 19/10/16.
 */

public class NetworkAsync<T> extends AsyncTask<Void, Void, T> {
    public interface OnNetworkResponseListener<T> {
        void onNetworkResponse(T response);
    }

    private final static String TAG = NetworkAsync.class.getSimpleName();

    private OnNetworkResponseListener<T> listener;
    private Call<T> call;

    public NetworkAsync(Call<T> call, OnNetworkResponseListener<T> listener) {
        this.listener = listener;
        this.call = call;
    }

    @Override
    protected T doInBackground(Void... params) {
        try {
            T t = call.execute().body();
            return t;
        } catch (IOException e) {
            Log.e(TAG, "Fetching data failed", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(T t) {
        listener.onNetworkResponse(t);
    }
}
