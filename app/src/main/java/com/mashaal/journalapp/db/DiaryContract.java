package com.mashaal.journalapp.db;

import android.provider.BaseColumns;

/**
 * Created by Mohamed Mashaal on 7/1/2018.
 */

public final class DiaryContract {

    private DiaryContract() {}

    /* Inner class that defines the table contents */
    public static final class DiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "diary";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_USER_ID = "user_id";
    }
}
