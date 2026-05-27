package com.example.medhelp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelp.R;
import com.example.medhelp.Model.shop_by_category_model_home_fragment;

import java.util.List;

public class ShopByCategoryModelAdapter extends RecyclerView.Adapter<ShopByCategoryModelAdapter.ShopByCategoryViewHolder> {

    private List<shop_by_category_model_home_fragment> shopByCategoryModelHomeFragmentList;
    public ShopByCategoryModelAdapter(List<shop_by_category_model_home_fragment> shopByCategoryModelHomeFragmentList){
        this.shopByCategoryModelHomeFragmentList= shopByCategoryModelHomeFragmentList;
    }


    @NonNull
    @Override
    public ShopByCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_by_category,parent,false);
      return new ShopByCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopByCategoryViewHolder holder, int position) {

        holder.shop_by_categoryImageBtn.setImageResource(shopByCategoryModelHomeFragmentList.get(position).getCategoryProductImage());
        holder.shop_by_categoryTextView.setText(shopByCategoryModelHomeFragmentList.get(position).getCategoryProductName());


    }

    @Override
    public int getItemCount() {
        return shopByCategoryModelHomeFragmentList.size();
    }

    public class ShopByCategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView   shop_by_categoryTextView;
        private ImageButton shop_by_categoryImageBtn;

        public ShopByCategoryViewHolder(@NonNull View itemView) {
            super(itemView);


            shop_by_categoryImageBtn = itemView.findViewById(R.id.shop_by_categoryImageBtn);
            shop_by_categoryTextView = itemView.findViewById(R.id.shop_by_categoryTextView);

        }
    }
}
