package com.example.checkerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CreatetestActivity extends AppCompatActivity {
    EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtest);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Button buttonforfragments = (Button)findViewById(R.id.buttontestcreate);
        editText1 = (EditText)findViewById(R.id.createtesttestname);
        editText2 = (EditText)findViewById(R.id.createtesttestdescription);

        buttonforfragments.setOnClickListener(new View.OnClickListener() {
            CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkbox1crtest);
            CheckBox checkBox2 = (CheckBox)findViewById(R.id.checkbox2crtest);
            CheckBox checkBox3 = (CheckBox)findViewById(R.id.checkbox3crtest);
            CheckBox checkBox4 = (CheckBox)findViewById(R.id.checkbox4crtest);
            @Override
            public void onClick(View v) {
                String tstname = editText1.getText().toString();
                String tstdescr = editText2.getText().toString();


                if(checkBox1.isChecked()){
                    loadFragment(new FirstFragmentcrtest(), tstname,tstdescr);
                }
                if(checkBox2.isChecked()){
                    loadFragment(new SecondFragmentcrtest(), tstname,tstdescr);
                }
                if(checkBox3.isChecked()){
                    loadFragment(new ThirdFragmentcrtest(), tstname,tstdescr);
                }
                if(checkBox4.isChecked()){
                    loadFragment(new FourthFragmentcrtest(), tstname,tstdescr);
                }

            }
        });

    }

    private void loadFragment(Fragment fragment, String tstname, String tstdescr) {
        Bundle bundle = new Bundle();
        bundle.putString("mytestname", tstname);
        bundle.putString("mytestdescription", tstdescr);

        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.setReorderingAllowed(true).replace(R.id.framelayoutcreatetest,fragment);
        fragmentTransaction.commit();


    }
}