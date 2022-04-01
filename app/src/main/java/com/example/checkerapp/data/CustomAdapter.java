package com.example.checkerapp.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerapp.DemologinActivity;
import com.example.checkerapp.DemosignupActivity;
import com.example.checkerapp.InsidetestActivity;
import com.example.checkerapp.R;
import com.example.checkerapp.StartingscreenActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{






//        private Arraylistcustom[] listdata;
        //for checking start
        ArrayList<Arraylistcustom> list;
        private Context context;

    //end




        // RecyclerView recyclerView;
        public CustomAdapter(ArrayList<Arraylistcustom> list, Context context) {

            this.list = list;
            this.context = context;
        }
        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.testlayoutrecycle, parent, false);
            return new ViewHolder(listItem);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Arraylistcustom myListData = list.get(position);
            holder.testname.setText(list.get(position).gettestname());
            holder.testdescription.setText(list.get(position).gettestdescription());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"click on item: "+myListData.gettestid() + "des: " + myListData.gettestdescription(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, InsidetestActivity.class);
                    intent.putExtra("testid", myListData.gettestid());
                    intent.putExtra("testdescription", myListData.gettestdescription());
                    intent.putExtra("testname", myListData.gettestname());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView testdescription;
            public TextView testname;
            public LinearLayout linearLayout;
            public ViewHolder(View itemView) {
                super(itemView);
                this.testdescription = (TextView) itemView.findViewById(R.id.testinfo);
                this.testname = (TextView) itemView.findViewById(R.id.testname);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.linearlayoutrecycle);
            }
        }
    }

