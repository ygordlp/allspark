package com.transformers.allspark.control;

import android.support.annotation.NonNull;

import com.transformers.allspark.R;
import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.util.AllSpark;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for testing purpose.
 */
public class SampleDataAPI extends TransformersAPI {

    List<Transformer> transformers;
    AllSpark allSpark;

    /**
     * MOCKS TransformersAPI that contains all data.
     *
     * @param listener
     */
    public SampleDataAPI(@NonNull ApiReadyListener listener) {
        super(listener);
        String[] autobots = listener.getContext().getResources().getStringArray(R.array.Autobots);
        String[] decepticons = listener.getContext().getResources().getStringArray(R.array.Decepticons);
        allSpark = new AllSpark(autobots, decepticons);
        generateRandomTransformers();
    }

    /**
     * Generates 5 Transformers from each team;
     */
    private void generateRandomTransformers() {
        transformers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            transformers.add(allSpark.randomGenerate(Transformer.TEAM_AUTOBOTS));
            transformers.add(allSpark.randomGenerate(Transformer.TEAM_DECEPTICONS));
        }
    }

    @Override
    public List<Transformer> getAllTransformers() {
        return transformers;
    }
}
