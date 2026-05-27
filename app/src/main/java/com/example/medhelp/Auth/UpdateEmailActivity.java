package com.example.medhelp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityUpdateEmailBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmailActivity extends AppCompatActivity {

    ActivityUpdateEmailBinding binding;

    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private String userOldEmail, userNewEmail, userPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Update Email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.buttonUpdateEmail.setEnabled(false);
        binding.editTextUpdateEamilNew.setEnabled(false);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

//        set old email Id on TextView
        userOldEmail = firebaseUser.getEmail();
        binding.textViewUpdateEmailOld.setText(userOldEmail);


        if (firebaseUser.equals("")){
            Toast.makeText(this, "Something went wrong! User's details not available.", Toast.LENGTH_SHORT).show();
        }else {
            reAuthenticate(firebaseUser);
        }




    }


//    reauthenticate user before updating email
    private void reAuthenticate(FirebaseUser firebaseUser) {
        binding.buttonAuthenticateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Obtain password for authentication
                userPwd = binding.editTextEmailVerifyPassword.getText().toString();
                                       if (TextUtils.isEmpty(userPwd)){
                    Toast.makeText(UpdateEmailActivity.this, "Password is needed to continue", Toast.LENGTH_SHORT).show();
                    binding.editTextEmailVerifyPassword.setError("Pleas enter your password for authenticate");
                    binding.editTextEmailVerifyPassword.requestFocus();
                }else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    AuthCredential credential = EmailAuthProvider.getCredential(userOldEmail,userPwd);

                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            binding.progressBar.setVisibility(View.GONE);

                            Toast.makeText(UpdateEmailActivity.this, "Password has been verified"+" You can update email now", Toast.LENGTH_SHORT).show();

//                            set TextView to show that user is authenticated
                            binding.textViewUpdateEmailAuthenticated.setText("you are authenticated. you can update your email now. ");

//                            disable EditText for passoword button to verfy user and enable edittext for new Email and

                            binding.editTextUpdateEamilNew.setEnabled(true);
                            binding.editTextEmailVerifyPassword.setEnabled(false);
                            binding.buttonAuthenticateUser.setEnabled(false);
                            binding.buttonUpdateEmail.setEnabled(true);



//                            change color of update email button
                            binding.buttonUpdateEmail.setBackgroundTintList(ContextCompat.getColorStateList(UpdateEmailActivity.this,R.color.button_tint_list));

                            binding.buttonUpdateEmail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    userNewEmail = binding.editTextUpdateEamilNew.getText().toString();
                                    if (TextUtils.isEmpty(userNewEmail)){
                                        Toast.makeText(UpdateEmailActivity.this, "new Email is Required", Toast.LENGTH_SHORT).show();
                                        binding.editTextUpdateEamilNew.setError("pleas enter new Email*");
                                        binding.editTextUpdateEamilNew.requestFocus();
                                    } else if (!Patterns.EMAIL_ADDRESS.matcher(userNewEmail).matches()) {
                                        Toast.makeText(UpdateEmailActivity.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
                                        binding.editTextUpdateEamilNew.setError("please provide valid email");
                                        binding.editTextUpdateEamilNew.requestFocus();
                                    } else if (userOldEmail.matches(userNewEmail)) {
                                        Toast.makeText(UpdateEmailActivity.this, "New email cannot be same as old Email", Toast.LENGTH_SHORT).show();
                                        binding.editTextUpdateEamilNew.setError("please proved new email");
                                        binding.editTextUpdateEamilNew.requestFocus();

                                    }else {
                                        binding.progressBar.setVisibility(View.VISIBLE);
                                        updateEmail(firebaseUser);
                                    }
                                }
                            });
                        }else {
                            try {
                                throw task.getException();
                            }catch (Exception e){
                                Toast.makeText(UpdateEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                        }
                    });
                }
            }
        });
    }

    private void updateEmail(FirebaseUser firebaseUser) {
        firebaseUser.updateEmail(userNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

//                    verify email
                    firebaseUser.sendEmailVerification();
                    Toast.makeText(UpdateEmailActivity.this, "Email has been updated. Pleas verify your new Email", Toast.LENGTH_SHORT).show();

                    Intent intent  = new Intent(UpdateEmailActivity.this,UserProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(UpdateEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                binding.progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(UpdateEmailActivity.this);
        } else if (id == R.id.menu_refresh) {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(UpdateEmailActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }  else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(UpdateEmailActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.menu_settings) {

            Toast.makeText(UpdateEmailActivity.this, "menu_settings", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(UpdateEmailActivity.this, ChangePasswordProfileActivity.class);
            startActivity(intent);
            finish();
        }
        /*else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(UserProfileActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        }*/else if (id == R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(UpdateEmailActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateEmailActivity.this, AuthenticationActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(UpdateEmailActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);

    }
}