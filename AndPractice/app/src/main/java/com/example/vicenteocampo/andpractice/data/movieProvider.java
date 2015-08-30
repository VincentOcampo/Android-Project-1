package com.example.vicenteocampo.andpractice.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.example.vicenteocampo.andpractice.data.MovieContract.MovieEntry;

/**
 * Created by Vicente Ocampo on 8/28/2015.
 */
public class MovieProvider extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDB mOpenHelper;

    static final int MOVIE = 100;
    static final int MOVIE_ID = 101;


    static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;


        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIE );
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/#",MOVIE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDB(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = mOpenHelper.getReadableDatabase().query(MovieEntry.TABLE_NAME,null,
                selection,selectionArgs,null,null,null); ;

        return retCursor;
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
