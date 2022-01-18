package com.example.checkerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DemologinActivity extends AppCompatActivity {
    EditText email, pass;
    TextView forgetPass, signupinstead;
    FirebaseAuth auth;

    Button login;
    float v= 0;

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demologin);
        email = findViewById(R.id.emailentry);
        pass = findViewById(R.id.loginpasswordentry);
        forgetPass = findViewById(R.id.loginforgotpassword);
        signupinstead = findViewById(R.id.gotosignup);
        login = findViewById(R.id.loginbutton);

        auth = FirebaseAuth.getInstance();


        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        signupinstead.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        forgetPass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signupinstead.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        signupinstead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DemologinActivity.this, DemosignupActivity.class);
                startActivity(intent);
            }

        });
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_pass = pass.getText().toString();
                loginUser(txt_email,txt_pass);

            }

        });

    }

    private void loginUser(String txt_email, String txt_pass) {
        auth.signInWithEmailAndPassword(txt_email,txt_pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                showToast("Login Successful");
                Intent intent = new Intent(DemologinActivity.this, StartingscreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}