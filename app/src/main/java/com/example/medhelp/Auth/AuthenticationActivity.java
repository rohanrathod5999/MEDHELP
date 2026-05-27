package com.example.medhelp.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.medhelp.Activity.MainActivity;
import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity {
    ActivityAuthenticationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        open login activity
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenticationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenticationActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        getSupportActionBar().setTitle("MedHelp");
//        / Prevent the user from going back to the splash screen
        onBackPressed();


    }

    @Override
    public void onBackPressed() {
//       // Do nothing to prevent the user from going back to the splash screen

    }
    public void startMainActivity(){
        Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}