package com.transformers.allspark.control;

import android.support.annotation.NonNull;

/**
 * Class used for testing purpose.
 */
public class SampleDataAPI extends TransformersAPI {

    /**
     * MOCKS TransformersAPI that contains all data.
     *
     * @param listener
     */
    public SampleDataAPI(@NonNull ApiReadyListener listener) {
        super(listener);
    }
}
