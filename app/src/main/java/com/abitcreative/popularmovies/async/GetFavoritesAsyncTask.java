package com.abitcreative.popularmovies.async;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.abitcreative.popularmovies.persistence.CupboardOpenHelper;
import com.abitcreative.popularmovies.persistence.FavoriteMovie;

import java.util.List;

import nl.qbusict.cupboard.DatabaseCompartment;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by daber on 18/11/16.
 */

public class GetFavoritesAsyncTask extends CupboardAsyncTask<Void, Void, List<FavoriteMovie>> {

    private final OnFavoritesResult callback;
    private SQLiteDatabase database;

    @Override
    protected List<FavoriteMovie> doInBackground(Void... params) {
        try {
            QueryResultIterable<FavoriteMovie> result = cupboard().withDatabase(database).query(FavoriteMovie.class).query();
            return result.list();
        } finally {
            database.close();
        }
    }

    public interface OnFavoritesResult {
        void onFavoritesResult(List<FavoriteMovie> movies);
    }

    public GetFavoritesAsyncTask(Context ctx, OnFavoritesResult callback) {
        super(ctx);
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<FavoriteMovie> favoriteMovies) {
        callback.onFavoritesResult(favoriteMovies);

    }
}
