package com.example.checkerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkerapp.data.Signupentries;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DemosignupActivity extends AppCompatActivity {
    EditText signupemail, signuppass,signupmobile,signupconfirmpass,signupfullname;
    TextView logininstead;
    FirebaseAuth auth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    String useruid;



    Button signup;
    float v= 0;
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void registerUser(String email, String password) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DemosignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                }
                else {
                    showToast("Firebase Registration Failed");
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demosignup);

    }

    @Override
    protected void onStart() {
        super.onStart();
        signupemail = findViewById(R.id.signupemailentry);
        signupfullname = findViewById(R.id.signupnameentry);
        signuppass = findViewById(R.id.signuppasswordentry);
        signupmobile = findViewById(R.id.signupmobileentry);
        signupconfirmpass = findViewById(R.id.signupconfirmpasswordentry);
        signup = findViewById(R.id.signupbutton);
        logininstead = findViewById(R.id.gotologin);


        signup.setAlpha(v);
        signupfullname.setAlpha(v);
        signupconfirmpass.setAlpha(v);
        signuppass.setAlpha(v);
        signupmobile.setAlpha(v);
        signupemail.setAlpha(v);
        logininstead.setAlpha(v);

        signupemail.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        signupfullname.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(350).start();
        signup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(800).start();
        signupmobile.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        signuppass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signupconfirmpass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
        logininstead.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        logininstead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DemosignupActivity.this, DemologinActivity.class);
                startActivity(intent);
                finish();
            }

        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String txt_email = signupemail.getText().toString();
                String txt_password = signuppass.getText().toString();
                String txt_confirmpassword = signupconfirmpass.getText().toString();
                String mo = signupmobile.getText().toString();
                String fulnm = signupfullname.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    showToast("Empty Credentials");
                }
                else if (txt_password.length()<6){
                    showToast("Password Small");
                }
                else if (txt_password == txt_confirmpassword){
                    showToast("Pass and Confirm pass different");
                }
                else{
                    registerUser(txt_email,txt_password);

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference();


                    Signupentries obj = new Signupentries(mo,fulnm, txt_email, txt_password);
                    String key = reference.push().getKey();

                    reference.child("users").child(key).setValue(obj);
                    reference.child("userkeys").child(key).setValue(txt_email);


                    showToast("User Registered");
                    Intent intent = new Intent(DemosignupActivity.this, DemologinActivity.class);
                    startActivity(intent);
                    finish();



                }


            }

        });
    }


}