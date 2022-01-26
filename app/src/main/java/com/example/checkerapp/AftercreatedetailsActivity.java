package com.example.checkerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.checkerapp.data.Testdatacollection;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class AftercreatedetailsActivity extends AppCompatActivity {
    TextView textView3, textView5, textView7;
    Button button1;
    String testname, testdescr, testid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbref;

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
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                dbref = firebaseDatabase.getReference();
                dbref.child("tests").child(testid).child("testname").setValue(testname);
                dbref.child("tests").child(testid).child("testdescription").setValue(testdescr);
                Intent intent = new Intent(AftercreatedetailsActivity.this, StartingscreenActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}