package com.example.vicenteocampo.andpractice.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Vicente Ocampo on 8/25/2015.
 */
public class movieContract {

    // defined columns of database for a movie entry
    public static final class MovieEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.example.vicenteocampo.and";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String TABLE_NAME = "movieDB";

        public static final String COLUMN_ID = "unique_id";

        public static final String COLUMN_TRAILER= "trailer";

        public static final String COLUMN_REVIEW = "review";

        public static final String COLUMN_POSTER = "poster";

        public static final String COlUMN_SUMMARY = "summary";

        public static final String COLUMN_RATING  = "rating";

        public static final String COLUMN_RELEASE = "release";
    }

}
