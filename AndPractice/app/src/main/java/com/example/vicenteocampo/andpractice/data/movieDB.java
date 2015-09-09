package com.example.vicenteocampo.andpractice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vicenteocampo.andpractice.data.MovieContract.MovieEntry;

/**
 * Created by Vicente Ocampo on 8/25/2015.
 */
public class MovieDB extends SQLiteOpenHelper {

     private static final int DATABASE_VERSION = 2;

     MovieDB(Context context){
         super(context,MovieEntry.TABLE_NAME,null, DATABASE_VERSION);
     }
    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_ID + " INTEGER , " +
                MovieEntry.COLUMN_POSTER + " BLOB , " +
                MovieEntry.COLUMN_INFO + " TEXT  , " +
                MovieEntry.COLUMN_REVIEW + " TEXT  , " +
                MovieEntry.COlUMN_SUMMARY + " TEXT , " +
                MovieEntry.COLUMN_TRAILER + " TEXT  " + " );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
