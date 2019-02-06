package com.transformers.allspark.control;

import android.support.annotation.NonNull;

import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.util.AllSpark;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for testing purpose.
 */
public class MockAPI extends TransformersAPI {

    private static final String AUTOBOT_TEAM_ICON = "file:///android_asset/ic_autobot.png";
    private static final String DECEPTICON_TEAM_ICON = "file:///android_asset/ic_decepticon.png";

    private AllSpark allSpark;

    /**
     * MOCKS TransformersAPI that contains all data.
     *
     * @param allSparkApp The AllSparkApp instance.
     */
    public MockAPI(@NonNull AllSparkApp allSparkApp) {
        super(allSparkApp);
        allSpark = allSparkApp.getAllSpark();
    }

    @Override
    public boolean loadTransformers() {
        generateRandomTransformers();
        return true;
    }

    /**
     * Generates 5 Transformers from each team;
     */
    private void generateRandomTransformers() {
        transformers = new ArrayList<>();
        Transformer t;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                t = allSpark.randomGenerate(Transformer.TEAM_AUTOBOTS);
                t.setTeam_icon(AUTOBOT_TEAM_ICON);
            } else {
                t = allSpark.randomGenerate(Transformer.TEAM_DECEPTICONS);
                t.setTeam_icon(DECEPTICON_TEAM_ICON);
            }

            t.setId(t.getName()+Integer.toString(i));
            transformers.add(t);
        }
    }

    @Override
    public List<Transformer> getAllTransformers() {
        return transformers;
    }

    @Override
    public Transformer getTransformer(String id) {
        for(Transformer t : transformers){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    @Override
    public String addTransformer(Transformer transformer) {
        int id = transformers.size();
        transformer.setId(transformer.getName()+Integer.toString(id));
        if (transformer.getTeam().equals(Transformer.TEAM_AUTOBOTS)) {
            transformer.setTeam_icon(AUTOBOT_TEAM_ICON);
        } else {
            transformer.setTeam_icon(DECEPTICON_TEAM_ICON);
        }
        transformers.add(transformer);
        return transformer.getId();
    }

    @Override
    public boolean editTransformer(Transformer transformer) {
        return true;
    }

    @Override
    public boolean deleteTransformer(Transformer transformer) {
        return transformers.remove(transformer);
    }
}
