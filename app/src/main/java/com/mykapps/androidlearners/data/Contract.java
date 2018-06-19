package com.mykapps.androidlearners.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    public static final String AUTHORITY = "com.mykapps.androidlearners";
    public static final Uri BASE_CONTENT_AUTHORITY = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "learners_favorites";
    public static final String JAVA_JSON_URL = "https://firebasestorage.googleapis.com/v0/b/android-learners.appspot.com/o/JsonStore%2FJava%2Fjava_source.json?alt=media&token=81c227cb-b947-4ff9-b07f-aab36c3603f0";
    public static final String KOTLIN_JSON_URL = "https://firebasestorage.googleapis.com/v0/b/android-learners.appspot.com/o/JsonStore%2FKotlin%2Fkotlin_source.json?alt=media&token=675e389e-d001-4e7a-b94c-84dde426643f";

    public static final class EntryInfo implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_AUTHORITY.buildUpon().appendPath(PATH).build();
        /******************************************************************************/
        /*********************Database Constants************************************/
        /******************************************************************************/
        public static final String TABLE_NAME = "learners_favorites";
        // "_ID" column in addition to the two below
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_VIDEO_URL = "video_url";
        public static final String COLUMN_LIKES_COUNT = "likes_count";
        public static final String COLUMN_COMMENTS_COUNT = "comments_count";
        public static final String COLUMN_PLACEHOLDER_STRING = "placeholderString";

    }


}
