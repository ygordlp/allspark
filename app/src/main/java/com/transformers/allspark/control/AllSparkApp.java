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
public class AllSparkApp extends Application {

    private static final String TAG = "AllSparkApp";
    private static final int REASON_TOKEN_FAIL = 1;
    private static final int REASON_LOAD_FAIL = 2;

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
        if (BuildConfig.MOCK) {
            api = new MockAPI(this);
        } else {
            api = new TransformersAPI(this);
        }

        new InitApiTask().execute();
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


    private void onApiReady() {
        Log.d(TAG, "API ready");
        new LoadTransformersTask().execute();
    }

    private void onApiFailed(final int reason) {
        String strReason = "unknown";
        switch (reason) {
            case REASON_TOKEN_FAIL:
                strReason = getString(R.string.str_token_fail);
                break;
            case REASON_LOAD_FAIL:
                strReason = getString(R.string.str_load_fail);
                break;
        }
        listener.onLoadFail(strReason);
    }

    /**
     * To be called when all Transformers were loaded.
     */
    private void onTransformersLoaded() {
        listener.onLoadFinished();
    }

    /**
     * Async task to load all Transformers.
     */
    public class LoadTransformersTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(TAG, "Loading transformers");
            return api.loadTransformers();
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                onTransformersLoaded();
            } else {
                onApiFailed(REASON_LOAD_FAIL);
            }

        }
    }

    /**
     * Async task to initialize API.
     */
    public class InitApiTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(TAG, "Initialize API");
            return api.init();
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                onApiReady();
            } else {
                onApiFailed(REASON_TOKEN_FAIL);
            }

        }
    }

}
