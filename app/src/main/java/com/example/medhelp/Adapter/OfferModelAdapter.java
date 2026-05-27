package com.example.medhelp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelp.R;
import com.example.medhelp.Model.offer_fragment_model;

import java.util.List;

public class OfferModelAdapter extends RecyclerView.Adapter<OfferModelAdapter.OfferModelViewHolder> {

    private List<offer_fragment_model> offerFragmentModelList;
    public OfferModelAdapter(List<offer_fragment_model>offerFragmentModelList){
        this.offerFragmentModelList = offerFragmentModelList;
    }

    @NonNull
    @Override
    public OfferModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuppon_offer_fragment_layout,parent,false);

        return new OfferModelViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull OfferModelViewHolder holder, int position) {
        holder.cupoonImage.setImageResource(offerFragmentModelList.get(position).getCupoonImage());
        holder.cupponTextHeader.setText(offerFragmentModelList.get(position).getCupponTextHeader());
        holder.cupponDescription.setText(offerFragmentModelList.get(position).getCupponDescription());
        holder.cupponNumber.setText(offerFragmentModelList.get(position).getCupponNumber());




    }

    @Override
    public int getItemCount() {
        return offerFragmentModelList.size();
    }

    public class OfferModelViewHolder extends RecyclerView.ViewHolder{

        private ImageView cupoonImage;
        private TextView cupponTextHeader;
        private TextView cupponDescription;
        private TextView cupponNumber;


        public OfferModelViewHolder(@NonNull View itemView) {
            super(itemView);
            cupoonImage =itemView.findViewById(R.id.cupoonImage);
            cupponTextHeader =itemView.findViewById(R.id.cupponTextHeader);
            cupponDescription =itemView.findViewById(R.id.cupponDescription);
            cupponNumber = itemView.findViewById(R.id.cupponNumber);



        }
    }

}
