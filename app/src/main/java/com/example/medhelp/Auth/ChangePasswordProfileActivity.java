package com.example.medhelp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityChangePasswordProfileBinding;
import com.example.medhelp.databinding.ActivityUpdateEmailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordProfileActivity extends AppCompatActivity {

    private FirebaseAuth authProfile;
    ActivityChangePasswordProfileBinding binding;
    private String userPwdCurr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//  Disable editText for new password , confirm new password and make change pwd button unclickable till user is autheticate
        binding.editTextChangePwdNew.setEnabled(false);
        binding.editTextChangePwdNewConfirm.setEnabled(false);
        binding.buttonChangePwd.setEnabled(false);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();


        if (firebaseUser.equals("")){
            Toast.makeText(ChangePasswordProfileActivity.this, "SomeThing Went wrong! User's details not available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordProfileActivity.this,UserProfileActivity.class);
            startActivity(intent);
            finish();
        }else {
            reAuthenticate(firebaseUser);
        }


    }

    private void reAuthenticate(FirebaseUser firebaseUser) {
        binding.buttonAuthenticateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwdCurr = binding.editTextChangePwdCurrent.getText().toString();

                if (TextUtils.isEmpty(userPwdCurr)){
                    Toast.makeText(ChangePasswordProfileActivity.this, "Password is needed", Toast.LENGTH_SHORT).show();
                    binding.editTextChangePwdCurrent.setError("Pleas enter your current password to authenticate");
                    binding.editTextChangePwdCurrent.requestFocus();

                }else {
                    binding.progressBar.setVisibility(View.VISIBLE);
//                    reAuthenticate user now
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),userPwdCurr);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                binding.progressBar.setVisibility(View.GONE);


//                                Disable edittext for current password Enabled Enable editText .Enable Edittext for new [assword and confirn new password

                                binding.editTextChangePwdCurrent.setEnabled(false);
                                binding.editTextChangePwdNew.setEnabled(true);
                                binding.editTextChangePwdNewConfirm.setEnabled(true);


//                                enabled change pwd button . disable authenticate button
                                binding.buttonAuthenticateUser.setEnabled(false);
                                binding.buttonChangePwd.setEnabled(true);

//                                set TexrView to show user is authenticated
                                binding.textViewChangePwdAuthenticated.setText("you are Authenticated/verified."+"You can chagne password now!");
                                Toast.makeText(ChangePasswordProfileActivity.this, "Password has been verified"+"Change password now", Toast.LENGTH_SHORT).show();

//                                update color of change password button
                                binding.buttonChangePwd.setBackgroundTintList(ContextCompat.getColorStateList(ChangePasswordProfileActivity.this,R.color.button_tint_list));



                                binding.buttonChangePwd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        changePwd(firebaseUser);
                                    }
                                });

                            }else {
                                try {
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(ChangePasswordProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            binding.progressBar.setVisibility(View.GONE);

                        }
                    });
                }
            }
        });
    }


    private void changePwd(FirebaseUser firebaseUser) {
        String userPwdNew = binding.editTextChangePwdNew.getText().toString();
        String userPwdConfirmNew = binding.editTextChangePwdNewConfirm.getText().toString();

        if (TextUtils.isEmpty(userPwdNew)){
            Toast.makeText(ChangePasswordProfileActivity.this, "New Password is needed", Toast.LENGTH_SHORT).show();
            binding.editTextChangePwdNew.setError("Please enter your new password");
            binding.editTextChangePwdNew.requestFocus();
        }else if(TextUtils.isEmpty(userPwdConfirmNew)){
            Toast.makeText(ChangePasswordProfileActivity.this, "Pleas confirm your new password", Toast.LENGTH_SHORT).show();
            binding.editTextChangePwdNewConfirm.setError("Please enter your new password");
            binding.editTextChangePwdNewConfirm.requestFocus();
        } else if (!userPwdNew.matches(userPwdConfirmNew)) {
            Toast.makeText(ChangePasswordProfileActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
            binding.editTextChangePwdNewConfirm.setError("Please re-enter your new password");
            binding.editTextChangePwdNewConfirm.requestFocus();

        } else if (userPwdCurr.matches(userPwdNew)) {
            Toast.makeText(ChangePasswordProfileActivity.this, "New Password", Toast.LENGTH_SHORT).show();
            binding.editTextChangePwdNew.setError("pleas Enter new password");
            binding.editTextChangePwdNew.requestFocus();
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(ChangePasswordProfileActivity.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(ChangePasswordProfileActivity.this, UserProfileActivity.class);
                       startActivity(intent);
                       finish();
                   }else {
                       try {
                           task.getException();
                       }catch (Exception e){
                           Toast.makeText(ChangePasswordProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
                   binding.progressBar.setVisibility(View.GONE);
                }
            });

        }
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
            NavUtils.navigateUpFromSameTask(ChangePasswordProfileActivity.this);
        } else if (id == R.id.menu_refresh) {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(ChangePasswordProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }  else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(ChangePasswordProfileActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.menu_settings) {

            Toast.makeText(ChangePasswordProfileActivity.this, "menu_settings", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(ChangePasswordProfileActivity.this, ChangePasswordProfileActivity.class);
            startActivity(intent);
            finish();
        }
        /*else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(UserProfileActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        }*/else if (id == R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(ChangePasswordProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordProfileActivity.this, AuthenticationActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(ChangePasswordProfileActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);

    }
}