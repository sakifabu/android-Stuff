package com.example.sakif.collegenetworkappabu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    String selectedDate;
    SQLiteDatabase dbRead;
    EditText title, text;
    private MyDBOpenHelper dbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        selectedDate =  i.getStringExtra("date");
        setContentView(R.layout.activity_second);
        dbOpenHelper = new MyDBOpenHelper(SecondActivity.this);
        dbRead = dbOpenHelper.getReadableDatabase();
        title = (EditText) findViewById(R.id.titleText);
        text = (EditText) findViewById(R.id.contentText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return true;
    }

    public void onAddAndBack(MenuItem item) {

        try {
            if(title.getText()!=null && text.getText()!=null)
            {
                String sql = "insert into tbNoteItem(noteItemTitle, noteItemText, remindDate) " +
                        "values ('" + title.getText() + "', '" + text.getText() + "' , '" + selectedDate + "')";
                dbRead.execSQL(sql);

                Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nextScreen);
            }
            else if (title.getText()!=null)
            {
                String sql = "insert into tbNoteItem(noteItemTitle, noteItemText, remindDate) " +
                        "values ('" + title.getText() + "', 'nothing special' , '" + selectedDate + "')";
                dbRead.execSQL(sql);
            }

        }
        catch (Exception e){

        }
    }

    public void onCancel(MenuItem item){
        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextScreen);
    }
}

