package com.transformers.allspark.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.transformers.allspark.R;
import com.transformers.allspark.model.Transformer;

import java.util.ArrayList;
import java.util.List;

public class TransformersRecyclerViewAdapter extends RecyclerView.Adapter<TransformersRecyclerViewAdapter.ViewHolder> {

    private List<Transformer> transformers = new ArrayList<>();
    private OnItemSelectedListener listener;

    public TransformersRecyclerViewAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setTransformers(List<Transformer> transformers) {
        this.transformers = transformers;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transformers_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.transformer = transformers.get(i);
        viewHolder.updateUI();
    }

    @Override
    public int getItemCount() {
        if (transformers == null) {
            return 0;
        } else {
            return transformers.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Transformer transformer;
        private TextView txtName;
        private TextView txtStrength;
        private TextView txtIntelligence;
        private TextView txtSpeed;
        private TextView txtEndurance;
        private TextView txtRank;
        private TextView txtCourage;
        private TextView txtFirepower;
        private TextView txtSkill;
        private ImageButton btnInfo;
        private ImageView imgTeamIcon;

        private ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtStrength = view.findViewById(R.id.txtStrength);
            txtIntelligence = view.findViewById(R.id.txtIntelligence);
            txtSpeed = view.findViewById(R.id.txtSpeed);
            txtEndurance = view.findViewById(R.id.txtEndurance);
            txtRank = view.findViewById(R.id.txtRank);
            txtCourage = view.findViewById(R.id.txtCourage);
            txtFirepower = view.findViewById(R.id.txtFirepower);
            txtSkill = view.findViewById(R.id.txtSkill);
            btnInfo = view.findViewById(R.id.btnInfo);
            imgTeamIcon = view.findViewById(R.id.imgTeamIcon);
        }

        @SuppressLint("SetTextI18n")
        private void updateUI() {
            txtName.setText(transformer.getName());
            txtStrength.setText(Integer.toString(transformer.getStrength()));
            txtIntelligence.setText(Integer.toString(transformer.getIntelligence()));
            txtSpeed.setText(Integer.toString(transformer.getSpeed()));
            txtEndurance.setText(Integer.toString(transformer.getEndurance()));
            txtRank.setText(Integer.toString(transformer.getRank()));
            txtCourage.setText(Integer.toString(transformer.getCourage()));
            txtFirepower.setText(Integer.toString(transformer.getFirepower()));
            txtSkill.setText(Integer.toString(transformer.getSkill()));
            Picasso.get()
                    .load(transformer.getTeam_icon())
                    .error(R.drawable.card_placeholder)
                    .into(imgTeamIcon);

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelected(transformer);
                }
            });
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Transformer item);
    }

}
