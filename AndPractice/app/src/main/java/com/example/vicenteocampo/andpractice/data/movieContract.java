package com.example.vicenteocampo.andpractice.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Vicente Ocampo on 8/25/2015.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.vicenteocampo.andPractice";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    // defined columns of database for a movie entry
    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES)
               .build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String TABLE_NAME = "MovieDB";

        public static final String COLUMN_ID = "unique_id";

        public static final String COLUMN_TRAILER= "trailer";

        public static final String COLUMN_REVIEW = "review";

        public static final String COLUMN_POSTER = "poster";

        public static final String COlUMN_SUMMARY = "summary";

        public static final String COLUMN_INFO  = "info";

        // return Uri for a specific movie in db
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        //TODO create fav table
        public static Uri buildMovieSlot(String movieId){
            return CONTENT_URI.buildUpon().appendPath(movieId).build();
        }

    }

}
