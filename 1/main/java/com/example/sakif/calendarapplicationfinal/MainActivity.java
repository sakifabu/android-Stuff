package com.example.sakif.calendarapplicationfinal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CalendarView myCalendar;
    private TextView Title,content;
    private int year, month, day;
    private MyDBOpenHelper dbOpenHelper;
    private SimpleDateFormat myDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SQLiteDatabase dbRead;
    private String DateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbOpenHelper=new MyDBOpenHelper(this);
        myCalendar=(CalendarView)findViewById(R.id.calendarView);
        Title =(TextView)findViewById(R.id.tvRemindTitle);
        content=(TextView)findViewById(R.id.tvRemindText);
        dbRead=dbOpenHelper.getReadableDatabase();
        DateSelected = myDateFormatter.format(new Date(System.currentTimeMillis()));//获取当前时间
        Calendar today=Calendar.getInstance(Locale.CHINA);
        year =today.get(Calendar.YEAR);
        month =today.get(Calendar.MONTH)+1;
        day = today.get(Calendar.DAY_OF_MONTH);
        Log.d("Notepad-Date of today", year +"-"+ month +"-"+ day);
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                MainActivity.this.year =year;
                MainActivity.this.month =month+1;
                day =dayOfMonth;
                DateSelected = MainActivity.this.year +"-"+ MainActivity.this.month +"-"+ day;
                try {
                    Date selectedDate=myDateFormatter.parse(DateSelected);
                    DateSelected =myDateFormatter.format(selectedDate);
                    Log.d("Notepad-Date selected", DateSelected);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Cursor result=dbRead.query("tbNoteItem",new String[]{
                                "_id","NoteItemTitle","NoteItemText","remindDate",},
                        "remindDate=?",new String[]{DateSelected},null,null,null,null);
                if (result.getCount() != 0 ){
                    result.moveToFirst();
                    Title.setText(result.getString (1));
                    content.setText(result.getString(2));
                }else {
                    Title.setText(R.string.nothing);
                    content.setText(R.string.nothing);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor result=dbRead.query("tbNoteItem",new String[]{
                        "_id","noteItemTitle","noteItemText","remindDate"},
                "remindDate=?",new String[]{DateSelected},null,null,null,null);
        if (result.getCount() != 0 ){
            result.moveToFirst();
            Title.setText(result.getString (1));
            content.setText(result.getString(2));
        }else{
            Title.setText(R.string.nothing);
            content.setText(R.string.nothing);
        }

    }

}
