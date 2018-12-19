package com.example.sadulla.tastyserveradmin;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sadulla.tastyserveradmin.Common.Common;
import com.example.sadulla.tastyserveradmin.Interface.ItemClickListener;
import com.example.sadulla.tastyserveradmin.Model.Request;
import com.example.sadulla.tastyserveradmin.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;


    MaterialSpinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase initialize
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        //Initialize
        recyclerView = (RecyclerView)findViewById(R.id.oder_list_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders();//load all orders
        
        //////////////////////////////////////////////////////
    }


    //==============================================================================================

    private void loadOrders() {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.orderIdTextView.setText(adapter.getRef(position).getKey());
                viewHolder.orderStatusTextView.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.orderAddressTextView.setText(model.getAddress());
                viewHolder.orderPhoneTextView.setText(model.getPhone());


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Avoiding crash
                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


    //==============================================================================================

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getIntent().equals(Common.UPDATE))
        {
            //UPDATE THE ORDER STATUS
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        }else
        if(item.getIntent().equals(Common.DELETE))
        {
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }



    //==============================================================================================


    private void deleteOrder(String key) {

        requests.child(key).removeValue();
        Toast.makeText(this, "< < ORDER REMOVED > >", Toast.LENGTH_SHORT).show();

    }


    //==============================================================================================


    private void showUpdateDialog(String key, final Request item) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("< < UPDATE ORDER!!! > >");
        alertDialog.setMessage("< < CHOOSE STATUS!!! > >");


        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout, null);

        spinner =(MaterialSpinner)view.findViewById(R.id.status_spinner);
        spinner.setItems("PLACED", "ON WAY", "SHIPPED");

        alertDialog.setView(view);

        final String localKey = key;

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);
                Toast.makeText(OrderStatus.this, "< < ORDER EDITED > >", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();

    }


    //==============================================================================================
}
