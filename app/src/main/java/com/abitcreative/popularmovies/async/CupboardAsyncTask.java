package com.abitcreative.popularmovies.async;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.abitcreative.popularmovies.persistence.CupboardOpenHelper;

/**
 * Created by daber on 18/11/16.
 */

public abstract class CupboardAsyncTask<A,B,C> extends AsyncTask <A,B,C> {
    protected final SQLiteDatabase database;
    public CupboardAsyncTask(Context ctx){
        database = new CupboardOpenHelper(ctx).getWritableDatabase();
    }
}
