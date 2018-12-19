package com.example.sadulla.tastyserveradmin.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.sadulla.tastyserveradmin.Model.Request;
import com.example.sadulla.tastyserveradmin.OrderStatus;
import com.example.sadulla.tastyserveradmin.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase database;
    DatabaseReference orders;


    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        orders = database.getReference("Requests");
    }


    //


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orders.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }


    //


    public ListenOrder() {
    }

    //

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        //Trigger HERE
        Request request = dataSnapshot.getValue(Request.class);
        if(request.getStatus().equals("0"))
            showNotification(dataSnapshot.getKey(), request);
    }



    private void showNotification(String key, Request request) {

        Intent intent = new Intent(getBaseContext(), OrderStatus.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("TASTY")
                .setContentInfo("ORDER")
                .setContentText("NEW ORDER #"+key+" ACCEPTED")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //If we want many notifications then give each of them UNIQUE ID
        int randomInt = new Random().nextInt(9999-1)+1;

        manager.notify(randomInt, builder.build());



    }


    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
