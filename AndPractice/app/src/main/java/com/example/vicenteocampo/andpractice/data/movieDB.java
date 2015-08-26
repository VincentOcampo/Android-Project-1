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
        db.execSQL("CREATE TABLE " );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
