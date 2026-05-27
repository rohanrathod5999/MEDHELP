package com.example.medhelp.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medhelp.Adapter.OfferModelAdapter;
import com.example.medhelp.Model.offer_fragment_model;
import com.example.medhelp.R;

import java.util.ArrayList;
import java.util.List;

public class OfferFragment extends Fragment {

// offer fragment defining layout
    RecyclerView cuppon_recyclerView;


    //    offer toolbar define id
    Toolbar offer_toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);

//        cuppon recycler view
        cuppon_recyclerView = view.findViewById(R.id.cuppon_recyclerView);
        cuppon_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cuppon_recyclerView.setLayoutManager(layoutManager);

        List<offer_fragment_model> offerFragmentModelList = new ArrayList<>();


//        drwable section

        offerFragmentModelList.add(new offer_fragment_model(R.drawable.medhelpoffer,"Get Flat 25% OFF on First 2 Medicine order","Cupon applicable on your medicine order above rs 1250","CODE:  WEL25"));
        offerFragmentModelList.add(new offer_fragment_model(R.drawable.paytmoffer,"Up to Rs.250 Cashback on payment view wallet","Valid once per user on transaction above Rs.800","CODE:   BIG123"));
        offerFragmentModelList.add(new offer_fragment_model(R.drawable.phonepayoffer,"Flat 10% off ","valid once per user on fist transaction via phonepay","CODE:   10off"));
        offerFragmentModelList.add(new offer_fragment_model(R.drawable.buy1get1,"BUY 1 GET 1 FREE On SkinCare Products","Buy 1 Himalaya Face wash and get 1 Free","CODE:  54514sd"));
//        offerFragmentModelList.add(new offer_fragment_model(R.drawable.,"","",""));



        OfferModelAdapter offerModelAdapter  = new OfferModelAdapter(offerFragmentModelList);
        cuppon_recyclerView.setAdapter(offerModelAdapter);



//        offer action bar toolbar
        offer_toolbar = view.findViewById(R.id.offer_toolbar);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(offer_toolbar);
//        to enable the option menu
        setHasOptionsMenu(true);
        return view;
    }

// tool bar menubar code
//    -------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.offer_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand( MenuItem menuItem) {
                Toast.makeText(getContext(), "Search is Expanded", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse( MenuItem menuItem) {
                Toast.makeText(getContext(), "Search is collapsed", Toast.LENGTH_SHORT).show();
                return true;

            }
        };


    }
// -------------------------------------------------------------------------------------------------------------------------------------------------
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        // Handle menu item clicks
//        switch (item.getItemId()) {
//
//            case R.id.offer_search:
//                // Handle Menu Item 1 click
//                return true;
//            case R.id.offer_cart:
//                // Handle Menu Item 2 click
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}

