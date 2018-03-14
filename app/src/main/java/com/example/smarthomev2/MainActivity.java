package com.example.smarthomev2;

import android.app.AlarmManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button to  input user settings
        Button settingsButton = (Button) findViewById(R.id.info);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginPage.class);

                startActivity(loginIntent);
            }
        });

   /*     //Button to go to list
        Button startButton = (Button) findViewById(R.id.getStarted);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), getStartedActivity.class);
                startActivity(startIntent);
            }
        }); */

        //Button to go to DB list
        Button startButton = (Button) findViewById(R.id.getStarted);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), database_test.class);
                startActivity(startIntent);
            }
        });
    }
}