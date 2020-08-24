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
import android.widget.TextView;
import android.widget.Toast;

public class ModifyRecord extends AppCompatActivity {

    String user;

    EditText scoreET;
    EditText commentET;

    MyDatabase studentDb;

    TextView idTv;

    Student student;

    int savedIdExtra;
    int savedScoreExtra;
    String savedCommentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_record);

        //changes actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("                Assessment App");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Black)));

        user = getIntent().getStringExtra("username");

        commentET = (EditText) findViewById(R.id.modifycommentEditText);

        savedIdExtra = getIntent().getIntExtra("id",0);
        idTv= (TextView) findViewById(R.id.studentIDTextView);
        if(savedIdExtra != 0) {
            idTv.setText("Student Id: " + savedIdExtra);
        }

        savedScoreExtra = getIntent().getIntExtra("score", 12);
        scoreET = (EditText) findViewById(R.id.scoreEditText);
        if(savedScoreExtra < 11) {
            scoreET.setText("  " +savedScoreExtra);
        }

        savedCommentExtra = getIntent().getStringExtra("comment");
        if(savedCommentExtra != null) {
            commentET.setText(savedCommentExtra);
            student = new Student(savedIdExtra, savedScoreExtra, savedCommentExtra);
        }

        studentDb = MyDatabase.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void update(View view) {

        String stringNewScore = scoreET.getText().toString();
        int newScore = Integer.parseInt(stringNewScore.trim());
        String newComment = commentET.getText().toString();

        student = new Student(student.getId(), newScore, newComment);

        studentDb.studentDao().update(student);

        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("username", user);
        startActivity(intent);

    }
}
