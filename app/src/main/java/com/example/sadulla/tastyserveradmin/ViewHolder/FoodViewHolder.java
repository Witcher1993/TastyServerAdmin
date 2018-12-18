package com.example.sadulla.tastyserveradmin.ViewHolder;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sadulla.tastyserveradmin.Common.Common;
import com.example.sadulla.tastyserveradmin.Interface.ItemClickListener;
import com.example.sadulla.tastyserveradmin.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

    public TextView foodName;
    public ImageView foodImage;

    private ItemClickListener itemClickListener;


    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        foodName = (TextView)itemView.findViewById(R.id.food_name);
        foodImage = (ImageView)itemView.findViewById(R.id.food_image);


        //for showing context menu
        itemView.setOnCreateContextMenuListener(this);
        //making items in recyclerview clickable
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        contextMenu.setHeaderTitle("< < SELECT THE ACTION > >");

        contextMenu.add(0, 0, getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1, getAdapterPosition(), Common.DELETE);


    }
}
