package com.example.checkerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
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
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.squareup.okhttp.MediaType;

import org.jetbrains.annotations.NotNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import okio.BufferedSink;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsidetestFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsidetestFragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String testname;
    private String testdescription;
    private String testid;
    String filelink;

    int p = 1;

    String email, userpushedkey;

    Button button;
    EditText imageView;
    CameraView cameraView;
    private OkHttpClient okHttpClient;



    public InsidetestFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsidetestFragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static InsidetestFragment3 newInstance(String param1, String param2) {
        InsidetestFragment3 fragment = new InsidetestFragment3();
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
        View view = inflater.inflate(R.layout.fragment_insidetest3, container, false);
        cameraView = view.findViewById(R.id.camview);
        cameraView.setLifecycleOwner(getViewLifecycleOwner());
        imageView = view.findViewById(R.id.imgviewinsidefrag3);
        button = view.findViewById(R.id.mainbutton);
        okHttpClient = new OkHttpClient();
        return view;
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
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
        cameraView.addCameraListener(new CameraListener() {
            @SuppressLint("WrongThread")
            @Override
            public void onPictureTaken(PictureResult result) {
                // Picture was taken!
                // If planning to show a Bitmap, we will take care of
                // EXIF rotation and background threading for you...

//                Bitmap bmp = result.toBitmap(100, 150, callback);
//                imageView.setImageBitmap(bmp);

                // If planning to save a file on a background thread,
                // just use toFile. Ensure you have permissions.
//                result.toFile(file, callback);

                // Access the raw data if needed.
                try {
                    byte[] data = result.getData();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                    imageView.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

//        String encod= Base64.encodeToString( by,Base64.DEFAULT );
//        Uri filepath = Uri.parse(encod);
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                    StorageReference myref = storageReference.child(testid).child("participantphotos").child(userpushedkey);



                    myref.child(String.valueOf(p)).putBytes(byteArray)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                            new OnCompleteListener<Uri>() {

                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    filelink = task.getResult().toString();


                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {

                        }
                    });

                    String mjurl ="http://192.168.1.8:5000/give";
                    RequestBody formbody = new FormBody.Builder().add("imagenumber", String.valueOf(p)).add("testid", testid).add("userid", userpushedkey).add("useremail", email).build();

                    Request request = new Request.Builder().url(mjurl).post(formbody).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
//                                    Toast.makeText(getActivity(), "server down", Toast.LENGTH_SHORT).show();
                                    p++;
                                    cameraView.takePicture();

                                }
                            });
//                            Toast.makeText(getActivity(), "server down", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    String respostring = response.body().string();
//                                    Toast.makeText(requireActivity(), respostring, Toast.LENGTH_SHORT).show();
                                    p++;
                                    cameraView.takePicture();
                                }
                            });


//                            Log.d("resopoonse",respostring);
//                            byte[] encodeByte = Base64.decode(respostring, Base64.DEFAULT);
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//                            imageView.setImageBitmap(bitmap);
//                            Toast.makeText(getActivity(), response.body().string(), Toast.LENGTH_SHORT).show();



                        }

                    });



                }catch (Exception e){
                    Log.d(String.valueOf(e), "error");
                }






            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.takePicture();



            }
        });
    }
}