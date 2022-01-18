package com.example.checkerapp.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerapp.R;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
        private Arraylistcustom[] listdata;

        // RecyclerView recyclerView;
        public CustomAdapter(Arraylistcustom[] listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.testlayoutrecycle, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Arraylistcustom myListData = listdata[position];
            holder.testname.setText(listdata[position].getTestname());
            holder.testinfo.setText(listdata[position].getTestinfo());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"click on item: "+myListData.getTestname(),Toast.LENGTH_LONG).show();
                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.length;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView testinfo;
            public TextView testname;
            public LinearLayout linearLayout;
            public ViewHolder(View itemView) {
                super(itemView);
                this.testinfo = (TextView) itemView.findViewById(R.id.testinfo);
                this.testname = (TextView) itemView.findViewById(R.id.testname);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.linearlayoutrecycle);
            }
        }
    }

