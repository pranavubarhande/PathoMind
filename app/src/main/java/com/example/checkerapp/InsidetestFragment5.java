package com.example.checkerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsidetestFragment5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsidetestFragment5 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String testname;
    private String testdescription;
    private String testid;
    Button button;
    String email;
    String userpushedkey;
    String testcreater;
    String testonoffvar;

    ListView notificationlistView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> particlist = new ArrayList<>();

    DatabaseReference dbref;

    public InsidetestFragment5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsidetestFragment5.
     */
    // TODO: Rename and change types and number of parameters
    public static InsidetestFragment5 newInstance(String param1, String param2) {
        InsidetestFragment5 fragment = new InsidetestFragment5();
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
        View view = inflater.inflate(R.layout.fragment_insidetest5, container, false);
        button = view.findViewById(R.id.button5);
        notificationlistView = view.findViewById(R.id.listviewfrag5);
        arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_expandable_list_item_1,particlist);
        notificationlistView.setAdapter(arrayAdapter);
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
        dbref = FirebaseDatabase.getInstance().getReference().child("tests").child(testid).child("notifications");
        particlist.clear();
        appendparticipants();
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
            String partmessage = (String) ((DataSnapshot) i.next()).getValue();
            String combined = partemail + ": " + partmessage;
            particlist.add(combined);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
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
                            firstmethod();
                            secondmethod();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("tests");
                dbref.child(testid).child("testonoff").setValue("0");
                Toast.makeText(requireActivity(), "Test set off", Toast.LENGTH_SHORT).show();
                button.setClickable(false);
            }
        });



    }

    private void secondmethod() {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("tests");
        dbr.child(testid).child("testonoff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                testonoffvar = Objects.requireNonNull(snapshot.getValue()).toString();
                if(testonoffvar.equals("0")){
                    button.setClickable(false);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void firstmethod() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tests");
        databaseReference.child(testid).child("testcreater").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                testcreater = snapshot.getValue().toString();
                if(!testcreater.equals(userpushedkey)){
                    button.setVisibility(View.GONE);
                    notificationlistView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}