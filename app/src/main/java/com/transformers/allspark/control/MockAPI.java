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

    List<Transformer> transformers;
    AllSpark allSpark;

    /**
     * MOCKS TransformersAPI that contains all data.
     *
     * @param allSparkApp The AllSparkApp instance.
     */
    public MockAPI(@NonNull AllSparkApp allSparkApp) {
        super(allSparkApp);
        allSpark = allSparkApp.getAllSpark();
        generateRandomTransformers();
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
            } else {
                t = allSpark.randomGenerate(Transformer.TEAM_DECEPTICONS);
            }

            t.setId(Integer.toString(i));
            transformers.add(t);
        }
    }

    @Override
    public List<Transformer> getAllTransformers() {
        return transformers;
    }

    @Override
    public Transformer getTransformer(String id) {
        int i;
        try {
            i = Integer.parseInt(id);
        } catch (Exception e) {
            i = 0;
        }
        return transformers.get(i);
    }

    @Override
    public boolean addTransformer(Transformer transformer) {
        int id = transformers.size();
        transformer.setId(Integer.toString(id));
        transformers.add(transformer);
        notifyDataSetListeners();
        return true;
    }

    @Override
    public boolean editTransformer(Transformer transformer) {
        notifyDataSetListeners();
        return true;
    }

    @Override
    public boolean deleteTransformer(Transformer transformer) {
        if(transformers.remove(transformer)){
            notifyDataSetListeners();
            return true;
        } else {
            return false;
        }
    }
}
