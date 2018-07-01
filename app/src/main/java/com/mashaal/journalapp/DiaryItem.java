package com.mashaal.journalapp;

/**
 * Created by Mohamed Mashaal on 6/30/2018.
 */

public class DiaryItem {
    private int year;
    private int month;
    private int day;
    private String DairyContent ;

    public DiaryItem(int year, int month , int day){
        this.year = year;
        this.month = month;
        this.day = day;
        DairyContent = new String();
    }

    public void setDairyContent(String dairyContent){
        DairyContent = dairyContent;
    }

    public void updateDate(int year, int month, int day){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDairyContent() {
        return DairyContent;
    }

    public String getTitle(){
        return new String(Integer.toString(year) + "-" + Integer.toString(month)+ "-" + Integer.toString(day));
    }

    public int getID(){
        String ID = new String(Integer.toString(year) + Integer.toString(month)+ Integer.toString(day));
        return Integer.parseInt(ID);
    }

}
