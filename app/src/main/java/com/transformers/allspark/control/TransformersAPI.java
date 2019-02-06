package com.transformers.allspark.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.transformers.allspark.model.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to communicate with the Transformers API.
 */
public class TransformersAPI {

    private static final String SHARED_PREFERENCES = "com.transformers.allspark.SHARED_PREFERENCES";
    private static final String SAVED_TOKEN = "SAVED_TOKEN";
    private static final String AUTOBOTS_FOLDER = "file:///android_asset/Autobots/";
    private static final String DECEPTICONS_FOLDER = "file:///android_asset/Decepticons/";

    protected AllSparkApp allSparkApp;
    protected List<Transformer> transformers = new ArrayList<>();
    private String token = null;
    private List<DataSetChangeListener> listeners = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public interface DataSetChangeListener {
        void onDataSetChanged();
    }

    /**
     * TransformersAPI that contains all API interface to
     *
     * @param allSparkApp The AllSparkApp instance.
     */
    public TransformersAPI(@NonNull AllSparkApp allSparkApp) {
        this.allSparkApp = allSparkApp;
        this.sharedPreferences = allSparkApp.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        init();
    }

    /**
     * Initialize API.
     */
    private void init() {
        String savedToken = sharedPreferences.getString(SAVED_TOKEN, null);
        if(savedToken == null){
            TokenTask tokenTask = new TokenTask();
            tokenTask.execute();
        } else {
            token = savedToken;
            allSparkApp.onApiReady();
        }

    }

    /**
     * Adds a listener to be notified when data is changed
     * @param listener DataSetChangeListener to be added.
     */
    public void addDataSetChangeListener(DataSetChangeListener listener){
        listeners.add(listener);
    }

    /**
     * Removes a listener
     * @param listener DataSetChangeListener to be removed.
     */
    public void removeDataSetChangeListener(DataSetChangeListener listener){
        listeners.remove(listener);
    }

    /**
     * Notify all listeners.
      */
    protected void notifyDataSetListeners(){
        for(DataSetChangeListener listener : listeners){
            listener.onDataSetChanged();
        }
    }

    /**
     * Loads and cache all save Transformers from database.
     */
    public void loadTransformers(){
        notifyDataSetListeners();
    }

    /**
     * Gets a list of all loaded transformers Transformers created using the POST API.
     *
     * @return A maximum list of 50 Transformers starting from the oldest created Transformer.
     */
    public List<Transformer> getAllTransformers() {
        //TODO: Get data
        return transformers;
    }

    /**
     * Creates a Transformer with the provided data and saves it in the database.
     *
     * @param transformer A valid Transformer instance.
     * @return True if added with success.
     */
    public boolean addTransformer(Transformer transformer) {

        notifyDataSetListeners();
        return false;
    }

    /**
     * Saves edited Transformer in database.
     *
     * @param transformer Edited Transformer to be saved.
     * @return True if save with success.
     */
    public boolean editTransformer(Transformer transformer){
        return false;
    }

    /**
     * Gets a Transformer based on a valid ID.
     *
     * @param id Valid id;
     * @return A Transformer if found, null otherwise.
     */
    public Transformer getTransformer(String id) {
        return null;
    }

    /**
     * Async task to request API Token.
     */
    public class TokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String token = "TEST";

            return token;
        }

        @Override
        protected void onPostExecute(String result) {
            token = result;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SAVED_TOKEN, token);
            editor.apply();
            allSparkApp.onApiReady();
        }
    }

    /**
     * Gets the transformer image.
     *
     * @param transformer Transformer object with a valid name.
     * @return The address for the image.
     */
    public String getTransformerImage(Transformer transformer) {
        String result = null;
        if (transformer != null && transformer.getName() != null &&
                transformer.getTeam() != null) {
            String name = transformer.getName().replace(" ", "_");

            if (transformer.getTeam().equals(Transformer.TEAM_DECEPTICONS)) {
                result = DECEPTICONS_FOLDER + name + ".jpg";
            } else {
                result = AUTOBOTS_FOLDER + name + ".jpg";
            }
        }

        return result;
    }

    /**
     * Removes the Transformer from database.
     *
     * @param transformer Transformer to be deleted.
     * @return True if delete with success.
     */
    public boolean deleteTransformer(Transformer transformer){
        notifyDataSetListeners();
        return false;
    }
}
