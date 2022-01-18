package com.example.checkerapp;

import androidx.annotation.NonNull;
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

import com.example.checkerapp.data.Arraylistcustom;
import com.example.checkerapp.data.CustomAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class StartingscreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variables for working with menu.
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


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

        //start adapter implementation
        Arraylistcustom[] myListData = new Arraylistcustom[] {
                new Arraylistcustom("Test0", "Test for MU maths Best of luck","id1"),
                new Arraylistcustom("Test1" , "Test for MU CG Best of luck","id1"),
                new Arraylistcustom("Test2", "Test for MU DLCOA Best of luck","id1"),
                new Arraylistcustom("Test3","Test for MU DS Best of luck","id1"),
                new Arraylistcustom("Test4", "Test for MU DSGT Best of luck","id1"),

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.startscreenrecyclerview);
        CustomAdapter adapter = new CustomAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //end
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
                showToast("will take to userinfo screen");
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
                showToast("Will take to How to use app screen");
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