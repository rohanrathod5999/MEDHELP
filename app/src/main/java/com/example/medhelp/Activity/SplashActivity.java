package com.example.medhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medhelp.Auth.AuthenticationActivity;
import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    TextView text1,text2;
    RelativeLayout relMain;
    Animation txtAnimation,layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_to_top);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        relMain = findViewById(R.id.relMain);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                relMain.setVisibility(View.VISIBLE);
                relMain.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        text1.setVisibility(View.VISIBLE);
                        text2.setVisibility(View.VISIBLE);

                        text2.setAnimation(txtAnimation);
                        text1.setAnimation(txtAnimation);

                    }
                },900);

            }
        },500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                finish();
            }
        },5500);


//        SharedPreferences preferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
//        if (preferences.getBoolean("isLoggedIn",false)){
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        },5500);





    }
}