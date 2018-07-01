package com.mashaal.journalapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mashaal.journalapp.db.DiaryContract.*;
import com.mashaal.journalapp.DiaryItem;

import java.util.ArrayList;

/**
 * Created by Mohamed Mashaal on 7/1/2018.
 */

public class DataManager {
    private static DataManager uniqueInstance;
    private DairyDbHelper dbHelper;
    private SQLiteDatabase db;
    private String currentUserID;

    private DataManager(){

    }

    private DataManager(Context context){
        dbHelper = new DairyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static DataManager getDataManager(Context context){
        if (uniqueInstance == null)
            return uniqueInstance = new DataManager(context);
        return uniqueInstance;
    }

    public void setCurrentUserID(String currentUserID){
        this.currentUserID = currentUserID;
    }

    public boolean addNewDiaryForCurrentUser(DiaryItem diaryItem){
        ContentValues values = new ContentValues();
        values.put(DiaryEntry.COLUMN_NAME_TITLE, diaryItem.getTitle());
        values.put(DiaryEntry.COLUMN_NAME_CONTENT, diaryItem.getDairyContent());
        values.put(DiaryEntry.COLUMN_NAME_USER_ID,currentUserID);

        long newRowId = db.insert(DiaryEntry.TABLE_NAME, null, values);
        if (newRowId != -1)
            return true;
        else
            return false;
    }

    public int deleteDiaryForCurrentUser(DiaryItem diaryItem){
        String selection = DiaryEntry.COLUMN_NAME_TITLE + " = ?" + " AND " + DiaryEntry.COLUMN_NAME_USER_ID + " = ?" ;

        String[] selectionArgs = { diaryItem.getTitle(),currentUserID };

        int deletedRows = db.delete(DiaryEntry.TABLE_NAME, selection, selectionArgs);
        return deletedRows;
    }

    public int updateDiaryForCurrentUser(DiaryItem oldDiary, DiaryItem newDiary){
        String newTitle = newDiary.getTitle();
        String newContent = newDiary.getDairyContent();
        ContentValues values = new ContentValues();
        values.put(DiaryEntry.COLUMN_NAME_TITLE, newTitle);
        values.put(DiaryEntry.COLUMN_NAME_CONTENT, newContent);

        String selection = DiaryEntry.COLUMN_NAME_TITLE + " = ?" + " AND " + DiaryEntry.COLUMN_NAME_USER_ID + " = ?";
        String[] selectionArgs = { oldDiary.getTitle(),currentUserID };

        int count = db.update(
                DiaryEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public ArrayList<DiaryItem> getCurrentUserDiaries(){
        String[] projection = {
                DiaryEntry.COLUMN_NAME_TITLE,
                DiaryEntry.COLUMN_NAME_CONTENT
        };

        String selection = DiaryEntry.COLUMN_NAME_USER_ID + " = ?";
        String[] selectionArgs = { currentUserID };

        String sortOrder =
                DiaryEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = db.query(
                DiaryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        ArrayList<DiaryItem> dataSet = getDiaryItems(cursor);
        cursor.close();
        return dataSet;
    }

    private ArrayList<DiaryItem> getDiaryItems(Cursor cursor) {
        ArrayList<DiaryItem> diaries = new ArrayList<>();
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(DiaryEntry.COLUMN_NAME_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(DiaryEntry.COLUMN_NAME_CONTENT));
            int fIndex = title.indexOf('-');
            int sIndex = title.lastIndexOf('-');
            int year = Integer.parseInt(title.substring(0,fIndex));
            int month = Integer.parseInt(title.substring(fIndex+1,sIndex));
            int day = Integer.parseInt(title.substring(sIndex+1,title.length()));
            DiaryItem diaryItem = new DiaryItem(year,month,day);
            diaryItem.setDairyContent(content);
            diaries.add(diaryItem);
        }
        return diaries;
    }


}
