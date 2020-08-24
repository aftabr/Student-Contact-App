package com.example.project_aftab_hasan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class NewRecord extends AppCompatActivity {

    Student student;
    MyDatabase studentDb;

    EditText studentIdET;
    EditText commentsET;
    SeekBar scoreSB;

    String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        //changes actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("                Assessment App");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Black)));

        studentIdET = (EditText) findViewById(R.id.studentIdEditText);
        commentsET = (EditText) findViewById(R.id.commentsEditText);
        scoreSB = (SeekBar) findViewById(R.id.scoreSeekBar);

        user = getIntent().getStringExtra("user");

        studentDb = MyDatabase.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public void save(View view) {

       int id = Integer.parseInt(studentIdET.getText().toString());
       int studentScore = scoreSB.getProgress();
       String comment = commentsET.getText().toString();


        student = new Student(id,studentScore,comment);

        studentDb.studentDao().insert(student);

        studentIdET.setText("");
        scoreSB.setProgress(5);
        commentsET.setText("");
        Toast.makeText(this,"ID: " + id, Toast.LENGTH_LONG).show();

    }

    public void back(View view) {
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }
}
