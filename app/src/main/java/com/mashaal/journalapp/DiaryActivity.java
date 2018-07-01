package com.mashaal.journalapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mashaal.journalapp.db.DataManager;

public class DiaryActivity extends AppCompatActivity {
    private static final String TAG = DiaryActivity.class.getSimpleName();
    ConstraintLayout mDiaryScreen;
    TextView mBarYear;
    TextView mBarTitle;
    TextView mBarDayMonth;
    TextView mContentArea;
    ConstraintLayout mEditScreen;
    EditText mDayEditText;
    EditText mMonthEditText;
    EditText mYearEditText;
    EditText mContentEditText;
    EditText mTitleEditText;
    DataManager mDataManager;
    private DiaryItem item;
    private String diary_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        mDataManager = DataManager.getDataManager(this);
        setUpDiaryShowViews();
        setUpDiaryEditViews();
        extractDiaryItem();
        updateDiaryViewsContent();
    }

    private void updateDiaryViewsContent() {
        mBarYear.setText(Integer.toString(item.getYear()));
        mBarTitle.setText(item.getTitle());
        mBarDayMonth.setText(item.getDayMonth());
        mContentArea.setText(item.getDairyContent());
    }

    private void extractDiaryItem(){
        diary_date = getIntent().getStringExtra("diary_date");
        item = mDataManager.getDiaryItem(diary_date);
    }

    private void setUpDiaryShowViews() {
        mDiaryScreen = (ConstraintLayout)findViewById(R.id.diary_show_screen);
        mBarYear =(TextView) findViewById(R.id.bar_item_year);
        mBarTitle =(TextView) findViewById(R.id.bar_item_title);
        mBarDayMonth =(TextView) findViewById(R.id.bar_item_day_month);
        mContentArea =(TextView) findViewById(R.id.content_area);
    }

    private void setUpDiaryEditViews() {
        mEditScreen = (ConstraintLayout)findViewById(R.id.diary_edit_screen);
        mDayEditText = (EditText)findViewById(R.id.diray_edit_day);
        mMonthEditText = (EditText)findViewById(R.id.diray_edit_month);
        mYearEditText = (EditText)findViewById(R.id.diray_edit_year);
        mContentEditText = (EditText)findViewById(R.id.diray_edit_content);
        mTitleEditText = (EditText)findViewById(R.id.diray_edit_title);
    }

    public void editDiary(View view) {
        showEditScreen();
        updateEditViewsContent();
    }

    private void updateEditViewsContent() {
        mDayEditText.setText(Integer.toString(item.getDay()));
        mMonthEditText.setText(Integer.toString(item.getMonth()));
        mYearEditText.setText(Integer.toString(item.getYear()));
        mTitleEditText.setText(item.getTitle());
        mContentEditText.setText(item.getDairyContent());
    }

    private void showEditScreen() {
        mEditScreen.setVisibility(View.VISIBLE);
        mDiaryScreen.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void cancelEdit(View view) {
        showDiaryScreen();
    }

    private void showDiaryScreen() {
        mEditScreen.setVisibility(View.INVISIBLE);
        mDiaryScreen.setVisibility(View.VISIBLE);
    }

    public void doneEditing(View view) {
        DiaryItem newItem = getTheNewItem();
        mDataManager.updateDiaryForCurrentUser(item,newItem);
        getIntent().putExtra("diary_date",newItem.getDate());
        extractDiaryItem();
        updateDiaryViewsContent();
        showDiaryScreen();
    }

    private DiaryItem getTheNewItem() {
        int year = Integer.parseInt(mYearEditText.getText().toString());
        int month = Integer.parseInt(mMonthEditText.getText().toString());
        int day = Integer.parseInt(mDayEditText.getText().toString());
        String title = mTitleEditText.getText().toString();
        String content = mContentEditText.getText().toString();
        DiaryItem item = new DiaryItem(year,month,day);
        item.setDairyContent(content);
        item.setTitle(title);
        return item;
    }

    @Override
    public void onBackPressed() {
        if(inDiaryScreen()){
            this.finish();
        }
        else{
            showDiaryScreen();
        }
    }

    private boolean inDiaryScreen() {
        if(mDiaryScreen.getVisibility() == View.VISIBLE){
            return true;
        }
        return false;
    }
}
