package com.transformers.allspark.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transformers.allspark.R;
import com.transformers.allspark.adapter.TransformersRecyclerViewAdapter;
import com.transformers.allspark.adapter.TransformersRecyclerViewAdapter.OnItemSelectedListener;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.control.TransformersAPI;
import com.transformers.allspark.model.Transformer;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransformersFragment extends Fragment implements OnItemSelectedListener {

    private static final String TAG = "TransformersFragment";
    private TransformersRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private TransformersAPI api;

    public TransformersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transformers, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            AllSparkApp app = (AllSparkApp) getActivity().getApplication();
            adapter = new TransformersRecyclerViewAdapter(this);
            recyclerView.setAdapter(adapter);
            api = app.getTransformersAPI();
        }

        return view;
    }

    public void onTransformersLoaded(List<Transformer> transformers){
        adapter.setTransformers(transformers);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(Transformer item) {

    }

    /**
     * Async task to request API Token.
     */
    public class LoadTransformersTask extends AsyncTask<Void, Void, List<Transformer> > {

        @Override
        protected List<Transformer> doInBackground(Void... params) {
            Log.d(TAG, "Loading transformers");
            List<Transformer> transformers = api.getAllTransformers();

            return  transformers;
        }

        @Override
        protected void onPostExecute(List<Transformer>  result) {
            Log.d(TAG, "Transformers loaded");
            onTransformersLoaded(result);
        }
    }

}
