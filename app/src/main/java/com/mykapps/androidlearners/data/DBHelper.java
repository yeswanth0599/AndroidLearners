package com.mykapps.androidlearners.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "androidlearners_check1.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + Contract.EntryInfo.TABLE_NAME + " (" +
                Contract.EntryInfo.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                Contract.EntryInfo.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.EntryInfo.COLUMN_IMAGE + " TEXT NOT NULL, " +
                Contract.EntryInfo.COLUMN_VIDEO_URL + " TEXT NOT NULL, " +
                Contract.EntryInfo.COLUMN_LIKES_COUNT + " INTEGER NOT NULL, " +
                Contract.EntryInfo.COLUMN_COMMENTS_COUNT + " INTEGER NOT NULL, " +
                Contract.EntryInfo.COLUMN_PLACEHOLDER_STRING + " TEXT NOT NULL); ";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.EntryInfo.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
