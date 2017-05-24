package com.example.sakif.calendarnotesapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView Title;
    private TextView Note;
    private EditText EditTitle;
    private EditText EditContent;
    private int year,month,day;
    private MyDBOpener Calendardb;
    private CalendarView CalendarV;
    private SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SQLiteDatabase Reader;
    private String strSelectedDate;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Title =(TextView)findViewById(R.id.txttitle);
        Note = (TextView) findViewById(R.id.txtContent);
        EditTitle = (EditText)findViewById(R.id.edittxttitle);
        EditContent = (EditText)findViewById(R.id.edittxtContent);
        CalendarV = (CalendarView)findViewById(R.id.Calendar);
        Calendardb = new MyDBOpener(this);
        Reader=Calendardb.getReadableDatabase();
        strSelectedDate= DateFormatter.format(new Date(System.currentTimeMillis()));
        Calendar today=Calendar.getInstance(Locale.CHINA);
        year =today.get(Calendar.YEAR);
        month =today.get(Calendar.MONTH)+1;
        day = today.get(Calendar.DAY_OF_MONTH);
        Log.d("Notepad-Date of today",year+"-"+month+"-"+day);
        CalendarV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                year=year;
                month=month+1;
                day=dayOfMonth;
                strSelectedDate= year+"-"+month+"-"+day;
                try {
                    Date selectedDate=DateFormatter.parse(strSelectedDate);
                    strSelectedDate=DateFormatter.format(selectedDate);
                    Log.d("Notepad-Date selected",strSelectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Cursor result=Reader.query("tbNoteItem",new String[]{
                                "_id","NoteItemTitle","NoteItemText","remindDate",},
                        "remindDate=?",new String[]{strSelectedDate},null,null,null,null);
                if (result.getCount() != 0 ){
                    result.moveToFirst();
                    Title.setText(result.getString (1));
                    Note.setText(result.getString(2));
                }else {
                    Title.setText(R.string.nothing);
                    Note.setText(R.string.nothing);
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Cursor result=Reader.query("tbNoteItem",new String[]{
                        "_id","noteItemTitle","noteItemText","remindDate"},
                "remindDate=?",new String[]{strSelectedDate},null,null,null,null);
        if (result.getCount() != 0 ){
            result.moveToFirst();
            Title.setText(result.getString (1));
            Note.setText(result.getString(2));
        }else{
            Title.setText(R.string.nothing);
            Note.setText(R.string.nothing);
        }

    }

}