package com.mashaal.journalapp;

/**
 * Created by Mohamed Mashaal on 6/30/2018.
 */

public class DiaryItem {
    private static final String [] MONTHS ={"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};
    private int year;
    private int month;
    private int day;
    private String DairyContent ;
    private String title;

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


    public String getDate(){
        return new String(Integer.toString(year) + "-" + Integer.toString(month)+ "-" + Integer.toString(day));
    }

    public String getDayMonth(){
        return day + "\n" + get3LettersMonth(month);
    }

    private String get3LettersMonth(int month){
        return MONTHS[month-1];
    }

    public int getID(){
        String ID = new String(Integer.toString(year) + Integer.toString(month)+ Integer.toString(day));
        return Integer.parseInt(ID);
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

}
