package com.example.smarthomev2;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create custom header
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // Create bottom tab layout
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(homeIntent);
                                break;
                            case R.id.navigation_settings:
                                Intent settingsIntent = new Intent(getApplicationContext(), settings.class);
                                startActivity(settingsIntent);
                                break;
                            case R.id.navigation_charts:
                                Intent chartsIntent = new Intent(getApplicationContext(), moreInfoActivity.class);
                                startActivity(chartsIntent);
                                break;
                        }
                        return true;
                    }
                });

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        // Place icon next to title name
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           // case R.id.action_menu:
             //   return true;
            case (R.id.action_login):
                Intent loginIntent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(loginIntent);
            default:
                // The user's action was not recognized return super statement.
                return super.onOptionsItemSelected(item);
        }
    }

}