package com.transformers.allspark.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        public final View mView;
        public Transformer transformer;

        public ViewHolder(View view) {
            super(view);
            mView = view;
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
