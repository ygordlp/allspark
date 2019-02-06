package com.transformers.allspark.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transformers.allspark.R;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.control.Battle;
import com.transformers.allspark.control.TransformersAPI;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BattleFragment extends Fragment implements TransformersAPI.DataSetChangeListener, View.OnClickListener {


    private Battle battle;
    private TransformersAPI api;
    private TextView txtResult;

    public BattleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle, container, false);

        AllSparkApp app = (AllSparkApp) getActivity().getApplication();
        api = app.getTransformersAPI();
        api.addDataSetChangeListener(this);

        battle = new Battle(api.getAllTransformers());

        view.findViewById(R.id.btnStartBattle).setOnClickListener(this);
        txtResult = view.findViewById(R.id.txtResult);

        return view;
    }

    @Override
    public void onDataSetChanged() {
        battle = new Battle(api.getAllTransformers());
    }

    @Override
    public void onClick(View view) {
        Battle.Result result = battle.getBattleResult();

        if(result.invalid){
            txtResult.setText(R.string.str_not_enough);
            return;
        }

        StringBuilder sb = new StringBuilder();
        String battle = (result.battleCount > 1)? getString(R.string.str_battles) : getString(R.string.str_battle);
        sb.append(String.format(battle, result.battleCount));
        sb.append("\n");

        if(result.allDestroyed) {
            sb.append(getString(R.string.str_all_destroyed));
        } else if(result.tie) {
            sb.append(getString(R.string.str_battle_tie));
        } else {
            String strWinners = getString(R.string.str_winners);
            sb.append(String.format(strWinners, result.winnerTeamName, toSingleString(result.winners)));
            sb.append("\n");
            String strSurvivors;
            if(result.survivors.size() > 0) {
                strSurvivors = toSingleString(result.survivors);
            } else {
                strSurvivors = getString(R.string.str_no_survivors);
            }
            sb.append(String.format( getString(R.string.str_losers), result.loserTeamName, strSurvivors));

        }

        txtResult.setText(sb.toString());
    }

    private String toSingleString(List<String> list){
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        int count = 0;
        for(String s : list){
            sb.append(s);
            count++;
            if(count >= size){
                sb.append(".");
            } else {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
