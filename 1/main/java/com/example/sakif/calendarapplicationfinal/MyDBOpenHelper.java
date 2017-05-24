package com.example.sakif.calendarapplicationfinal;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;

/**
 * Created by sakif on 5/18/2017.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(Context context) {

        super(context, "CalendarNoteDatabase.db", null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table tbNoteItem(_id integer primary key autoincrement, " +
                "noteItemTitle text not null, " +
                "noteItemText text, " +
                "remindDate text unique);";
        db.execSQL(sql);

        SimpleDateFormat myDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String strTodayDate = myDateFormatter.format(new Date(System.currentTimeMillis()));
        String strTomorrowDate = myDateFormatter.format(new Date(System.currentTimeMillis()+1000*60*60*24));
        String strAfterTomorrowDate = myDateFormatter.format(new Date(System.currentTimeMillis()+1000*60*60*24*2));
        String strAfter30DaysDate = myDateFormatter.format(new Date(System.currentTimeMillis()+1000*60*60*24*3));

        String sql_insert="insert into tbNoteItem(noteItemTitle,noteItemText,remindDate) " +
                "values ('!','The good weather puts me into a good mood.','"+strTodayDate+"')";
        db.execSQL(sql_insert);
        sql_insert="insert into tbNoteItem(noteItemTitle,noteItemText,remindDate) " +
                "values ('NBC News','The brothers from Liberty Township, near Cincinnati ','"+strTomorrowDate+"')";
        db.execSQL(sql_insert);
        sql_insert="insert into tbNoteItem(noteItemTitle,noteItemText,remindDate) " +
                "values ('The Celtics will roll on!','The Cavaliers had only a two-point','"+strAfterTomorrowDate+"')";
        db.execSQL(sql_insert);
        sql_insert="insert into tbNoteItem(noteItemTitle,noteItemText,remindDate) " +
                "values ('Classes started','Classes have already started, so I have a lot to do.','"+strAfter30DaysDate+"')";
        db.execSQL(sql_insert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
