package com.example.checkerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chaquo.python.Python;
import com.example.checkerapp.CreatetestActivity;
import com.example.checkerapp.DemologinActivity;
import com.example.checkerapp.JointestActivity;
import com.example.checkerapp.R;
import com.example.checkerapp.data.Arraylistcustom;
import com.example.checkerapp.data.CustomAdapter;
import com.example.checkerapp.data.Testdatacollection;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class StartingscreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variables for working with menu.
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String userpushedkey;

    String email;

    CustomAdapter adapter;
    Arraylistcustom[] myListData;


//basic
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startingscreen);


    }
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart invoked");



        //start

        //bottomright fab setup
        //fab1
        FloatingActionButton floatingActionButton1 = findViewById(R.id.startscreenfabaction1);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("gotocreatetestactivity");
                Intent intent1 = new Intent(StartingscreenActivity.this, CreatetestActivity.class);
                startActivity(intent1);
            }

        });
        //fab2
        FloatingActionButton floatingActionButton2 = findViewById(R.id.startscreenfabaction2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("gotojointestactivity");
                Intent intent2 = new Intent(StartingscreenActivity.this, JointestActivity.class);
                startActivity(intent2);
            }

        });
        //end
        //start setup menu

        //define hooks for menu
        drawerLayout = findViewById(R.id.startscreendrawerlayout);
        navigationView = findViewById(R.id.startscreennavigation);
        toolbar = findViewById(R.id.startscreentoolbar);
        //setting toolbar for menu
        setSupportActionBar(toolbar);
        //setting navigation drawer menu

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //let menu items clickable
        navigationView.setNavigationItemSelectedListener(this);

        //end
        //getting email and id for rest operations
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
                            Toast.makeText(StartingscreenActivity.this, userpushedkey, Toast.LENGTH_SHORT).show();
                        }
                        listviewimplement();
                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });





        //start adapter implementation


        //end
    }

    private void listviewimplement() {
        ArrayList<Arraylistcustom> list = new ArrayList<>();
        list.add(new Arraylistcustom("praanv","5464afas45", "uabrhande"));
//
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        list.clear();
        mDatabase.child(userpushedkey).child("testsjoined").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Iterator i =snapshot.getChildren().iterator();
                while (i.hasNext()){

                    String desc = (String) ((DataSnapshot)i.next()).getValue();
                    String id = (String) ((DataSnapshot)i.next()).getValue();
                    String name = (String) ((DataSnapshot)i.next()).getValue();
                    Arraylistcustom kas = new Arraylistcustom(desc, id, name);

                    list.add(kas);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

//        mDatabase.child(userpushedkey).child("testsjoined").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NotNull DataSnapshot snapshot) {
////                Iterator<DataSnapshot> items = snapshot.getChildren().iterator();
////                int counter = 0;
////                while (items.hasNext()){
////                    DataSnapshot item = items.next();
////
////                    Log.i("Result testname", Objects.requireNonNull(item.child("testname").getValue()).toString());
////                    Log.i("Result testdescription", Objects.requireNonNull(item.child("testdescription").getValue()).toString());
////                    list.add(new Arraylistcustom(Objects.requireNonNull(item.child("testname").getValue()).toString(), Objects.requireNonNull(item.child("testdescription").getValue()).toString()));
////                }
//
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    Arraylistcustom kas = ds.getValue(Arraylistcustom.class);
//
//
//                    list.add(kas);
//
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//                Toast.makeText(StartingscreenActivity.this, "Database error", Toast.LENGTH_SHORT).show();
//
//            }
//        });



        // in below line we are calling method for add child event
        // listener to get the child of our database.


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.startscreenrecyclerview);
        adapter = new CustomAdapter(list, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop invoked");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart invoked");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy invoked");
    }
    //method for onback press listener
    @Override
    public void onBackPressed() {
        //start
        //if startscreen drawer menu is open then on back click that should only closed not whole activity
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
        //end
    }
    public boolean onNavigationItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.userinfo:
                Intent i = new Intent(StartingscreenActivity.this, UserinfoActivity.class);
                startActivity(i);

                break;
            case R.id.menulogout:
                FirebaseAuth.getInstance().signOut();
                showToast("user signed out");
                Intent intent = new Intent(StartingscreenActivity.this, DemologinActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menusettings:
                showToast("Will take to setting activity");
                break;
            case R.id.menuhowto:
                Intent s = new Intent(StartingscreenActivity.this, HowtoActivity.class);
                startActivity(s);
                break;

        }
        return true;
    }

    //start
    //method for showing toast after fab item clicks
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //end
}