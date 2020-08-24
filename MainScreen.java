package com.example.project_aftab_hasan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {

    TextView welcome;
    RadioGroup optionRG;
    Button submitB;
    Intent intent;
    int choice;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //changes actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("                Assessment App");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Black)));

        optionRG = (RadioGroup) findViewById(R.id.optionRadioGroup);
        submitB = (Button) findViewById(R.id.submitButton);

        user = getIntent().getStringExtra("username");
        welcome = (TextView) findViewById(R.id.welcomeUserTextView);
        welcome.setText("Hi, " + user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public void submit(View view) {

        optionRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.newRecordRadioButton:
                        choice = 1;
                        break;
                    case R.id.prevRadioButton:
                        choice = 2;
                        break;
                    case R.id.logoutRadioButton:
                        choice = 3;
                        break;
                    default:
                }
            }
        });

        switch (choice) {
            case 1:
                Toast.makeText(this, " new record", Toast.LENGTH_LONG).show();
                intent = new Intent(this, NewRecord.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case 2:
                Toast.makeText(this, " all records", Toast.LENGTH_LONG).show();
                intent = new Intent(this, AllRecords.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case 3:
                Toast.makeText(this, "logout", Toast.LENGTH_LONG).show();
                intent = new Intent(this, MainActivity.class);
                boolean clear = true;
                intent.putExtra("clear", clear);
                startActivity(intent);
                break;
        }

    }

}
