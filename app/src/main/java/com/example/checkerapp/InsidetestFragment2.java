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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsidetestFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsidetestFragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String testname;
    private String testdescription;
    private String testid;

    ListView msglistView;
    EditText editText;
    Button button;
    ArrayAdapter arrayAdapter;
    ArrayList<String> conversationlist = new ArrayList<>();

    DatabaseReference dbref;

    private String chatmsg, chatemail;
    String email;

    public InsidetestFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsidetestFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static InsidetestFragment2 newInstance(String param1, String param2) {
        InsidetestFragment2 fragment = new InsidetestFragment2();
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
        View view = inflater.inflate(R.layout.fragment_insidetest2, container, false);
        editText = (EditText) view.findViewById(R.id.edittextinsidefrag2);
        button = (Button) view.findViewById(R.id.buttom1insidefrag2);
        msglistView = (ListView) view.findViewById(R.id.listviewfrag2);
        arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_expandable_list_item_1,conversationlist);
        msglistView.setAdapter(arrayAdapter);
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





        dbref = FirebaseDatabase.getInstance().getReference().child("tests").child(testid).child("testmessages");
        conversationlist.clear();

        beforeappendchat(1);

    }

    @Override
    public void onStart() {

        super.onStart();
        email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                String temp_key = dbref.push().getKey();
                dbref.updateChildren(map);
                DatabaseReference message_root = dbref.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("email", email);//pranavubaaarhande@gmail.com
                map2.put("message", editText.getText().toString());
                message_root.updateChildren(map2);
//                beforeappendchat(1);




            }
        });

    }

    private void beforeappendchat(int p) {
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(p == 1){
                    appendchatconversation(snapshot);

                }
                if(p == 2){
                    appendchatconversation2(snapshot);

                }





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
            chatemail = (String) ((DataSnapshot)i.next()).getValue();
            chatmsg = (String) ((DataSnapshot)i.next()).getValue();
            String combined = chatemail + ": " + chatmsg;
            conversationlist.add(combined);
            getParentFragmentManager().beginTransaction().detach(InsidetestFragment2.this).attach(InsidetestFragment2.this).commit();

        }
    }
    private void appendchatconversation2(DataSnapshot snapshot) {

        Iterator i =snapshot.getChildren().iterator();
        while (!i.hasNext()){

            chatemail = (String) ((DataSnapshot)i).getValue();
            chatmsg = (String) ((DataSnapshot)i).getValue();
            String combined = chatemail + ": " + chatmsg;
            conversationlist.add(combined);
            getParentFragmentManager().beginTransaction().detach(InsidetestFragment2.this).attach(InsidetestFragment2.this).commit();

        }
    }
}