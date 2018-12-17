package com.example.sadulla.tastyserveradmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sadulla.tastyserveradmin.Model.User;
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
                signInUser(phoneEdt.getText().toString(), passwordEdt.getText().toString());
            }
        });


    }

    private void signInUser(final String phone, String password) {
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("< < WAIT... > >");
        mDialog.show();

        final String localPhone = phone;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                if (dataSnapshot.child(localPhone).exists())
                {
                    mDialog.dismiss();
                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
                    if(Boolean.parseBoolean(user.getIsStaff()))
                    {
                        if(user.getPassword().equals(localPassword))
                        {
                            //LOGIN OK
                            Toast.makeText(SignIn.this, "< < SUCCESS! > >", Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(SignIn.this, Home.class);
                            startActivity(home);
                        }
                        else
                        {
                            Toast.makeText(SignIn.this, "< < WRONG PASSWORD! > >", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "< < YOU ARE NOT ADMIN! > >", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(SignIn.this, "< < USER DOES NOT EXIST > >", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
