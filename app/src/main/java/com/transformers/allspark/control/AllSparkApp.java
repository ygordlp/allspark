package com.transformers.allspark.control;

import android.app.Application;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.transformers.allspark.BuildConfig;
import com.transformers.allspark.R;
import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.util.AllSpark;

import java.util.List;

/**
 * Application class.
 * Holds application states and data.
 */
public class AllSparkApp extends Application implements TransformersAPI.ApiListener {

    private static final String TAG = "AllSparkApp";

    public interface LoaderListener {
        void onLoadFinished();
        void onLoadFail(String reason);
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

        Log.d(TAG, "Use mock: " + BuildConfig.MOCK);
        if(BuildConfig.MOCK) {
            api = new MockAPI(this);
        } else {
            api = new TransformersAPI(this);
        }
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


    @Override
    public void onApiReady() {
        Log.d(TAG, "API ready");
        new LoadTransformersTask().execute();
    }

    @Override
    public void onApiFailed(int reason) {
        String strReason = "unknown";
        switch (reason){
            case TransformersAPI.REASON_TOKEN_FAIL:
                strReason = getString(R.string.str_token_fail);
                break;
            case TransformersAPI.REASON_LOAD_FAIL:
                strReason = getString(R.string.str_load_fail);
                break;
        }
        listener.onLoadFail(strReason);
    }

    /**
     * To be called when all Transformers were loaded.
     */
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
