package com.transformers.allspark.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void setTransformers(List<Transformer> transformers){
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
        public Transformer transformer;
        public TextView txtStrength;
        public TextView txtIntelligence;
        public TextView txtSpeed;
        public TextView txtEndurance;
        public TextView txtRank;
        public TextView txtCourage;
        public TextView txtFirepower;
        public TextView txtSkill;
        public ImageButton btnInfo;
        public ImageView imgTeamIcon;

        public ViewHolder(View view) {
            super(view);
            txtStrength = view.findViewById(R.id.txtStrength);
            txtIntelligence = view.findViewById(R.id.txtIntelligence);
            txtSpeed = view.findViewById(R.id.txtSpeed);
            txtEndurance = view.findViewById(R.id.txtEndurance);
            txtRank = view.findViewById(R.id.txtRank);
            txtCourage = view.findViewById(R.id.txtCourage);
            txtFirepower = view.findViewById(R.id.txtFirepower);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + transformer.getName() + "'";
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Transformer item);
    }

}
