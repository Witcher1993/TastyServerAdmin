package com.example.sadulla.tastyserveradmin;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.BitSet;

public class SignIn extends AppCompatActivity {

    EditText phoneEdt;
    EditText passwordEdt;
    Button signInButton;

    //

    FirebaseDatabase database;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phoneEdt = (EditText) findViewById(R.id.sign_in_phone_edit);
        passwordEdt = (EditText)findViewById(R.id.sign_in_password_edit);
        signInButton = (Button)findViewById(R.id.sign_in_sign_in_button);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("WAIT...");
                mDialog.show();
                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}
