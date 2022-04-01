package com.example.checkerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InsidetestActivity extends AppCompatActivity {
    String mytestid;
    String mytestname;
    String mytestdescription;
    String email, userpushedkey, testcreater, testonoffvar;
    String issubmitted;
    BottomNavigationView bottomNavigationView;
    InsidetestFragment1 firstfragment = new InsidetestFragment1();
    InsidetestFragment2 secondfragment = new InsidetestFragment2();
    InsidetestFragment3 thirdfragment = new InsidetestFragment3();
    InsidetestFragment4 fourthfragment = new InsidetestFragment4();
    InsidetestFragment5 fifthfragment = new InsidetestFragment5();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insidetest);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mytestid = extras.getString("testid");
            mytestdescription = extras.getString("testdescription");
            mytestname = extras.getString("testname");
        }
        finduserkey();






        bottomNavigationView = findViewById(R.id.bottomnavigation);
        Bundle bundle = new Bundle();
        bundle.putString("mytestname", mytestname);
        bundle.putString("mytestdescription", mytestdescription);
        bundle.putString("mytestid", mytestid);
        firstfragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutinsidetest, firstfragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        firstfragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutinsidetest, firstfragment).commit();
                        return true;

                    case R.id.nav_chats:
                        secondfragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutinsidetest, secondfragment).commit();
                        return true;
                    case R.id.nav_camera:
                        thirdfragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutinsidetest, thirdfragment).commit();
                        return true;
                    case R.id.nav_participants:
                        fourthfragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutinsidetest, fourthfragment).commit();
                        return true;
                    case R.id.nav_settings:
                        fifthfragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutinsidetest, fifthfragment).commit();
                        return true;




                }
                return true;
            }
        });

    }

    private void finduserkey() {
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
                        }
                        findtestcreater();

                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    private void findtestcreater() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tests");
        databaseReference.child(mytestid).child("testcreater").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                testcreater = snapshot.getValue().toString();
                findisteston();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void findisteston() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("tests");
        databaseRef.child(mytestid).child("testonoff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                testonoffvar = snapshot.getValue().toString();
                findissubmitted();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void findissubmitted() {
        DatabaseReference databaseRefer = FirebaseDatabase.getInstance().getReference("tests");
        databaseRefer.child(mytestid).child("submitlist").child(userpushedkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                issubmitted = snapshot.getValue().toString();
                lastprocess();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void lastprocess() {
        if((testonoffvar.equals("0") || issubmitted.equals("1")) && (!userpushedkey.equals(testcreater))){
            Toast.makeText(this, "Test is over for students", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InsidetestActivity.this, StartingscreenActivity.class);
            startActivity(intent);
            finish();
        }
    }
}