package com.example.medhelp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelp.Activity.ProductDetailedActivity;
import com.example.medhelp.databinding.MedicineProductMedicineOnClickActivityBinding;
//import com.example.medhelp.databinding.MedicineProductMedicineOnClickActivityBinding;

import java.util.ArrayList;

public class orderMedicineOnClickActivityAdapter extends RecyclerView.Adapter<orderMedicineOnClickActivityAdapter.MedProductViewHolder> {
    private Context context;
    private ArrayList<MedProduct> medProductArrayList;

    public orderMedicineOnClickActivityAdapter(Context context, ArrayList<MedProduct> medProductArrayList) {
        this.context = context;
        this.medProductArrayList = medProductArrayList;
    }

    @NonNull
    @Override
    public MedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicineProductMedicineOnClickActivityBinding binding = DataBindingUtil
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.medicine_product_medicine_on_click_activity,
                        parent,
                        false


                );
        return new MedProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedProductViewHolder holder, int position) {

        MedProduct medProduct = medProductArrayList.get(position);
        holder.medicineProductMedicineOnClickActivityBinding.setMedicineProduct(medProduct);

    }



    @Override
    public int getItemCount() {
        return medProductArrayList.size();
    }

    class MedProductViewHolder extends RecyclerView.ViewHolder{
        private MedicineProductMedicineOnClickActivityBinding medicineProductMedicineOnClickActivityBinding;


        public MedProductViewHolder( MedicineProductMedicineOnClickActivityBinding medicineProductMedicineOnClickActivityBinding) {
            super(medicineProductMedicineOnClickActivityBinding.getRoot());
            this.medicineProductMedicineOnClickActivityBinding = medicineProductMedicineOnClickActivityBinding;
            medicineProductMedicineOnClickActivityBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    get the clicked product
                    MedProduct clickedProduct = medProductArrayList.get(getAdapterPosition());
//                    Start the Product Detailed Activity and pass relevant data

                    Intent intent = new Intent(context, ProductDetailedActivity.class);
                    intent.putExtra("product", clickedProduct);
                    context.startActivity(intent);



                }
            });
        }
    }

}
