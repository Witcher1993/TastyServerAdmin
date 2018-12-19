package com.example.sadulla.tastyserveradmin.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.sadulla.tastyserveradmin.Interface.ItemClickListener;
import com.example.sadulla.tastyserveradmin.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView orderIdTextView;
    public TextView orderStatusTextView;
    public TextView orderAddressTextView;
    public TextView orderPhoneTextView;

    private ItemClickListener itemClickListener;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderIdTextView = (TextView)itemView.findViewById(R.id.order_id);
        orderStatusTextView = (TextView)itemView.findViewById(R.id.order_status);
        orderAddressTextView = (TextView)itemView.findViewById(R.id.order_address);
        orderPhoneTextView = (TextView)itemView.findViewById(R.id.order_phone);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("< < SELECT ACTION!!! > >");

        contextMenu.add(0,0,getAdapterPosition(),"Update");
        contextMenu.add(0,1,getAdapterPosition(),"Delete");


    }
}
