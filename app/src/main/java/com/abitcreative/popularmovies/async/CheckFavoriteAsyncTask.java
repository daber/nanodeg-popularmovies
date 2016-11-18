package com.abitcreative.popularmovies.async;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.abitcreative.popularmovies.persistence.CupboardOpenHelper;
import com.abitcreative.popularmovies.webapi.ListResult;


import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by daber on 18/11/16.
 */

public class CheckFavoriteAsyncTask extends CupboardAsyncTask<Long, Void, Boolean> {


    public CheckFavoriteAsyncTask(Context ctx) {
        super(ctx);
    }

    @Override
    protected Boolean doInBackground(Long... params) {
        try {
            String id = params[0].toString();
            ListResult movie = cupboard().withDatabase(database).query(ListResult.class).withSelection("id = ?", id).get();
            return movie != null;
        }finally {
            database.close();
        }
    }


}
