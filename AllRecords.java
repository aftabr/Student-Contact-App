package com.example.project_aftab_hasan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AllRecords extends AppCompatActivity {

    MyDatabase studentDb;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_records);

        //changes actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("                Assessment App");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Black)));

        user = getIntent().getStringExtra("user");

        studentDb = MyDatabase.getInstance(this);

        final List<Student> students = studentDb.studentDao().getAll();
        ListView listView = findViewById(R.id.recordList);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , students);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllRecords.this, ModifyRecord.class);

                intent.putExtra("username", user);

                intent.putExtra("id", students.get(position).getId());
                intent.putExtra("score", students.get(position).getScore());
                intent.putExtra("comment", students.get(position).getComment());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public void main(View view) {
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }
}
