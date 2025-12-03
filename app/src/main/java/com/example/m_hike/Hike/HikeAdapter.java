package com.example.m_hike.Hike;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.DatabaseHelper.DatabaseHelper;
import com.example.m_hike.Observation.ObservationActivity;
import com.example.m_hike.R;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> {
    private ArrayList<Hike> hikes;
    private DatabaseHelper db;
    public HikeAdapter(ArrayList<Hike> hikes, DatabaseHelper db){
        this.hikes = hikes;
        this.db = db;
    }

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
        holder.tvLength.setText(current.getLength() + " km");
        holder.tvDifficulty.setText(current.getDifficulty());

        String parking = (current.getParking() == 1)? "Available" : "Unavailable";
        holder.tvParking.setText(parking);

        String completion = (current.getCompleted() == 1)? "Completed" : "Not Completed";
        holder.tvCompletion.setText(completion);

        int favorite = current.getFavorite();
        if(favorite == 1){
            holder.btn_favorite.setImageResource(R.drawable.favorite_24dp_ea3323_fill1_wght400_grad0_opsz24);
        } else {
            holder.btn_favorite.setImageResource(R.drawable.favorite_24dp_b7b7b7_fill0_wght400_grad0_opsz24);
        }

        enableDescription(holder,current);
        clickEditButton(holder,current);

        clickDeleteButton(holder,current,position);

//        Update favourite status
        holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite == 0){
                    current.setFavorite(1);
                    holder.btn_favorite.setImageResource(R.drawable.favorite_24dp_ea3323_fill1_wght400_grad0_opsz24);
                    db.updateFavoriteStatus(current.getId(),1);

                } else {
                    current.setFavorite(0);
                    holder.btn_favorite.setImageResource(R.drawable.favorite_24dp_b7b7b7_fill0_wght400_grad0_opsz24);
                    db.updateFavoriteStatus(current.getId(),0);
                }
            }
        });

//        Each hike card is a button. When clicked, it will show a list of observations
        holder.itemHikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ObservationActivity.class);
                intent.putExtra("HIKE_ID", current.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){return hikes.size();}

    public class HikeViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHikeName, tvDate, tvLocation, tvParking, tvLength, tvDifficulty, tvCompletion, tvDescription;
        public LinearLayout itemHikeLayout, itemHikeDescriptionLayout;
        public ImageButton btn_favorite, btn_edit_hike, btn_delete_hike, btn_description;

        public HikeViewHolder(@NonNull View itemView){
            super(itemView);
            tvHikeName = itemView.findViewById(R.id.tv_item_hike_name);
            tvDate = itemView.findViewById(R.id.tv_item_hike_date);
            tvLocation = itemView.findViewById(R.id.tv_item_hike_location);
            tvParking = itemView.findViewById(R.id.tv_item_hike_parking);
            tvLength = itemView.findViewById(R.id.tv_item_hike_length);
            tvDifficulty = itemView.findViewById(R.id.tv_item_hike_difficulty);
            tvCompletion = itemView.findViewById(R.id.tv_item_hike_completion);
            tvDescription = itemView.findViewById(R.id.tv_item_hike_description);

            itemHikeLayout = itemView.findViewById(R.id.item_hike_layout);
            itemHikeDescriptionLayout = itemView.findViewById(R.id.item_hike_description_layout);

            btn_favorite = itemView.findViewById(R.id.btn_favorite);
            btn_edit_hike = itemView.findViewById(R.id.btn_edit_hike);
            btn_delete_hike = itemView.findViewById(R.id.btn_delete_hike);
            btn_description = itemView.findViewById(R.id.btn_description);
        }
    }

//    If there is no description, then it will be hidden
    private void enableDescription(@NonNull HikeViewHolder holder, Hike current){
        if(!current.getDescription().equalsIgnoreCase("")) {
            holder.itemHikeDescriptionLayout.setVisibility(View.GONE);
            holder.tvDescription.setText(current.getDescription());

            holder.btn_description.setVisibility(View.VISIBLE);
            holder.btn_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visibility = holder.itemHikeDescriptionLayout.getVisibility();

                    if (visibility == View.VISIBLE) {
                        holder.btn_description.setImageResource(R.drawable.arrow_drop_down_24dp_b7b7b7_fill0_wght400_grad0_opsz24);
                        holder.itemHikeDescriptionLayout.setVisibility(View.GONE);
                    } else {
                        holder.btn_description.setImageResource(R.drawable.arrow_drop_up_24dp_b7b7b7_fill0_wght400_grad0_opsz24);
                        holder.itemHikeDescriptionLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void clickEditButton(@NonNull HikeViewHolder holder, Hike current){
        holder.btn_edit_hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EditHikeActivity.class);
                intent.putExtra("HIKE_ID", current.getId());
                context.startActivity(intent);
            }
        });
    }

    private void clickDeleteButton(@NonNull HikeViewHolder holder, Hike current, int position){
        holder.btn_delete_hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog dialog = createDialog(context,current,position);
                dialog.show();
            }
        });
    }

//    Show message box to confirm before deleting
    AlertDialog createDialog(Context context, Hike current, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.title_delete_hike);

        String message = context.getString(R.string.msg_delete_hike_confirmation) + " " + current.getName() + "?";
        builder.setMessage(message);

        builder.setPositiveButton(R.string.btn_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteHike(current.getId());
                hikes.remove(position);
                notifyItemRemoved( position);
            }
        });

        builder.setNegativeButton(R.string.btn_cancel, null);

        return builder.create();
    }
}
