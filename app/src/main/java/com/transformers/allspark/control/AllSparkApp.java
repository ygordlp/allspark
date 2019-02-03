package com.transformers.allspark.control;

import android.app.Application;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.transformers.allspark.model.Transformers;

/**
 * Application class.
 * Holds application states and data.
 */
public class AllSparkApp extends Application implements TransformersAPI.ApiReadyListener {

    private static final String TAG = "AllSparkApp";

    public interface LoaderListener {
        void onLoadFinished(Transformers transformers);
    }

    private LoaderListener listener;
    private TransformersAPI api;
    private Transformers transformers;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /** Initialize everything, load api. */
    public void init(@NonNull LoaderListener listener) {
        this.listener = listener;
        Log.d(TAG, "Starting API.");
        api = new TransformersAPI(this);
    }

    public TransformersAPI getTransformersAPI(){
        return this.api;
    }

    @Override
    public void onReady() {
        Log.d(TAG, "API ready");
        transformers = api.getAllTransformers();
        new LoadTransformersTask().execute();
    }

    /**
     * Async task to request API Token.
     */
    public class LoadTransformersTask extends AsyncTask<Void, Void, Transformers> {

        @Override
        protected Transformers doInBackground(Void... params) {
            Log.d(TAG, "Loading transformers");
            transformers = api.getAllTransformers();

            return  transformers;
        }

        @Override
        protected void onPostExecute(Transformers result) {
            Log.d(TAG, "Transformers loaded");
            listener.onLoadFinished(result);
        }
    }
}
