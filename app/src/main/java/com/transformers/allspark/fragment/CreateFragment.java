package com.transformers.allspark.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.transformers.allspark.R;
import com.transformers.allspark.activity.DetailActivity;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.control.TransformersAPI;
import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.util.AllSpark;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment implements View.OnClickListener {

    private TransformersAPI api;
    private AllSpark allSpark;
    private ProgressDialog dialog;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        AllSparkApp app = (AllSparkApp) getActivity().getApplication();

        api = app.getTransformersAPI();
        allSpark = app.getAllSpark();

        view.findViewById(R.id.bntCreateAutobot).setOnClickListener(this);
        view.findViewById(R.id.bntCreateDecepticon).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bntCreateAutobot:
                createTransformer(Transformer.TEAM_AUTOBOTS);
                break;
            case R.id.bntCreateDecepticon:
                createTransformer(Transformer.TEAM_DECEPTICONS);
        }
    }

    private void createTransformer(String team) {
        dialog = ProgressDialog.show(getContext(), "", getString(R.string.lbl_creating), true);
        new CreateTask().execute(team);
    }

    private void onTransformerCreated(Transformer transformer) {
        if (dialog != null) {
            dialog.cancel();
        }
        Toast toast = Toast.makeText(getContext(), R.string.lbl_create_success, Toast.LENGTH_SHORT);
        toast.getView().setBackgroundResource(R.color.positive);
        toast.show();

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.TRANSFORMER_ID, transformer.getId());
        intent.putExtra(DetailActivity.NEW_TRANSFORMER, true);

        startActivity(intent);
    }

    private void onTransformerCreationError() {
        if (dialog != null) {
            dialog.cancel();
        }
        Toast toast = Toast.makeText(getContext(), R.string.lbl_create_fail, Toast.LENGTH_LONG);
        toast.getView().setBackgroundResource(R.color.negative);
        toast.show();
    }

    private class CreateTask extends AsyncTask<String, Void, Transformer> {

        @Override
        protected Transformer doInBackground(String... strings) {
            String team = strings[0];
            Transformer transformer = allSpark.randomGenerate(team);
            transformer.setId(api.addTransformer(transformer));
            return transformer;
        }

        @Override
        protected void onPostExecute(Transformer transformer) {
            if (transformer.getId() == null) {
                onTransformerCreationError();
            } else {
                onTransformerCreated(transformer);
            }
        }
    }
}
