package com.example.m_hike.Observation;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Hike.Hike;
import com.example.m_hike.R;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder> {
    private ArrayList<Observation> observations;
    private DatabaseHelper db;
    public ObservationAdapter(ArrayList<Observation> observations, DatabaseHelper db){
        this.observations = observations;
        this.db = db;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_observation,parent,false);
        return new ObservationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder,int position){
        Observation current = observations.get(position);

        holder.tvObservation.setText(current.getObservation());
        holder.tvDate.setText(current.getDate());
        holder.tvType.setText(current.getType());
        holder.tvDescription.setText(current.getDescription());

        clickDeleteButton(holder,current,position);
    }

    @Override
    public int getItemCount(){return observations.size();}

    public class ObservationViewHolder extends RecyclerView.ViewHolder{
        public TextView tvObservation, tvDate, tvType, tvDescription;
        private ImageButton btn_edit, btn_delete;

        public ObservationViewHolder(@NonNull View view){
            super(view);
            tvObservation = view.findViewById(R.id.tv_item_observation);
            tvDate = view.findViewById(R.id.tv_item_observation_date);
            tvType = view.findViewById(R.id.tv_item_observation_type);
            tvDescription = view.findViewById(R.id.tv_item_observation_description);

            btn_delete = view.findViewById(R.id.btn_delete_observation);

        }


    }
    private void clickDeleteButton(@NonNull ObservationViewHolder holder, Observation observation, int position){
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog alertDialog = createDialog(context,observation,position);
                alertDialog.show();
            }
        });
    }
    AlertDialog createDialog(Context context, Observation current, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.title_delete_hike);

        String message = context.getString(R.string.msg_delete_hike_confirmation) + " " + current.getObservation() + "?";
        builder.setMessage(message);

        builder.setPositiveButton(R.string.btn_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteObservation(current.getId());
                observations.remove(position);
                notifyItemRemoved( position);
            }
        });

        builder.setNegativeButton(R.string.btn_cancel, null);

        return builder.create();
    }
}
