package com.example.sakif.collegenetworkappabu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CalendarView myCalendar;
    private TextView tvRemindTitle, tvRemindText;
    private int yearSet, monthSet, daySet;
    private MyDBOpenHelper dbOpenHelper;
    private SimpleDateFormat myDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SQLiteDatabase dbRead;
    private String strSelectedDate;
    MenuItem check, add, remove;


    private static final String DATABASE_NAME = "CollegeConnect";
    private static final String TABLE_NETWORKS = "Networks";
    private static final String TABLE_FRIENDS = "Friends";
    private static final String TABLE_ACTIVITIES = "Activities";
    private static final String TABLE_PROFILE = "Profile";

    private static final String NETWORK_KEY_ID = "Nid";
    private static final String NETWORK_NAME = "NName";

    private static final String FRIENDS_KEY_ID = "Fid";
    private static final String FRIENDS_NAME = "FName";

    private static final String ACTIVITIES_KEY_ID = "Aid";
    private static final String ACTIVITIES_NAME = "AName";

    private static final String PROFILE_KEY_ID = "Pid";
    private static final String PROFILE_NAME = "PName";

    SQLiteDatabase db = null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridN = (GridView) findViewById(R.id.gridViewNetworks);
        gridN.setBackgroundColor(Color.LTGRAY);

        GridView gridA = (GridView) findViewById(R.id.gridViewActivities);
        gridA.setBackgroundColor(Color.LTGRAY);

        GridView gridF = (GridView) findViewById(R.id.gridViewFriends);
        gridF.setBackgroundColor(Color.LTGRAY);


        try{

            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            // for testing purposes
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NETWORKS );
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS );
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE );
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES );


            //creating tables
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NETWORKS  + " ( " + NETWORK_KEY_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + NETWORK_NAME + " VARCHAR);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FRIENDS  + " ( " + FRIENDS_KEY_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + FRIENDS_NAME + " VARCHAR);");


            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ACTIVITIES  + " ( " + ACTIVITIES_KEY_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACTIVITIES_NAME + " VARCHAR);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PROFILE  + " ( " + PROFILE_KEY_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_NAME + " VARCHAR);");


            //putting records
            db.execSQL("INSERT INTO " + TABLE_NETWORKS + " (" + NETWORK_NAME + ") VALUES ('PSU');" );
            db.execSQL("INSERT INTO " + TABLE_FRIENDS + " (" + FRIENDS_NAME + ") VALUES ('Everyone');" );
            db.execSQL("INSERT INTO " + TABLE_ACTIVITIES + " (" + ACTIVITIES_NAME + ") VALUES ('Being Awesome');" );
            db.execSQL("INSERT INTO " + TABLE_ACTIVITIES + "(" + ACTIVITIES_NAME+ ") VALUES ('Being Awesomer');" );
            db.execSQL("INSERT INTO " + TABLE_PROFILE + " (" + PROFILE_NAME + ") VALUES ('Sakif Abu');" );

            ContentValues cv = new ContentValues();

            cv.put(NETWORK_NAME, "PSU World Campus");

            db.insert(TABLE_NETWORKS, null, cv);

            ArrayList<String> list = new ArrayList<String>();
            ArrayList<String> list2 = new ArrayList<String>();
            ArrayList<String> list3 = new ArrayList<String>();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list2);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list3);


            Cursor cursor = db.rawQuery("Select * FROM " + TABLE_NETWORKS, null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                do {
                    Network net = new Network();
                    net.setNID(Integer.parseInt(cursor.getString(0)));
                    net.setNName(cursor.getString(1));
                    list.add(net.getNName());
                    gridN.setAdapter(adapter);

                } while (cursor.moveToNext());
            }

            cursor = db.rawQuery("Select * FROM " + TABLE_FRIENDS, null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                do {
                    list2.add(cursor.getString(1));
                    gridF.setAdapter(adapter2);
                } while (cursor.moveToNext());
            }


            cursor = db.rawQuery("Select * FROM " + TABLE_ACTIVITIES, null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                do {
                    list3.add(cursor.getString(1));
                    gridA.setAdapter(adapter3);
                } while (cursor.moveToNext());
            }



        }
        catch (Exception e )
        {
            Log.d("Error:", e.toString());
            Toast.makeText(getApplicationContext(), "Database Issue: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        finally {
            Log.d("Data written:"," ");
            db.close();
        }




        //calendar stuff

        dbOpenHelper = new MyDBOpenHelper(MainActivity.this);
        myCalendar = (CalendarView) findViewById(R.id.calendarView);
        tvRemindTitle = (TextView) findViewById(R.id.titleMain);
        tvRemindText = (TextView)findViewById(R.id.textMain);
        dbRead = dbOpenHelper.getReadableDatabase();
        strSelectedDate = myDateFormatter.format(new Date(System.currentTimeMillis()));
        Calendar today = Calendar.getInstance(Locale.US);
        yearSet = today.get(Calendar.YEAR);
        monthSet = today.get(Calendar.MONTH)+1;
        daySet = today.get(Calendar.DAY_OF_MONTH);
        // Log.d("Notepad-Date of today", yearSet +"-"+monthSet+"="+daySet);


        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                check.setVisible(true);
                add.setVisible(true);
                remove.setVisible(true);


                LinearLayout calContainer = (LinearLayout) findViewById(R.id.Banner);
                calContainer.setVisibility(View.INVISIBLE);
                ScrollView textView = (ScrollView) findViewById(R.id.infoLayout);
                textView.setVisibility(View.VISIBLE);

                yearSet=year;
                monthSet=month+1;
                daySet=dayOfMonth;
                strSelectedDate= yearSet+"-"+monthSet+"-"+daySet;
                try {
                    Date selectedDate=myDateFormatter.parse(strSelectedDate);
                    strSelectedDate=myDateFormatter.format(selectedDate);
                    Log.d("Notepad-Date selected",strSelectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Cursor result = dbRead.query("tbNoteItem", new String[]{
                                "_id", "NoteItemTitle", "NoteItemText", "remindDate",},
                        "remindDate=?", new String[]{strSelectedDate}, null, null, null, null);

                if (result.getCount() != 0) {
                    result.moveToFirst();
                    tvRemindTitle.setText(result.getString(1));
                    tvRemindText.setText(result.getString(2));
                }
                else
                {
                    tvRemindText.setText("Nothing special");
                    tvRemindTitle.setText("Nothing special");
                }

            }
        });




    }

    public void onClickNetworks(View view)
    {
        FrameLayout calContainer = (FrameLayout) findViewById(R.id.calFrame);
        calContainer.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerFri = (FrameLayout) findViewById(R.id.gridFrameFriends);
        gridContainerFri.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerAct = (FrameLayout) findViewById(R.id.gridFrameActivities);
        gridContainerAct.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerProf = (FrameLayout) findViewById(R.id.gridFrameProfile);
        gridContainerProf.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerNet = (FrameLayout) findViewById(R.id.gridFrameNetworks);
        gridContainerNet.setVisibility(View.VISIBLE);

    }

    public void onClickFriends(View view)
    {
        FrameLayout calContainer = (FrameLayout) findViewById(R.id.calFrame);
        calContainer.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerFri = (FrameLayout) findViewById(R.id.gridFrameFriends);
        gridContainerFri.setVisibility(View.VISIBLE);
        FrameLayout gridContainerAct = (FrameLayout) findViewById(R.id.gridFrameActivities);
        gridContainerAct.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerProf = (FrameLayout) findViewById(R.id.gridFrameProfile);
        gridContainerProf.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerNet = (FrameLayout) findViewById(R.id.gridFrameNetworks);
        gridContainerNet.setVisibility(View.INVISIBLE);
    }
    public void onClickActivities(View view)
    {
        FrameLayout calContainer = (FrameLayout) findViewById(R.id.calFrame);
        calContainer.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerFri = (FrameLayout) findViewById(R.id.gridFrameFriends);
        gridContainerFri.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerAct = (FrameLayout) findViewById(R.id.gridFrameActivities);
        gridContainerAct.setVisibility(View.VISIBLE);
        FrameLayout gridContainerProf = (FrameLayout) findViewById(R.id.gridFrameProfile);
        gridContainerProf.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerNet = (FrameLayout) findViewById(R.id.gridFrameNetworks);
        gridContainerNet.setVisibility(View.INVISIBLE);
    }

    public void onClickProfile(View view) {

        FrameLayout calContainer = (FrameLayout) findViewById(R.id.calFrame);
        calContainer.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerFri = (FrameLayout) findViewById(R.id.gridFrameFriends);
        gridContainerFri.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerAct = (FrameLayout) findViewById(R.id.gridFrameActivities);
        gridContainerAct.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerProf = (FrameLayout) findViewById(R.id.gridFrameProfile);
        gridContainerProf.setVisibility(View.VISIBLE);
        FrameLayout gridContainerNet = (FrameLayout) findViewById(R.id.gridFrameNetworks);
        gridContainerNet.setVisibility(View.INVISIBLE);
    }

    public void onPicClick(View view) {

        FrameLayout calContainer = (FrameLayout) findViewById(R.id.calFrame);
        calContainer.setVisibility(View.VISIBLE);
        FrameLayout gridContainerFri = (FrameLayout) findViewById(R.id.gridFrameFriends);
        gridContainerFri.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerAct = (FrameLayout) findViewById(R.id.gridFrameActivities);
        gridContainerAct.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerProf = (FrameLayout) findViewById(R.id.gridFrameProfile);
        gridContainerProf.setVisibility(View.INVISIBLE);
        FrameLayout gridContainerNet = (FrameLayout) findViewById(R.id.gridFrameNetworks);
        gridContainerNet.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        check = menu.getItem(0);
        add = menu.getItem(1);
        remove = menu.getItem(2);
        return true;
    }


    public void onRemoveClick(MenuItem item) {
        long date = myCalendar.getDate();
//        Log.d("myTag", String.valueOf(date));
//        // strSelectedDate = myDateFormatter.format(date);
//        Log.d("myTag", strSelectedDate );
//        //String sql = "insert into tbNoteItem(noteItemTitle, noteItemText, remindDate) "+
//        // "values ('', NULL , '"+strSelectedDate+"')";

        String sql = "DELETE FROM tbNoteItem WHERE remindDate = '"+strSelectedDate+"'";

        try {
            dbRead.execSQL(sql);

        }
        catch (Exception e)
        {
            Log.d("tagMistake", e.toString());
        }

        tvRemindTitle.setText("Nothing special");
        tvRemindText.setText("Nothing special");

    }

    public void onAddClick(MenuItem item) {
        Intent nextScreen = new Intent(getApplicationContext(), SecondActivity.class);
        nextScreen.putExtra("date", strSelectedDate);
        startActivity(nextScreen);
    }


    public void onCheckClick(MenuItem item) {
        LinearLayout picContainer = (LinearLayout) findViewById(R.id.Banner);
        picContainer.setVisibility(View.VISIBLE);
        ScrollView textView = (ScrollView) findViewById(R.id.infoLayout);
        textView.setVisibility(View.GONE);

        check.setVisible(false);
        add.setVisible(false);
        remove.setVisible(false);
    }
}
