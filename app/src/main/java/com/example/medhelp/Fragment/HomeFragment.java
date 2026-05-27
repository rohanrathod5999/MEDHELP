package com.example.medhelp.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medhelp.Adapter.AddModelAdapter;
import com.example.medhelp.Adapter.CatlogModelAdapter;
import com.example.medhelp.Adapter.ShopByCategoryModelAdapter;
import com.example.medhelp.Model.add_model_home_fragment;
import com.example.medhelp.Model.catlog_model_home_fragment;
import com.example.medhelp.Model.shop_by_category_model_home_fragment;
import com.example.medhelp.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

//    HOME NAV ID FOR RECYLE AND OTHER VIEW

    private NestedScrollView nestedScrollView_home_frag;
    private RecyclerView add_recycleView;
    private RecyclerView catlog_recycleView;
    private RecyclerView shop_by_category_layout;

    private TextView shop_by_category_header;

//TOOLBAR SECTION ID
    Toolbar toolbar;
//    ImageButton imageButton;
     MaterialSearchBar searchView;
//com.mancj.materialsearchbar.MaterialSearchBar searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);




        //        nested scroll view
        nestedScrollView_home_frag = view.findViewById(R.id.nestedScrollView_home_frag);
        nestedScrollView_home_frag.setFillViewport(true);


//     addrecyclerView
        add_recycleView= view.findViewById(R.id.add_recycleView);
        add_recycleView.setHasFixedSize(true);
        add_recycleView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL,false));

        List<add_model_home_fragment>  addList = new ArrayList<>();


        //drwable upload section off add
        addList.add(new add_model_home_fragment(R.drawable.add1));
        addList.add(new add_model_home_fragment(R.drawable.add2));
        addList.add(new add_model_home_fragment(R.drawable.add3));



        AddModelAdapter addModelAdapter = new AddModelAdapter(addList);
        add_recycleView.setAdapter(addModelAdapter);


//        ---------------------------------------------------------------------------------------------------------------------
//        catlogRecycle View
        catlog_recycleView=view.findViewById(R.id.catlog_recycleView);
        catlog_recycleView.setHasFixedSize(true);
        catlog_recycleView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        List<catlog_model_home_fragment> catlogList =new ArrayList<>();

        //drwable section here

        catlogList.add(new catlog_model_home_fragment(R.drawable.ordermedicine,"Order Medicine"));
        catlogList.add(new catlog_model_home_fragment(R.drawable.labtests30off,"Book Lab Tests"));
        catlogList.add(new catlog_model_home_fragment(R.drawable.allproduct,"Healthcare"));
        catlogList.add(new catlog_model_home_fragment(R.drawable.topproduct,"Top Brand"));
        catlogList.add(new catlog_model_home_fragment(R.drawable.consultdoctor,"Consult Doctor"));
        catlogList.add(new catlog_model_home_fragment(R.drawable.matrese,"Comfort Mattresses"));


//
        CatlogModelAdapter catlogModelAdapter = new CatlogModelAdapter(catlogList);
        catlog_recycleView.setAdapter(catlogModelAdapter);


//        -------------------------------------------------------------------------------------------------------------------------------------------------





//        shop by category recycler gridlayout
        shop_by_category_layout= view.findViewById(R.id.shop_by_category_layout);
        shop_by_category_layout.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        shop_by_category_layout.setLayoutManager(gridLayoutManager);

        List<shop_by_category_model_home_fragment> shopByCategoryModelHomeFragmentList =new ArrayList<>();


//    drwable section here
//        catlogList.add(new catlog_model_home_fragment(R.drawable.matrese,"Comfort Mattresses"));

       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.eldercare,"Elder Care"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.babycare,"Baby Care"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.fittnesscare,"Fitness Care"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.helthydrink,"Healthy Drinks"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.personalcare,"Personal Care"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.skincare,"Skin Care"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.sexualwellness,"Sexual Wellness"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.healthcaredevices,"Devices"));
       shopByCategoryModelHomeFragmentList.add(new shop_by_category_model_home_fragment(R.drawable.mattresses,"Mattresses"));

       ShopByCategoryModelAdapter shopByCategoryModelAdapter = new ShopByCategoryModelAdapter(shopByCategoryModelHomeFragmentList);
       shop_by_category_layout.setAdapter(shopByCategoryModelAdapter);






//        ------------------------=--------------------------------------------------------------------------------------------------------------------------------

//  shop by category header text view deifne section

        shop_by_category_header = view.findViewById(R.id.shop_by_category_header);


        //        TOOLBAR SECTION CODE
        toolbar = view.findViewById(R.id.home_toolbar);
//        imageButton = view.findViewById(R.id.cart_button);
        searchView = view.findViewById(R.id.search_view);


        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);


//        -----------------------------------------------------------------



        return view;
    }
}

