package com.example.databaseaccess;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolderClass> {

    ArrayList<Image> imageArrayList;


    public RecyclerViewAdapter(ArrayList<Image> imageArrayList) {
        this.imageArrayList = imageArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderClass holder, int position) {
        Image image = imageArrayList.get(position);
        holder.imageNameTV.setText(image.getImageName());
        holder.imageIV.setImageBitmap(image.getImage());
    }

    @Override
    public int getItemCount() {
        return  imageArrayList.size();
    }

    public static class RecyclerViewHolderClass extends RecyclerView.ViewHolder {

        TextView imageNameTV;
        ImageView imageIV;

        public RecyclerViewHolderClass(@NonNull View itemView) {
            super(itemView);

            imageNameTV =  itemView.findViewById(R.id.row_image_detailsTV);
            imageIV = itemView.findViewById(R.id.row_imageIV);

        }
    }

}
