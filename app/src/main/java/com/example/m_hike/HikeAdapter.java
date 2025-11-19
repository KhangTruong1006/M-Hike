package com.example.m_hike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> {
    private ArrayList<Hike> hikes;
    public HikeAdapter(ArrayList<Hike> hikes){this.hikes = hikes;}

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hike,parent,false);
        return new HikeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position){
        holder.tvHikeName.setText(hikes.get(position).getName());
    }

    @Override
    public int getItemCount(){return hikes.size();}

    public class HikeViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHikeName;

        public HikeViewHolder(@NonNull View itemView){
            super(itemView);
            tvHikeName = itemView.findViewById(R.id.tvHikeName);
        }
    }
}
