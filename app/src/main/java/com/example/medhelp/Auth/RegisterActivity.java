package com.example.medhelp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.medhelp.Activity.MainActivity;
import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
   private  ActivityRegisterBinding binding;
   private DatePickerDialog picker;
  ProgressBar progressBar;
  private RadioButton  radioButtonRegisterGenderSelected;
  private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(RegisterActivity.this, "You can Register Now", Toast.LENGTH_LONG).show();
        binding.radioGroupRegisterGender.clearCheck();


//        setting up DatePicker on EditText
        binding.editTextRegisterDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.editTextRegisterDob.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                }, year,month,day);
                picker.show();
            }
        });
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = binding.radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);


//               obtain the entered data
                progressBar = findViewById(R.id.progressBar);
                String textFullName = binding.editTextRegisterFullName.getText().toString();
                String textEmail = binding.editTextRegisterEmail.getText().toString();
                String textDOB = binding.editTextRegisterDob.getText().toString();
                String textMobile = binding.editTextRegisterMobile.getText().toString();
                String textPwd = binding.editTextRegisterPassword.getText().toString();
                String textConfirmPwd = binding.editTextRegisterConfirmPassword.getText().toString();
                String textGender;

//                validate mobile number using matcher and pattern (reguleer expression)
                String mobileRegex = "[6-9][0-9]{9}";  // first no can be {6,8,9} and rest 9 nos can be any no.
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty((textFullName))) {
                    Toast.makeText(RegisterActivity.this, "Pleas Enter Your Full Name", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterFullName.setError("Full Name Is Required");
                    binding.editTextRegisterFullName.requestFocus();
                } else if (TextUtils.isEmpty((textEmail))) {
                    Toast.makeText(RegisterActivity.this, "Pleas Enter Your Email", Toast.LENGTH_SHORT).show();
//                    binding.editTextRegisterEmail.("Email is required");
                    binding.editTextRegisterEmail.setError("Email is required");
                    binding.editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Pleas Re-Enter Your Email", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterEmail.setError("Valid Email Is Required");
                    binding.editTextRegisterEmail.requestFocus();


                } else if (TextUtils.isEmpty((textDOB))) {
                    Toast.makeText(RegisterActivity.this, "Pleas Enter Your date of birth", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterDob.setError("Date of birth Is Required");
                    binding.editTextRegisterDob.requestFocus();
                } else if (binding.radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Pleas Select You Gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("Gender is Required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty((textMobile))) {
                    Toast.makeText(RegisterActivity.this, "Pleas Enter Your mobile no:", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterMobile.setError("Mobile no is Required");
                    binding.editTextRegisterMobile.requestFocus();
                } else if (textMobile.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Pleas re-Enter Your mobile no:", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterMobile.setError("Mobile no is should be 10 digit");
                    binding.editTextRegisterMobile.requestFocus();

                } else if (!mobileMatcher.find()){
                    Toast.makeText(RegisterActivity.this, "Pleas re-Enter Your mobile no:", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterMobile.setError("Mobile no is not valid");
                    binding.editTextRegisterMobile.requestFocus();

                } else if (textPwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password is required:", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterPassword.setError("password too weak");
                    binding.editTextRegisterPassword.requestFocus();

                } else if (TextUtils.isEmpty((textConfirmPwd))) {
                    Toast.makeText(RegisterActivity.this, "Pleas confirm your password", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterConfirmPassword.setError("password confirmation is required");
                    binding.editTextRegisterConfirmPassword.requestFocus();
                } else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Pleas enter same password", Toast.LENGTH_SHORT).show();
                    binding.editTextRegisterConfirmPassword.setError("password confirmation is required");
                    binding.editTextRegisterConfirmPassword.requestFocus();

//                    clear the entered pass
                    binding.editTextRegisterPassword.clearComposingText();
                    binding.editTextRegisterConfirmPassword.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    binding.progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textEmail, textDOB, textGender, textMobile, textPwd);
                }
            }
        });
        }


//        register user using the credentials given
    private void registerUser(String textFullName, String textEmail, String textDOB, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

//        create user profile
//        new OnCompleteListener<AuthResult>(){}
        auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                   FirebaseUser firebaseUser = auth.getCurrentUser();



//                   update display name of user
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);



//                   Enter user Data into the firebase realtie database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDOB,textGender,textMobile);

//                    Extracting user reference from database for "Registerd Users
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()){
                                //                            send verification email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterActivity.this, "User registered successfully. Pleas verify your email", Toast.LENGTH_SHORT).show();

//                    open user profile after successfully registerd
                                Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
////                                to close the register activity
                                finish();


                            }else {
                                Toast.makeText(RegisterActivity.this, "User registered failed. Pleas try again", Toast.LENGTH_SHORT).show();


                            }
                            progressBar.setVisibility(View.GONE);





                        }
                    });
//

                }else {
                    try {
                        throw  task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        binding.editTextRegisterPassword.setError("Your Password is To weak. Kindly use a mix of alphabets, number");
                        binding.editTextRegisterPassword.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        binding.editTextRegisterPassword.setError("User is already registered with the email. use another email.");
                        binding.editTextRegisterPassword.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        binding.editTextRegisterPassword.setError("User is already registered with this email. Use another email.");
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }
}