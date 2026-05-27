package com.example.medhelp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medhelp.Activity.MainActivity;
import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.databinding.DataBindingUtil;
import com.example.medhelp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static  final  String  TAG = "LoginActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        getSupportActionBar().setTitle("Login");


        progressBar = binding.progressBar;
        authProfile = FirebaseAuth.getInstance();

        binding.buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "You can reset your password now!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
        binding.imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        binding.imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
//                if password is visible then hide it
                    binding.editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                change icon
                    binding.imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                } else{
                    binding.editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }

        });


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = binding.editTextLoginEmail.getText().toString();
                String textPwd = binding.editTextLoginPwd.getText().toString();

                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this, "Pleas enter your email", Toast.LENGTH_SHORT).show();
                    binding.editTextLoginEmail.setError("Email is required");
                    binding.editTextLoginEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginActivity.this, "Pleas re-enter your email", Toast.LENGTH_SHORT).show();
                    binding.editTextLoginEmail.setError("Valid Email is required");
                    binding.editTextLoginEmail.requestFocus();

                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(LoginActivity.this, "Pleas enter your password", Toast.LENGTH_SHORT).show();
                    binding.editTextLoginPwd.setError("Valid Email is required");
                    binding.editTextLoginPwd.requestFocus();

                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPwd);
                }
            }
        });

    }

    private void loginUser(String email, String pwd) {
        progressBar.setVisibility(View.VISIBLE);
        authProfile.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();

//                            get instance of the current user
                            FirebaseUser firebaseUser = authProfile.getCurrentUser();

//                            check if the email is verified before user can access their profile
                            if (firebaseUser.isEmailVerified()){
                                Toast.makeText(LoginActivity.this, "You are logged in now ", Toast.LENGTH_SHORT).show();

                                // open user profile

//            Start the UserProfileActivity
                                startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
                                finish();
                            } else {
                                firebaseUser.sendEmailVerification();
                                authProfile.signOut();
                                showAlertDialog(); // show alert dialog here
                            }
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginActivity.this, "Invalid credentials. Kindly, check and re-enter.", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidUserException e) {
                                binding.editTextLoginEmail.setError("User does not exist or no longer valid. Please register again.");
                                binding.editTextLoginEmail.requestFocus();
                            } catch (FirebaseAuthException e) {
                                Log.e(TAG, "User already exists with this email address. Please login with another email.");
                                Toast.makeText(LoginActivity.this, "User already exists with this email address. Please login with another email.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void showAlertDialog() {
        //        setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Pleas verify your email now. You can not login without email verification.");

//        open email apps if user cliks continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN) ;
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
//        create the AlerDialog
        AlertDialog alertDialog = builder.create();


//        show the AlerDialog
        alertDialog.show();
    }
    
    @Override
    protected void onStart(){
        super.onStart();
        if (authProfile.getCurrentUser() != null){
            Toast.makeText(this, "Already Logged In!", Toast.LENGTH_SHORT).show();

//            Start the UserProfileActivity
            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            finish();

        }else {
            Toast.makeText(LoginActivity.this, "You can login now!", Toast.LENGTH_SHORT).show();
        }
    }



}