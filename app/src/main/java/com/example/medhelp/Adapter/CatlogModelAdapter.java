package com.example.medhelp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelp.Activity.orderMedicineOnClickActivity;
import com.example.medhelp.R;
import com.example.medhelp.Model.catlog_model_home_fragment;

import java.util.List;

public class CatlogModelAdapter extends RecyclerView.Adapter<CatlogModelAdapter.CatlogModelViewHolder> {
    private List<catlog_model_home_fragment> catlogList;
    private Context context;

    public  CatlogModelAdapter(List<catlog_model_home_fragment> catlogList){
        this.catlogList= catlogList;
        this.context = context;
    }

    @NonNull
    @Override
    public CatlogModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catlog, parent,false);
      return new CatlogModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatlogModelViewHolder holder, int position) {
        holder.catlogButton.setImageResource(catlogList.get(position).getCatImage());
        holder.catlogTextView.setText(catlogList.get(position).getCatName());
        // Set a click listener for the "Order Medicine" item
        if (position == 0) { // Assuming "Order Medicine" is the first item
            holder.catlogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open the new activity when "Order Medicine" is clicked
                    Intent intent = new Intent(view.getContext(), orderMedicineOnClickActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
        if (position == 0) { // Assuming "Order Medicine" is the first item
            holder.catlogTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open the new activity when "Order Medicine" is clicked
                    Intent intent = new Intent(view.getContext(), orderMedicineOnClickActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return catlogList.size();
    }

    public class CatlogModelViewHolder extends RecyclerView.ViewHolder{

        private TextView catlogTextView;
        private ImageButton catlogButton;
        public CatlogModelViewHolder(@NonNull View itemView){
            super(itemView);

            catlogTextView = itemView.findViewById(R.id.catlogTextView);
            catlogButton = itemView.findViewById(R.id.catlogButton);
        }

    }
}
