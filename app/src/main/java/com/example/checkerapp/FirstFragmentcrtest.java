package com.example.checkerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragmentcrtest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragmentcrtest extends Fragment {
    private static final String ARG_PARAM1 = "mytestname";
    private static final String ARG_PARAM2 = "mytestdescription";
    private static final int pic_id = 123;

    Button button1, button2, button3, button4, button5;
    ImageView imageView1, imageView2, imageView3, imageView4;


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
    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK){
                assert result.getData() != null;
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                imageView1.setImageBitmap(bitmap);

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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mGetContent.launch(intent);



            }
        });
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

    }

//    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri uri) {
//                    imageView1.setImageURI(uri);
//                }
//            });

}