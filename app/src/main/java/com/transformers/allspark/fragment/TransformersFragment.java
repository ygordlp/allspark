package com.transformers.allspark.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.transformers.allspark.R;
import com.transformers.allspark.activity.DetailActivity;
import com.transformers.allspark.adapter.TransformersRecyclerViewAdapter;
import com.transformers.allspark.adapter.TransformersRecyclerViewAdapter.OnItemSelectedListener;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.control.TransformersAPI;
import com.transformers.allspark.model.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransformersFragment extends Fragment implements OnItemSelectedListener, TransformersAPI.DataSetChangeListener {

    private static final String TAG = "TransformersFragment";
    private TransformersRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView message;
    private TransformersAPI api;

    public TransformersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transformers, container, false);

        recyclerView = view.findViewById(R.id.list);
        progressBar = view.findViewById(R.id.listLoading);
        message = view.findViewById(R.id.txtNoTransformers);

        adapter = new TransformersRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        try {
            AllSparkApp app = (AllSparkApp) getActivity().getApplication();
            api = app.getTransformersAPI();
            api.addDataSetChangeListener(this);
        } catch (NullPointerException e) {
            Log.e(TAG, "Null application.");
        }


        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBar.setVisibility(View.VISIBLE);
        message.setVisibility(View.INVISIBLE);

        new LoadTransformersTask().execute();

        return view;
    }

    public void onTransformersLoaded(@NonNull List<Transformer> transformers){
        adapter.setTransformers(transformers);
        adapter.notifyDataSetChanged();

        progressBar.setVisibility(View.INVISIBLE);
        if(transformers.isEmpty()){
            Log.d(TAG, "No transformers found");
            message.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, transformers.size() + " transformers found");
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemSelected(Transformer item) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.TRANSFORMER_ID, item.getId());
        intent.putExtra(DetailActivity.NEW_TRANSFORMER, false);

        startActivity(intent);
    }

    @Override
    public void onDataSetChanged() {
        new LoadTransformersTask().execute();
    }

    /**
     * Async task to request API Token.
     */
    public class LoadTransformersTask extends AsyncTask<Void, Void, List<Transformer> > {

        @Override
        protected List<Transformer> doInBackground(Void... params) {
            Log.d(TAG, "Loading transformers");
            return  api.getAllTransformers();
        }

        @Override
        protected void onPostExecute(List<Transformer> result) {
            Log.d(TAG, "Transformers loaded");
            if(result == null){
                result = new ArrayList<>();
            }
            onTransformersLoaded(result);
        }
    }

}
