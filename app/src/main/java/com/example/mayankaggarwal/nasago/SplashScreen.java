package com.example.mayankaggarwal.nasago;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    private final Handler waitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
        waitHandler.postDelayed(waitCallback, 2000);
    }

    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {
//            if(Prefs.getPrefs("loggedIn",SplashScreen.this).equals("true")){
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
//            }else{
//                Intent intent = new Intent(SplashScreen.this, SplashSlider.class);
//                startActivity(intent);
//                finish();
//            }
        }
    };

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }
}
