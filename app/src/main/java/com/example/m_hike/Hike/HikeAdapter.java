package com.example.m_hike.Hike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.R;

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
        holder.tvDate.setText(hikes.get(position).getDate());
        holder.tvLocation.setText(hikes.get(position).getLocation());
        holder.tvLength.setText(hikes.get(position).getLength() + "km");
        holder.tvDifficulty.setText(hikes.get(position).getDifficulty());

        String parking = (hikes.get(position).getParking() == 1)? "Available" : "Unavailable";
        holder.tvParking.setText(parking);

        holder.tvDescription.setText(hikes.get(position).getDescription());
    }

    @Override
    public int getItemCount(){return hikes.size();}

    public class HikeViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHikeName, tvDate, tvLocation, tvParking, tvLength, tvDifficulty,tvDescription;

        public HikeViewHolder(@NonNull View itemView){
            super(itemView);
            tvHikeName = itemView.findViewById(R.id.tv_item_hike_name);
            tvDate = itemView.findViewById(R.id.tv_item_hike_date);
            tvLocation = itemView.findViewById(R.id.tv_item_hike_location);
            tvParking = itemView.findViewById(R.id.tv_item_hike_parking);
            tvLength = itemView.findViewById(R.id.tv_item_hike_length);
            tvDifficulty = itemView.findViewById(R.id.tv_item_hike_difficulty);
            tvDescription = itemView.findViewById(R.id.tv_item_hike_description);
        }
    }
}
