package com.example.medhelp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityUpdateProfileBinding;
import com.example.medhelp.databinding.ActivityUploadProfilePicBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UpdateProfileActivity extends AppCompatActivity {

    ActivityUpdateProfileBinding binding;
    private FirebaseAuth authProfile;
    RadioButton radioButtonGenderSelected;
    private String textFullName, textDoB, textGender, textMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Update Profile Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        authProfile = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        
//        show profile data
        showProfile(firebaseUser);

//        upload profile pic
        binding.buttonUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfileActivity.this, UploadProfilePicActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        update email
        binding.buttonUpdateProfileEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfileActivity.this, UpdateEmailActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.editTextUpdateProfileDob.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {
//                final Calendar calendar = Calendar.getInstance();
                String  textSADoB [] = textDoB.split("/");

                int day = Integer.parseInt(textSADoB[0]);
                int month = Integer.parseInt(textSADoB[1])-1;
                int year = Integer.parseInt(textSADoB[2]);
                DatePickerDialog picker = new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.editTextUpdateProfileDob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

//        update profile
        binding. buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
                
            }
        });






    }

    private void updateProfile(FirebaseUser firebaseUser) {
        //                validate mobile number using matcher and pattern (reguleer expression)
        String mobileRegex = "[6-9][0-9]{9}";  // first no can be {6,8,9} and rest 9 nos can be any no.
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(textMobile);

        if (TextUtils.isEmpty((textFullName))) {
            Toast.makeText(UpdateProfileActivity.this, "Pleas Enter Your Full Name", Toast.LENGTH_SHORT).show();
            binding.editTextUpdateProfileName.setError("Full Name Is Required");
            binding.editTextUpdateProfileName.requestFocus();
        } else if (TextUtils.isEmpty((textDoB))) {
            Toast.makeText(UpdateProfileActivity.this, "Pleas Enter Your date of birth", Toast.LENGTH_SHORT).show();
            binding.editTextUpdateProfileDob.setError("Date of birth Is Required");
            binding.editTextUpdateProfileDob.requestFocus();
        } else if (TextUtils.isEmpty(radioButtonGenderSelected.getText())) {
            Toast.makeText(UpdateProfileActivity.this, "Pleas Select You Gender", Toast.LENGTH_SHORT).show();
            radioButtonGenderSelected.setError("Gender is Required");
            radioButtonGenderSelected.requestFocus();
        } else if (TextUtils.isEmpty((textMobile))) {
            Toast.makeText(UpdateProfileActivity.this, "Pleas Enter Your mobile no:", Toast.LENGTH_SHORT).show();
            binding.editTextUpdateProfileMobile.setError("Mobile no is Required");
            binding.editTextUpdateProfileMobile.requestFocus();
        } else if (textMobile.length() != 10) {
            Toast.makeText(UpdateProfileActivity.this, "Pleas re-Enter Your mobile no:", Toast.LENGTH_SHORT).show();
            binding.editTextUpdateProfileMobile.setError("Mobile no is should be 10 digit");
            binding.editTextUpdateProfileMobile.requestFocus();

        } else if (!mobileMatcher.find()){
            Toast.makeText(UpdateProfileActivity.this, "Pleas re-Enter Your mobile no:", Toast.LENGTH_SHORT).show();
            binding.editTextUpdateProfileMobile.setError("Mobile no is not valid");
            binding.editTextUpdateProfileMobile.requestFocus();

        }  else {
            textGender = radioButtonGenderSelected.getText().toString();
            textFullName = binding.editTextUpdateProfileName.getText().toString();
            textDoB = binding.editTextUpdateProfileDob.getText().toString();
            textMobile = binding.editTextUpdateProfileMobile.getText().toString();

            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDoB, textGender,textMobile);
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
            String userID = firebaseUser.getUid();
            binding.progressBar.setVisibility(View.VISIBLE);

            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
//                        Setting new Display name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(UpdateProfileActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();

//                        stop user from retuning to Update ProfileActivity on pressing back button and close activity
                        Intent intent = new Intent(UpdateProfileActivity.this,UserProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    binding.progressBar.setVisibility(View.GONE);

                }
            });
        }

    }


    //    fetch teh data from teh firebase and display
    private void showProfile(FirebaseUser firebaseUser) {
        String userIDofRegistered = firebaseUser.getUid();

//        Extracting user reference from database for "registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        binding.progressBar.setVisibility(View.VISIBLE);
        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null){
                    textFullName = firebaseUser.getDisplayName();
                    textDoB = readUserDetails.doB;
                    textGender = readUserDetails.gender;
                    textMobile = readUserDetails.mobile;
                    binding.editTextUpdateProfileName.setText(textFullName);
                    binding.editTextUpdateProfileDob.setText(textDoB);
                    binding.editTextUpdateProfileMobile.setText(textMobile);

//                    Show gender through Radio Button
                    if (textGender.equals("Male")){
                        radioButtonGenderSelected = binding.radioMale;
                    }else {
                        radioButtonGenderSelected = binding.radioFemale;
                    }
                    radioButtonGenderSelected.setChecked(true);
                }else {
                    Toast.makeText(UpdateProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
            NavUtils.navigateUpFromSameTask(UpdateProfileActivity.this);
        } else if (id == R.id.menu_refresh) {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(UpdateProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }  else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(UpdateProfileActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.menu_settings) {

            Toast.makeText(UpdateProfileActivity.this, "menu_settings", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(UpdateProfileActivity.this, ChangePasswordProfileActivity.class);
            startActivity(intent);
            finish();
        }
        /*else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(UserProfileActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        }*/else if (id == R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(UpdateProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfileActivity.this, AuthenticationActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(UpdateProfileActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);

    }
}