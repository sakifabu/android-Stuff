package com.example.sakif.calendarnotesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Date;

/**
 * Created by sakif on 5/15/2017.
 */

public class MyDBOpener extends SQLiteOpenHelper {
    public static String name = "Calendar.db";
    public static int version = 1;
    public MyDBOpener(Context context) {
        super(context, name, null, version);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table Note(_id integer primary key autoincrement, noteItemTitle text not null, noteItemText text, remindDate text unique);";
        db.execSQL(sql);

        SimpleDateFormat myDateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strTodayDate = myDateFormater.format(new Date(System.currentTimeMillis()));
        String strTomorrowdate = myDateFormater.format(new Date(System.currentTimeMillis()+1000*60*60*24));
        String strAfterTomorrowDate = myDateFormater.format(new Date(System.currentTimeMillis()+1000*60*60*24*2));
        String strAfter3DaysDate = myDateFormater.format(new Date(System.currentTimeMillis()+1000*60*60*24*3));
        String sql_insert = "insert into Note(noteIteamTitle,noteItemText,remindDate)"+"values ('Today is a nice Day!', The weather is good',"+strTodayDate +")";
        db.execSQL(sql_insert);
        sql_insert = "insert into Note(noteIteamTitle,noteItemText,remindDate)"+"values ('News', 'hello',"+strTomorrowdate +")";
        db.execSQL(sql_insert);
        sql_insert = "insert into Note(noteIteamTitle,noteItemText,remindDate)"+"values ('weather forecast', 'Rain',"+strAfterTomorrowDate +")";
        db.execSQL(sql_insert);
        sql_insert = "insert into Note(noteIteamTitle,noteItemText,remindDate)"+"values ('Departure', 'airport',"+strAfter3DaysDate +")";
        db.execSQL(sql_insert);
        Log.d("Notepad-MyDBOpenHelper", "The database was created "+strTomorrowdate);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
