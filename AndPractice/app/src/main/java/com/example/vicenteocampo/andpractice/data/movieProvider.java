package com.example.vicenteocampo.andpractice.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.vicenteocampo.andpractice.data.MovieContract.MovieEntry;

/**
 * Created by Vicente Ocampo on 8/28/2015.
 */
public class MovieProvider extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDB mOpenHelper;

    static final int MOVIES= 100;
    static final int FAV_MOVIES = 101;


    static UriMatcher buildUriMatcher(){
        //TODO: use UriMatcher when implementing fav table
        //UriMatcher match incoming Uri to a specific table

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIES );

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDB(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = mOpenHelper.getReadableDatabase().query(MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri retUri = null;

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long _id = db.insert(MovieEntry.TABLE_NAME,null,values);

        if( _id > 0)
            retUri = MovieEntry.buildMovieUri(_id);
        else
            throw new android.database.SQLException("Failed insertion of "+ uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        int rowsDeleted;

        if(selection == null) selection = "1";

        rowsDeleted = db.delete(
                MovieEntry.TABLE_NAME,selection, selectionArgs);
        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }


        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        //TODO: use match when updating fav table
        final int match = sUriMatcher.match(uri);

        int rowsUpdated;

        if(selection == null) selection = "1";

        rowsUpdated = db.update(
                MovieEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated!= 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
    // Useful for inserting a batch of movies
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(MovieEntry.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
            return returnCount;
        }
    

}
