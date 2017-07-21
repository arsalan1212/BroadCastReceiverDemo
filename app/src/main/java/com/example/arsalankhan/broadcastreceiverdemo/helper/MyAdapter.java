package com.example.arsalankhan.broadcastreceiverdemo.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arsalankhan.broadcastreceiverdemo.R;

import java.util.ArrayList;

/**
 * Created by Arsalan khan on 7/21/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Contract> contractArrayList;
    public MyAdapter(Context context, ArrayList<Contract> arrayList){
        this.context=context;
        contractArrayList=arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.single_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_id.setText(contractArrayList.get(position).getId()+"");
        holder.tv_number.setText(contractArrayList.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return contractArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_id,tv_number;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_number=itemView.findViewById(R.id.tv_number);
        }
    }
}
