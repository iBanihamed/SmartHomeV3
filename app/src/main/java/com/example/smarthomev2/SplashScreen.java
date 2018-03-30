package com.example.smarthomev2;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
    private static int SPLASH_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove AppName title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);
        // Startup loading screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loadingScreen = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(loadingScreen);
                finish();
            }
        },SPLASH_DELAY);
        //overridePendingTransition(); further testing for transitioning activities
    }
}
