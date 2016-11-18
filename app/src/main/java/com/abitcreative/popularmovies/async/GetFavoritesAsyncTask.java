package com.abitcreative.popularmovies.async;

import android.content.Context;

import com.abitcreative.popularmovies.webapi.ListResult;

import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by daber on 18/11/16.
 */

public class GetFavoritesAsyncTask extends CupboardAsyncTask<Void, Void, List<ListResult>> {

    private final OnFavoritesResult callback;

    @Override
    protected List<ListResult> doInBackground(Void... params) {
        try {
            QueryResultIterable<ListResult> result = cupboard().withDatabase(database).query(ListResult.class).query();
            return result.list();
        } finally {
            database.close();
        }
    }

    public interface OnFavoritesResult {
        void onFavoritesResult(List<ListResult> movies);
    }

    public GetFavoritesAsyncTask(Context ctx, OnFavoritesResult callback) {
        super(ctx);
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<ListResult> list) {
        callback.onFavoritesResult(list);

    }
}
