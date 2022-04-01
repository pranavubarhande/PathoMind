package com.example.checkerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class UserinfoActivity extends AppCompatActivity {
    String username, useremail, usermobileno;
    String email,userpushedkey;
    TextView textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        textView1 = (TextView) findViewById(R.id.userinfotv1);//name
        textView2 = (TextView) findViewById(R.id.userinfotv2);//email
        textView3 = (TextView) findViewById(R.id.userinfotv3);




    }
    private void textviewimplementation() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userpushedkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                useremail = snapshot.child("useremail").getValue(String.class);
                username = snapshot.child("username").getValue(String.class);
                usermobileno = snapshot.child("usernumber").getValue(String.class);
                textView1.setText(username);
                textView2.setText(useremail);
                textView3.setText(usermobileno);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("userkeys")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot childsnapshot: snapshot.getChildren()){
                            userpushedkey = childsnapshot.getKey();
                            textviewimplementation();

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });





    }


}