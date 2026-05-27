package com.example.medhelp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medhelp.Adapter.ProfileModelAdapter;
import com.example.medhelp.Model.profile_fragment_model;
import com.example.medhelp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

//    profile list content id
    ListView profile_listView;
    ArrayList<profile_fragment_model> profile_fragment_modelArrayList;
    private static ProfileModelAdapter profileModelAdapter;

//    PROFILE TOOLBAR SECTIO ID
    Toolbar toolbar_profile;
    TextView profile_name_textView;
    TextView profile_info;
    TextView editTextView;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        profile list view code
        profile_listView = view.findViewById(R.id.profile_listView);
        profile_fragment_modelArrayList = new ArrayList<>();
//        profile list drwable section

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fetchAndDisplayUserData();



        profile_fragment_model order = new profile_fragment_model(R.drawable.profileorders,"Orders");
        profile_fragment_model savedForLater= new profile_fragment_model(R.drawable.savedforlater,"Saved for Later");
        profile_fragment_model notification = new profile_fragment_model(R.drawable.notfication,"Notification");
        profile_fragment_model wallet = new profile_fragment_model(R.drawable.wallet,"Wallet");
        profile_fragment_model needHelp = new profile_fragment_model(R.drawable.needhelp,"Need Help");
        profile_fragment_model prescriptionn = new profile_fragment_model(R.drawable.prescription,"Prescription");
        profile_fragment_model manageAddress = new profile_fragment_model(R.drawable.manageaddress,"manage Address");
        profile_fragment_model setRefundPreferences = new profile_fragment_model(R.drawable.setrefundpreference,"Set Refund Preferences");
        profile_fragment_model referAndEarn = new profile_fragment_model(R.drawable.profilereferandearn,"Refer And Earn");

        profile_fragment_modelArrayList.add(order);
        profile_fragment_modelArrayList.add(savedForLater);
        profile_fragment_modelArrayList.add(notification);
        profile_fragment_modelArrayList.add(wallet);
        profile_fragment_modelArrayList.add(needHelp);
        profile_fragment_modelArrayList.add(prescriptionn);
        profile_fragment_modelArrayList.add(manageAddress);
        profile_fragment_modelArrayList.add(setRefundPreferences);
        profile_fragment_modelArrayList.add(referAndEarn);

//        profile adapter
        profileModelAdapter = new ProfileModelAdapter(profile_fragment_modelArrayList, getContext());
        profile_listView.setAdapter(profileModelAdapter);

//       




//      PROFILE TOOLBAR SECTION CODE

        profile_name_textView = view.findViewById(R.id.profile_name_textView);
        profile_info =  view.findViewById(R.id.profile_info);
        editTextView = view.findViewById(R.id.editTextView);
        toolbar_profile = view.findViewById(R.id.toolbar_profile);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar_profile);
        return view;
    }

    private void fetchAndDisplayUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null){
            String userId = currentUser.getUid();
            mDatabase.child("Registerd Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        userName = snapshot.child("fullName").getValue(String.class);
                        profile_name_textView.setText(userName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}