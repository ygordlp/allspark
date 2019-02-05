package com.transformers.allspark.control;

import android.app.Application;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.transformers.allspark.R;
import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.util.AllSpark;

import java.util.List;

/**
 * Application class.
 * Holds application states and data.
 */
public class AllSparkApp extends Application {

    private static final String TAG = "AllSparkApp";

    public interface LoaderListener {
        void onLoadFinished();
    }

    private AllSpark allSpark;
    private LoaderListener listener;
    private TransformersAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Initialize everything, load api.
     */
    public void init(@NonNull LoaderListener listener) {
        this.listener = listener;
        Log.d(TAG, "Starting API.");

        allSpark = AllSpark.getInstance(getResources().getStringArray(R.array.Autobots),
                getResources().getStringArray(R.array.Decepticons));

        //api = new TransformersAPI(this);
        api = new MockAPI(this);
    }

    /**
     * Returns the Transformers API access.
     *
     * @return Transformers API
     */
    public TransformersAPI getTransformersAPI() {
        return this.api;
    }

    /**
     * Returns the AllSpark util class.
     *
     * @return AllSpark
     */
    public AllSpark getAllSpark() {
        return allSpark;
    }

    /**
     * To be called when API is ready
     */
    public void onApiReady() {
        Log.d(TAG, "API ready");
        new LoadTransformersTask().execute();
    }

    private void onTransformersLoaded(){
        listener.onLoadFinished();
    }

    /**
     * Async task to request API Token.
     */
    public class LoadTransformersTask extends AsyncTask<Void, Void, List<Transformer> > {

        @Override
        protected List<Transformer> doInBackground(Void... params) {
            Log.d(TAG, "Loading transformers");
            api.loadTransformers();
            return  api.getAllTransformers();
        }

        @Override
        protected void onPostExecute(List<Transformer> result) {
            Log.d(TAG, "Transformers loaded");
            onTransformersLoaded();
        }
    }

}
