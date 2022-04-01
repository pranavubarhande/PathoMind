package com.example.checkerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class JointestActivity extends AppCompatActivity {
    EditText editText;
    Button button;

    String userpushedkey;

    String testname, testdescription, testid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jointest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText = findViewById(R.id.jointestedittext);
        button = findViewById(R.id.jointextbutton);

        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("userkeys")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot childsnapshot: snapshot.getChildren()){
                            userpushedkey = childsnapshot.getKey();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredid = editText.getText().toString();

                try {
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("tests").child(enteredid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            testdescription = snapshot.child("testdescription").getValue(String.class);
                            testid = snapshot.child("testid").getValue(String.class);
                            testname = snapshot.child("testname").getValue(String.class);
                            Toast.makeText(JointestActivity.this, testname + testdescription, Toast.LENGTH_SHORT).show();





                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(JointestActivity.this, "Add test failure", Toast.LENGTH_SHORT).show();

                        }
                    });
                    mDatabase.child("users").child(userpushedkey).child("testsjoined").child(testid).child("testname").setValue(testname);
                    mDatabase.child("users").child(userpushedkey).child("testsjoined").child(testid).child("testdescription").setValue(testdescription);
                    mDatabase.child("users").child(userpushedkey).child("testsjoined").child(testid).child("testid").setValue(testid);

                    mDatabase.child("tests").child(testid).child("submitlist").child(userpushedkey).setValue("0");


                } catch (Exception e) {
                    Toast.makeText(JointestActivity.this, "Add test failure", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }
}