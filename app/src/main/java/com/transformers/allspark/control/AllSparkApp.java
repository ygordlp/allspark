package com.transformers.allspark.control;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.model.Transformers;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class.
 * Holds application states and data.
 */
public class AllSparkApp extends Application implements TransformersAPI.ApiReadyListener {

    private static final String TAG = "AllSparkApp";

    public interface LoaderListener {
        void onLoadFinished();
    }

    private LoaderListener listener;
    private TransformersAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /** Initialize everything, load api. */
    public void init(@NonNull LoaderListener listener) {
        this.listener = listener;
        Log.d(TAG, "Starting API.");
        //api = new TransformersAPI(this);
        api = new SampleDataAPI(this);
    }

    public TransformersAPI getTransformersAPI(){
        return this.api;
    }

    @Override
    public void onReady() {
        Log.d(TAG, "API ready");
        listener.onLoadFinished();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

}
