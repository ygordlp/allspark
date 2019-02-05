package com.transformers.allspark.control;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.transformers.allspark.model.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to communicate with the Transformers API.
 */
public class TransformersAPI {

    private static final String AUTOBOTS_FOLDER = "file:///android_asset/Autobots/";
    private static final String DECEPTICONS_FOLDER = "file:///android_asset/Decepticons/";

    private AllSparkApp allSparkApp;
    private String token = null;

    /**
     * TransformersAPI that contains all API interface to
     *
     * @param allSparkApp The AllSparkApp instance.
     */
    public TransformersAPI(@NonNull AllSparkApp allSparkApp) {
        this.allSparkApp = allSparkApp;
        init();
    }

    /**
     * Initialize API.
     */
    private void init() {
        TokenTask tokenTask = new TokenTask();
        tokenTask.execute();
    }

    /**
     * Gets a list of the Transformers created using the POST API.
     *
     * @return A maximum list of 50 Transformers starting from the oldest created Transformer.
     */
    public List<Transformer> getAllTransformers() {
        //TODO: Get data

        return new ArrayList<>();
    }

    /**
     * Creates a Transformer with the provided data and saves it in the database.
     *
     * @param transformer A valid Transformer instance.
     * @return True if added with success.
     */
    public boolean addTransformer(Transformer transformer) {
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
}
