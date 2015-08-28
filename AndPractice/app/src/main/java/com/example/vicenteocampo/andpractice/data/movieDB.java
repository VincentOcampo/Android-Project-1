package com.example.vicenteocampo.andpractice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vicenteocampo.andpractice.data.movieContract.MovieEntry;

/**
 * Created by Vicente Ocampo on 8/25/2015.
 */
public class movieDB extends SQLiteOpenHelper {

     private static final int DATABASE_VERSION = 2;


     movieDB(Context context){

         super(context,MovieEntry.TABLE_NAME,null, DATABASE_VERSION);
     }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_POSTER + " TEXT UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_RATING + " TEXT UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_RELEASE + " TEXT UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_REVIEW + " TEXT UNIQUE NOT NULL, " +
                MovieEntry.COlUMN_SUMMARY + " TEXT UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_TRAILER + " TEXT UNIQUE NOT NULL, " + " );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
