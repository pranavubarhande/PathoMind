package com.example.checkerapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class FirstFragmentcrtest extends Fragment {
    private static final String ARG_PARAM1 = "mytestname";
    private static final String ARG_PARAM2 = "mytestdescription";

    static String testuniquekey;


    Button button1, button2, button3, button4, button5;
    ImageView imageView1, imageView2, imageView3, imageView4;


    StorageReference storageReference;
    ProgressDialog progressDialog;


    private String testnametobesaved;
    private String testdescriptiontobesaved;

    public FirstFragmentcrtest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragmentcrtest.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragmentcrtest newInstance(String param1, String param2) {
        FirstFragmentcrtest fragment = new FirstFragmentcrtest();
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
        View view= inflater.inflate(R.layout.fragment_first_fragmentcrtest, container, false);
        button1 = (Button) view.findViewById(R.id.frag1button1);
        button2 = (Button) view.findViewById(R.id.frag1button2);
        button3 = (Button) view.findViewById(R.id.frag1button3);
        button4 = (Button) view.findViewById(R.id.frag1button4);
        button5 = (Button) view.findViewById(R.id.frag1finalbutton);
        imageView1 = (ImageView) view.findViewById(R.id.frag1imgView1);
        imageView2 = (ImageView) view.findViewById(R.id.frag1imgView2);
        imageView3 = (ImageView) view.findViewById(R.id.frag1imgView3);
        imageView4 = (ImageView) view.findViewById(R.id.frag1imgView4);

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    static int mycode;
    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK && mycode == 1){
                assert result.getData() != null;
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");


                imageView1.setImageBitmap(bitmap);


            }
            if (result.getResultCode() == Activity.RESULT_OK && mycode == 2){
                assert result.getData() != null;
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                imageView2.setImageBitmap(bitmap);


            }
            if (result.getResultCode() == Activity.RESULT_OK && mycode == 3){
                assert result.getData() != null;
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                imageView3.setImageBitmap(bitmap);


            }
            if (result.getResultCode() == Activity.RESULT_OK && mycode == 4){
                assert result.getData() != null;
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                imageView4.setImageBitmap(bitmap);


            }
            else {
                Log.d("cameraintent","act result");
            }

        }
    });
    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            testnametobesaved = getArguments().getString(ARG_PARAM1);
            testdescriptiontobesaved = getArguments().getString(ARG_PARAM2);
        }


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycode = 1;

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mGetContent.launch(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycode = 2;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mGetContent.launch(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycode = 3;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mGetContent.launch(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycode = 4;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mGetContent.launch(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testuniquekey = UUID.randomUUID().toString();
                uploadimages(testuniquekey);


            }
        });
    }


    private void uploadimages(String testuniquekey) {


        imageView1.setDrawingCacheEnabled(true);
        imageView2.setDrawingCacheEnabled(true);
        imageView3.setDrawingCacheEnabled(true);
        imageView4.setDrawingCacheEnabled(true);

        Bitmap bitmap1 = imageView1.getDrawingCache();
        Bitmap bitmap2 = imageView2.getDrawingCache();
        Bitmap bitmap3 = imageView3.getDrawingCache();
        Bitmap bitmap4 = imageView4.getDrawingCache();



        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference myref = storageReference.child(testuniquekey).child("questionphotos");



        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading..."); // Setting Message
        progressDialog.setTitle("Wait"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        uploadimage1(bitmap1,myref,bitmap2,bitmap3,bitmap4);


    }

    private void uploadimage1(Bitmap bitmap1, StorageReference myref, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4) {

        ByteArrayOutputStream ba1=new ByteArrayOutputStream(  );
        bitmap1.compress( Bitmap.CompressFormat.PNG,90,ba1 );
        byte[] by1=ba1.toByteArray();
//        String encod= Base64.encodeToString( by,Base64.DEFAULT );
//        Uri filepath = Uri.parse(encod);

        myref.child("1").putBytes(by1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "upload 1 success", Toast.LENGTH_SHORT).show();
                        uploadimage2(bitmap2,myref,bitmap3,bitmap4);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(), "upload 1 failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void uploadimage2(Bitmap bitmap2, StorageReference myref, Bitmap bitmap3, Bitmap bitmap4) {
        ByteArrayOutputStream ba2=new ByteArrayOutputStream(  );
        bitmap2.compress( Bitmap.CompressFormat.PNG,90,ba2 );

        byte[] by2=ba2.toByteArray();
//        String encod= Base64.encodeToString( by,Base64.DEFAULT );
//        Uri filepath = Uri.parse(encod);
        myref.child("2").putBytes(by2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "upload 2 success", Toast.LENGTH_SHORT).show();
                        uploadimage3(bitmap3,myref, bitmap4);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(), "upload 2 failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void uploadimage3(Bitmap bitmap3, StorageReference myref, Bitmap bitmap4) {
        ByteArrayOutputStream ba3=new ByteArrayOutputStream(  );
        bitmap3.compress( Bitmap.CompressFormat.PNG,90,ba3 );

        byte[] by3=ba3.toByteArray();
//        String encod= Base64.encodeToString( by,Base64.DEFAULT );
//        Uri filepath = Uri.parse(encod);
        myref.child("3").putBytes(by3)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "upload 3 success", Toast.LENGTH_SHORT).show();
                        uploadimage4(bitmap4,myref);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(), "upload 3 failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void uploadimage4(Bitmap bitmap4, StorageReference myref) {
        ByteArrayOutputStream ba4=new ByteArrayOutputStream(  );
        bitmap4.compress( Bitmap.CompressFormat.PNG,90,ba4 );

        byte[] by4=ba4.toByteArray();
//        String encod= Base64.encodeToString( by,Base64.DEFAULT );
//        Uri filepath = Uri.parse(encod);
        myref.child("4").putBytes(by4)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "upload 4 success", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(getActivity(), AftercreatedetailsActivity.class);
                        intent.putExtra("mytestname", testnametobesaved);
                        intent.putExtra("mytestdescription", testdescriptiontobesaved);
                        intent.putExtra("mytestid", testuniquekey);

                        startActivity(intent);
                        requireActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(), "upload 4 failed", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

    }


}