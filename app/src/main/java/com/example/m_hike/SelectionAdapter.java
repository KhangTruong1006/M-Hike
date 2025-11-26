package com.example.m_hike;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Hike.Hike;
import com.example.m_hike.Observation.ObservationActivity;
import com.example.m_hike.Observation.ObservationNewActivity;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder> {
    private ArrayList<Hike> hikes;
    private DatabaseHelper db;
    public SelectionAdapter(ArrayList<Hike> hikes, DatabaseHelper db){
        this.hikes = hikes;
        this.db = db;
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_selection,parent,false);
        return new SelectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectionViewHolder holder, int position){
        Hike current = hikes.get(position);

        holder.tvName.setText(current.getName());
        holder.tvLocation.setText(current.getLocation());
        holder.tvDifficulty.setText(current.getDifficulty());

        holder.hike_selection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ObservationActivity.class);
//                Intent intent = new Intent(context, ObservationNewActivity.class);
                intent.putExtra("HIKE_ID", current.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){return hikes.size();}

    public class SelectionViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvLocation, tvDifficulty, tvObservationCount;
        public LinearLayout hike_selection_layout;

        public SelectionViewHolder(@NonNull View view){
            super(view);
            tvName = view.findViewById(R.id.tv_hike_selection_name);
            tvLocation = view.findViewById(R.id.tv_hike_selection_location);
            tvDifficulty = view.findViewById(R.id.tv_hike_selection_difficulty);
            tvObservationCount = view.findViewById(R.id.tv_observation_count);

            hike_selection_layout = view.findViewById(R.id.hike_selection);
        }
    }
}
