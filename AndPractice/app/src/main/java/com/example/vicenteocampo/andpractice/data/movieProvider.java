package com.example.vicenteocampo.andpractice.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Vicente Ocampo on 8/28/2015.
 */
public class movieProvider extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private movieDB mOpenHelper;

    static final int MOVIE = 100;


    private static final SQLiteQueryBuilder sMovieQueryBuilder;


    static{
        sMovieQueryBuilder = new SQLiteQueryBuilder();




    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
