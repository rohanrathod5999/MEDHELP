package com.example.medhelp.Activity;



import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.medhelp.MedProduct;
import com.example.medhelp.orderMedicineOnClickActivityAdapter;
import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityOrderMedicineOnClickBinding;
import com.example.medhelp.viewmodel.ordermedicineViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class orderMedicineOnClickActivity extends AppCompatActivity {
    private ArrayList<MedProduct> medProducts;
    private RecyclerView OrdMedRecycler;
    private orderMedicineOnClickActivityAdapter orderMedicineOnClickActivityAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ActivityOrderMedicineOnClickBinding binding;

    private ordermedicineViewModel ordermedicineViewModel;
    private MaterialSearchBar searchBar;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_medicine_on_click);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_order_medicine_on_click
        );

        ordermedicineViewModel = new ViewModelProvider(this)
                .get(ordermedicineViewModel.class);
        getNewMedProduct();

        swipeRefreshLayout = binding.swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.common);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewMedProduct();
            }
        });


    }

    private void getNewMedProduct() {
      ordermedicineViewModel.getAllMedProduct().observe(this, new Observer<List<MedProduct>>() {
          @Override
          public void onChanged(List<MedProduct> medProductsFromLiveData) {

              medProducts = (ArrayList<MedProduct>) medProductsFromLiveData;
              displayMedProductsInRecyclerView();

              
          }
      });
    }

    private void displayMedProductsInRecyclerView() {
        OrdMedRecycler = binding.OrdMedRecycler;
        orderMedicineOnClickActivityAdapter = new orderMedicineOnClickActivityAdapter(this,medProducts);
        OrdMedRecycler.setItemAnimator(new DefaultItemAnimator());
        OrdMedRecycler.setAdapter(orderMedicineOnClickActivityAdapter);
        OrdMedRecycler.setLayoutManager(new GridLayoutManager(this,2));
        orderMedicineOnClickActivityAdapter.notifyDataSetChanged();
    }

}