package com.example.medhelp.Auth;

import static java.util.regex.Pattern.matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.medhelp.R;
//import com.example.medhelp.databinding.ActivityForgotPasswordBinding;
import com.example.medhelp.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {


    ActivityForgotPasswordBinding binding;
    private FirebaseAuth authProfile;
    private  final static String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.buttonPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextPasswordResetEmail.getText().toString();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(ForgotPasswordActivity.this, "Pleas enter your registered email", Toast.LENGTH_SHORT).show();
                    binding.editTextPasswordResetEmail.setError("Email is required");
                    binding.editTextPasswordResetEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Pleas enter valid email", Toast.LENGTH_SHORT).show();
                    binding.editTextPasswordResetEmail.setError("Valid email is required");
                    binding.editTextPasswordResetEmail.requestFocus();
                }else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }
            }
        });
    }

    private void resetPassword(String email) {
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Pleas check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgotPasswordActivity.this, AuthenticationActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        binding.editTextPasswordResetEmail.setError("user does not exists or is no longer valid. pleas register again.");
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

}









//                 else{
//                    try {
//                        throw task.getException();
//                    } catch (FirebaseAuthException e) {
//                        if (e instanceof FirebaseAuthInvalidUserException) {
//                            binding.editTextPasswordResetEmail.setError("user does not exists or is no longer valid. pleas register again.");
//                        } else {
//                            Log.e(TAG, e.getMessage());
//                            Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, e.getMessage());
//                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    try {
//                        throw task.getException();
//                    }catch (FirebaseAuthException e){
//                        if (e instanceof  FirebaseAuthInvalidUserException){
//                            binding.editTextPasswordResetEmail.setError("user does not exists or is no longer valid. pleas register again.");
//                        }else {
//                            Log.e(TAG, e.getMessage());
//                            Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }catch (Exception e){
//                        Log.e(TAG, e.getMessage());
//                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
