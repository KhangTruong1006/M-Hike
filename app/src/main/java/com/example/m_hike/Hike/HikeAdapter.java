package com.example.m_hike.Hike;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        Hike current = hikes.get(position);

        holder.tvHikeName.setText(current.getName());
        holder.tvDate.setText(current.getDate());
        holder.tvLocation.setText(current.getLocation());
        holder.tvLength.setText(current.getLength() + "km");
        holder.tvDifficulty.setText(current.getDifficulty());

        String parking = (current.getParking() == 1)? "Available" : "Unavailable";
        holder.tvParking.setText(parking);


        if(!current.getDescription().equalsIgnoreCase("")) {
            holder.tvDescription.setText(current.getDescription());
            holder.itemHikeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.tvDescription.getVisibility() == View.VISIBLE) {
                        holder.tvDescription.setVisibility(View.GONE);
                    } else {
                        holder.tvDescription.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount(){return hikes.size();}

    public class HikeViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHikeName, tvDate, tvLocation, tvParking, tvLength, tvDifficulty,tvDescription;
        public LinearLayout itemHikeLayout;

        public HikeViewHolder(@NonNull View itemView){
            super(itemView);
            tvHikeName = itemView.findViewById(R.id.tv_item_hike_name);
            tvDate = itemView.findViewById(R.id.tv_item_hike_date);
            tvLocation = itemView.findViewById(R.id.tv_item_hike_location);
            tvParking = itemView.findViewById(R.id.tv_item_hike_parking);
            tvLength = itemView.findViewById(R.id.tv_item_hike_length);
            tvDifficulty = itemView.findViewById(R.id.tv_item_hike_difficulty);
            tvDescription = itemView.findViewById(R.id.tv_item_hike_description);
            itemHikeLayout = itemView.findViewById(R.id.item_hike_layout);

        }
    }
}
