package com.example.project_aftab_hasan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    EditText userNameET;
    EditText passwordET;
    CheckBox keepLoginCB;

    String username;
    String password;

    boolean logout = false;

    static final String SHARED_PREFS = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //changes actionbar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("                Assessment App");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Black)));

        userNameET = (EditText) findViewById(R.id.usernameEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
        keepLoginCB = (CheckBox) findViewById(R.id.keepLoginCheckBox);

        username = userNameET.getText().toString();
        password = passwordET.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String n = sharedPreferences.getString("userName","");
        String o = sharedPreferences.getString("passWord", "");

        userNameET.setText(n);
        passwordET.setText(o);


        logout = getIntent().getBooleanExtra("clear", false);
        if(logout) {
            clear();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public void login(View view) {

        username = userNameET.getText().toString();
        password = passwordET.getText().toString();

        if (checkConnection()) {
            new NetTask().execute();
        }

        if (keepLoginCB.isChecked()) {

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please enter proper username and/or password", Toast.LENGTH_LONG).show();
                clear();
            }else{
                editor.putString("userName" , username);
                editor.putString("passWord" , password);
                editor.commit();
            }
        }

    }

    public void clear() {
        userNameET.setText("");
        passwordET.setText("");
    }

    private boolean checkConnection() {
        boolean result;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    private String getData( String myurl) {
        String data = "";

        try {
            URL urlObject = new URL(myurl);
            HttpURLConnection conn  = (HttpURLConnection) urlObject.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d("DEBUG_TAG", "The response is "+ response);

            InputStream is = conn.getInputStream();
            data = processData(is);

        } catch ( Exception e) {
            e.printStackTrace();
            Log.d("net","error");
        }

        return data;
    }

    private String processData(InputStream is) {

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        StringBuilder sb = new StringBuilder();
        String line = "";

        try {
            while (((line = br.readLine())!= null)) {
                Log.d("response", line);
                sb = sb.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("net", "error2");
        }

        String result = sb.toString();

        return result;
    }

    class NetTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = "https://mohameom.dev.fast.sheridanc.on.ca/users/verifyUserData.php?name=" + username + "&password=" + password;
            String result = getData(url);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                String login = object.getString("login");
                if(login.equals("valid")) {
                    gotoMain();
                }
                else {
                    clear();
                }
                Toast.makeText(MainActivity.this, "login is " + login, Toast.LENGTH_LONG).show();

            } catch (JSONException e) {

            }


        }
    }

    void gotoMain() {
        Toast.makeText(this, "Login successful for " + username, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainScreen.class);

        intent.putExtra("username", username);

        startActivity(intent);
    }

}
