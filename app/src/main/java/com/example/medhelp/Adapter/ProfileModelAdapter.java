package com.example.medhelp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medhelp.R;
import com.example.medhelp.Model.profile_fragment_model;

import java.util.ArrayList;

public class ProfileModelAdapter extends ArrayAdapter<profile_fragment_model> {
    private ArrayList<profile_fragment_model> profile_fragment_models_list;
    Context context;

    public ProfileModelAdapter(ArrayList<profile_fragment_model>profile_fragment_models_list,Context context){
        super(context,R.layout.profile_fragment_layout,profile_fragment_models_list);
        this.profile_fragment_models_list =profile_fragment_models_list;
        this.context =context;

    }
    private static class ProfileViewHolder{
        ImageView profile_list_image;
        TextView profile_list_text;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        profile_fragment_model profileFragmentModel = getItem(position);

        ProfileViewHolder profileViewHolder;
        final View result;

        if(convertView==null){
            profileViewHolder =new ProfileViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.profile_fragment_layout,parent,false);

            profileViewHolder.profile_list_image=(ImageView) convertView.findViewById(R.id.profile_list_image);
            profileViewHolder.profile_list_text=(TextView) convertView.findViewById(R.id.profile_list_text);

            result = convertView;
            convertView.setTag(profileViewHolder);


        }else{
            profileViewHolder = (ProfileViewHolder) convertView.getTag();
            result = convertView;
        }
        profileViewHolder.profile_list_text.setText(profileFragmentModel.getProfile_list_text());
        profileViewHolder.profile_list_image.setImageResource(profileFragmentModel.getProfile_list_image());

      return result;
    }
}
