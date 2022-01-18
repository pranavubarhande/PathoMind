package com.example.checkerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;
    //variables
    Animation top_animation , bottom_animation;
    ImageView imageView;
    TextView textView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.flash_screen);

        //animations addition to flash screen
        top_animation= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_animation=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView2);

        imageView.setAnimation(top_animation);
        textView.setAnimation(bottom_animation);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();
                if(auth.getCurrentUser() != null){
                    Intent intent = new Intent(MainActivity.this, StartingscreenActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(MainActivity.this, DemologinActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_SCREEN);

    }
}