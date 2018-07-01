package com.mashaal.journalapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mashaal.journalapp.db.DiaryContract.*;
/**
 * Created by Mohamed Mashaal on 7/1/2018.
 */

public class DairyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diaries.db";
    private static final int DATABASE_VERSION = 1;

    public DairyDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DIARIES_TABLE = "CREATE TABLE " + DiaryEntry.TABLE_NAME + " (" +
                DiaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiaryEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                DiaryEntry.COLUMN_NAME_CONTENT + " TEXT NOT NULL, " +
                DiaryEntry.COLUMN_NAME_USER_ID + " TEXT NOT NULL" +
                "); ";
        db.execSQL(SQL_CREATE_DIARIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DiaryEntry.TABLE_NAME);
        onCreate(db);
    }
}
