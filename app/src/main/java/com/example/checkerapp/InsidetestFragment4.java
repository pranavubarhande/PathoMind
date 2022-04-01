package com.example.checkerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsidetestFragment4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsidetestFragment4 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String testname;
    private String testdescription;
    private String testid;

    ListView partilistView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> particlist = new ArrayList<>();

    DatabaseReference dbref;

    private String chatmsg, chatemail;

    public InsidetestFragment4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsidetestFragment4.
     */
    // TODO: Rename and change types and number of parameters
    public static InsidetestFragment4 newInstance(String param1, String param2) {
        InsidetestFragment4 fragment = new InsidetestFragment4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insidetest4, container, false);

        partilistView = (ListView) view.findViewById(R.id.listviewfrag4);
        arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_expandable_list_item_1,particlist);
        partilistView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            testname = getArguments().getString("mytestname");
            testdescription = getArguments().getString("mytestdescription");
            testid = getArguments().getString("mytestid");
        }
        dbref = FirebaseDatabase.getInstance().getReference().child("tests").child(testid).child("usersjoiningtest");
        particlist.clear();
        appendparticipants();




    }



    @Override
    public void onStart() {
        super.onStart();
        try{


        }catch (Exception e){
            e.printStackTrace();
        }
        try{


        }catch (Exception e){
            e.printStackTrace();
        }
        try{


        }catch (Exception e){
            e.printStackTrace();
        }

//        try{
//
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }

    private void appendparticipants() {
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                appendchatconversation(snapshot);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();

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
    }

    private void appendchatconversation(DataSnapshot snapshot) {
        Iterator i =snapshot.getChildren().iterator();
        while (i.hasNext()){

            String partemail = (String) ((DataSnapshot) i.next()).getValue();
            String partid = (String) ((DataSnapshot) i.next()).getValue();
            String partmonumber = (String) ((DataSnapshot) i.next()).getValue();
            String combined = partemail + ": " + partmonumber;
            particlist.add(combined);

        }
    }
}