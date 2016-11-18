package com.abitcreative.popularmovies.async;

import android.content.Context;

import com.abitcreative.popularmovies.persistence.FavoriteMovie;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by daber on 18/11/16.
 */

public class ToggleFavoriteAsyncTask extends CupboardAsyncTask<Boolean,Void,Boolean> {

    private final String poster_url;
    private final long id;

    public ToggleFavoriteAsyncTask(Context ctx, long id, String poster_url) {
        super(ctx);
        this.poster_url = poster_url;
        this.id = id;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        try {
            Boolean enable = params[0];
            if (enable) {
                FavoriteMovie fm = new FavoriteMovie();
                fm.id = id;
                fm.posterUrl = poster_url;
                cupboard().withDatabase(database).put(fm);
                return true;
            } else {
                cupboard().withDatabase(database).delete(FavoriteMovie.class,id);
                return false;
            }
        }finally {
            database.close();
        }
    }
}
