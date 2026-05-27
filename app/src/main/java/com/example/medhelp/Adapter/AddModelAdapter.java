package com.example.medhelp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelp.R;
import com.example.medhelp.Model.add_model_home_fragment;

import java.util.List;

public class AddModelAdapter extends RecyclerView.Adapter<AddModelAdapter.AddModelViewHolder> {

    private List<add_model_home_fragment> addList;
    public  AddModelAdapter(List<add_model_home_fragment> addList){
        this.addList= addList;
    }
    @NonNull
    @Override
    public AddModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_recycle_2,parent,false);
        return new AddModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddModelViewHolder holder, int position) {

        holder.addImage.setImageResource(addList.get(position).getAddImage());

    }

    @Override
    public int getItemCount() {
        return addList.size();
    }

    public class AddModelViewHolder extends RecyclerView.ViewHolder{
        private ImageView addImage;

    public AddModelViewHolder(@NonNull View itemView){
        super(itemView);

        addImage =itemView.findViewById(R.id.addImage);
    }

}


}
