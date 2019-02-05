package com.transformers.allspark.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transformers.allspark.R;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.control.Battle;

/**
 * A simple {@link Fragment} subclass.
 */
public class BattleFragment extends Fragment {


    private Battle battle;

    public BattleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle, container, false);

        AllSparkApp app = (AllSparkApp) getActivity().getApplication();
        //api = app.getTransformersAPI();

        return view;
    }

}
