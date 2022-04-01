package com.example.checkerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkerapp.data.Testdatacollection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AftercreatedetailsActivity extends AppCompatActivity {
    TextView textView3, textView5, textView7;
    Button button1;
    String testname, testdescr, testid;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbref;
    String userpushedkey;
    String usermobilenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftercreatedetails);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            testname = extras.getString("mytestname");
            testdescr = extras.getString("mytestdescription");
            testid = extras.getString("mytestid");
        }
        textView3 = findViewById(R.id.textview3);
        textView5 = findViewById(R.id.textview5);
        textView7 = findViewById(R.id.textview7);
        button1 = findViewById(R.id.buttonfinish);
        textView3.setText(testname);
        textView5.setText(testdescr);
        textView7.setText(testid);

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
                        getusernumber(userpushedkey);
                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                dbref = firebaseDatabase.getReference();
                dbref.child("tests").child(testid).child("testname").setValue(testname);
                dbref.child("tests").child(testid).child("testdescription").setValue(testdescr);
                dbref.child("tests").child(testid).child("testid").setValue(testid);
                dbref.child("tests").child(testid).child("testcreater").setValue(userpushedkey);
                dbref.child("tests").child(testid).child("testonoff").setValue("1");

                dbref.child("tests").child(testid).child("submitlist").child(userpushedkey).setValue("0");

                dbref.child("tests").child(testid).child("usersjoiningtest").child(userpushedkey).child("useremail").setValue(email);
                dbref.child("tests").child(testid).child("usersjoiningtest").child(userpushedkey).child("userid").setValue(userpushedkey);
                dbref.child("tests").child(testid).child("usersjoiningtest").child(userpushedkey).child("usermobilenumber").setValue(usermobilenum);

                dbref.child("users").child(userpushedkey).child("testsjoined").child(testid).child("testname").setValue(testname);
                dbref.child("users").child(userpushedkey).child("testsjoined").child(testid).child("testdescription").setValue(testdescr);
                dbref.child("users").child(userpushedkey).child("testsjoined").child(testid).child("testid").setValue(testid);
                Intent intent = new Intent(AftercreatedetailsActivity.this, StartingscreenActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    private void getusernumber(String userkey) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userkey).child("usernumber");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                usermobilenum = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(AftercreatedetailsActivity.this, "Failed to get mobile number", Toast.LENGTH_SHORT).show();

            }
        });



    }
}