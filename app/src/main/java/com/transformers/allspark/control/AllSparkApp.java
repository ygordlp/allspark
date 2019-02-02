package com.transformers.allspark.control;

import android.app.Application;

import com.transformers.allspark.model.Transformers;

/**
 * Application class.
 * Holds application states and data.
 */
public class AllSparkApp extends Application implements TransformersAPI.ApiReadyListener {

    private TransformersAPI api;
    private Transformers transformers;

    @Override
    public void onCreate() {
        super.onCreate();
        api = new TransformersAPI(this);

    }

    public TransformersAPI getTransformersAPI(){
        return this.api;
    }

    @Override
    public void onReady() {

    }
}
