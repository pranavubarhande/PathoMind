package com.example.checkerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ortiz.touchview.TouchImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsidetestFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsidetestFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String testname;
    private String testdescription;
    private String testid;

    TouchImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    TextView textView1, textView2, textView3, textView4;
    Button button1, button2;
    ProgressDialog dialog;

    AlertDialog.Builder builder;

    StorageReference storageReference;
    ActivityResultLauncher<String> mtakepdf;

    String email, userpushedkey;

    public InsidetestFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsidetestFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static InsidetestFragment1 newInstance(String param1, String param2) {
        InsidetestFragment1 fragment = new InsidetestFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            testname = getArguments().getString("mytestname");
            testdescription = getArguments().getString("mytestdescription");
            testid = getArguments().getString("mytestid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_insidetest1, container, false);
        imageView1 = (TouchImageView) view.findViewById(R.id.infrag1image1);
        imageView2 = (TouchImageView) view.findViewById(R.id.infrag1image2);
        imageView3 = (TouchImageView) view.findViewById(R.id.infrag1image3);
        imageView4 = (TouchImageView) view.findViewById(R.id.infrag1image4);
        imageView5 = (TouchImageView) view.findViewById(R.id.infrag1image5);
        textView1 = (TextView) view.findViewById(R.id.infrag1textview1);
        textView2 = (TextView) view.findViewById(R.id.infrag1textview2);
        textView3 = (TextView) view.findViewById(R.id.infrag1textview3);
        textView4 = (TextView) view.findViewById(R.id.infrag1textview4);
        button1 = (Button) view.findViewById(R.id.infrag1btn1);
        button2 = (Button) view.findViewById(R.id.infrag1btn2);

        mtakepdf = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        Uri pdfuri = result;
                        File file= new File(pdfuri.getPath());
                        String filename = file.getName();
                        // Here we are initialising the progress dialog box
                        dialog = new ProgressDialog(requireContext());
                        dialog.setMessage("Uploading");

                        // this will show message uploading
                        // while pdf is uploading
                        dialog.show();

                        final String timestamp = "" + System.currentTimeMillis();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        final String messagePushID = timestamp;

                        // Here we are uploading the pdf in firebase storage with the name of current time
                        final StorageReference filepath = storageReference.child(testid).child("users").child(userpushedkey).child("submission").child(filename);//.child(messagePushID + "." + "pdf");
                        filepath.putFile(pdfuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(@NonNull @NotNull UploadTask.TaskSnapshot taskSnapshot) {
                                dialog.dismiss();
                                Toast.makeText(requireActivity(), "File Uploaded", Toast.LENGTH_SHORT).show();
                                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("tests");
                                dbref.child(testid).child("submitlist").child(userpushedkey).setValue("1");
                                Toast.makeText(requireActivity(), "Test set off", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    }
                }
        );

        return view;
    }
//

    @Override
    public void onStart() {
        super.onStart();
        textView2.setText(testname);
        textView4.setText(testdescription);
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference myref = storageReference.child(testid).child("questionphotos").child("1");
        final long ONE_MEGABYTE = 1024 * 1024;
        myref.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView1.setImageBitmap(bmp);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
        StorageReference myref2 = storageReference.child(testid).child("questionphotos").child("2");
        myref2.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView2.setImageBitmap(bmp);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
        StorageReference myref3 = storageReference.child(testid).child("questionphotos").child("3");
        myref.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView3.setImageBitmap(bmp);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
        StorageReference myref4 = storageReference.child(testid).child("questionphotos").child("4");
        myref.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView4.setImageBitmap(bmp);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
        StorageReference myref5 = storageReference.child(testid).child("questionphotos").child("5");

        myref.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView5.setImageBitmap(bmp);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
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

                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                mGetContent.launch(galleryIntent);
                mtakepdf.launch("application/pdf");

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());
                builder.setMessage("You will exit now. Sure?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(requireActivity(), StartingscreenActivity.class);
                                startActivity(intent);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Exiting?");
                alert.show();

            }
        });



    }
}