package com.transformers.allspark.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.model.TransformerRequest;
import com.transformers.allspark.model.Transformers;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class responsible to communicate with the Transformers API.
 */
public class TransformersAPI {

    private static final String TAG = "TransformersAPI";
    private static final String SHARED_PREFERENCES = "com.transformers.allspark.SHARED_PREFERENCES";
    private static final String SAVED_TOKEN = "SAVED_TOKEN";
    private static final String AUTOBOTS_FOLDER = "file:///android_asset/Autobots/";
    private static final String DECEPTICONS_FOLDER = "file:///android_asset/Decepticons/";

    private static final String URL_TOKEN = "https://transformers-api.firebaseapp.com/allspark";
    private static final String URL_TRANSFORMERS = "https://transformers-api.firebaseapp.com/transformers";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_BEARER = "Bearer ";
    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
    public static  final int REASON_TOKEN_FAIL = 1;
    public static  final int REASON_LOAD_FAIL = 2;

    protected AllSparkApp allSparkApp;
    protected ApiListener listener;
    protected List<Transformer> transformers = new ArrayList<>();
    private String token = null;
    private SharedPreferences sharedPreferences;
    private OkHttpClient http = new OkHttpClient();
    private Gson gson = new Gson();


    /**
     * Interface for listener of Api events.
     */
    public interface ApiListener {
        /**
         * When api is ready for request.
         */
        void onApiReady();

        /**
         * When api fails.
         */
        void onApiFailed(int reason);
    }

    /**
     * TransformersAPI that contains all API interface to
     *
     * @param allSparkApp The AllSparkApp instance.
     */
    public TransformersAPI(@NonNull AllSparkApp allSparkApp) {
        this.allSparkApp = allSparkApp;
        this.listener = allSparkApp;
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
     * Loads and cache all save Transformers from database.
     */
    public void loadTransformers(){
        Request request = new Request.Builder()
                .url(URL_TRANSFORMERS)
                .addHeader(HEADER_AUTHORIZATION, getBearer())
                .build();
        try {
            Response response = http.newCall(request).execute();
            String body = response.body().string();
            Transformers transformersRequest = gson.fromJson(body, Transformers.class);
            transformers = transformersRequest.getTransformers();
        } catch (Exception e) {
            listener.onApiFailed(REASON_LOAD_FAIL);
            Log.e(TAG, "An error occurred while loading transformers: " + e.getMessage());
        }
    }

    private String getBearer(){
        return HEADER_BEARER + token;
    }

    /**
     * Gets a list of all loaded transformers Transformers created using the POST API.
     *
     * @return A maximum list of 50 Transformers starting from the oldest created Transformer.
     */
    public List<Transformer> getAllTransformers() {
        return transformers;
    }

    /**
     * Creates a Transformer with the provided data and saves it in the database.
     *
     * @param transformer A valid Transformer instance.
     * @return New transformer id.
     */
    public String addTransformer(Transformer transformer) {
        TransformerRequest tr = new TransformerRequest(transformer);
        String json = gson.toJson(tr);
        RequestBody requestBody = RequestBody.create(JSON_TYPE, json);
        Request request = new Request.Builder()
                .url(URL_TRANSFORMERS)
                .post(requestBody)
                .addHeader(HEADER_AUTHORIZATION, getBearer())
                .build();
        try {
            Response response = http.newCall(request).execute();
            String body = response.body().string();
            Transformer savedTransformer = gson.fromJson(body, Transformer.class);
            transformers.add(savedTransformer);
            return savedTransformer.getId();
        } catch (Exception e) {
            Log.e(TAG, "Error on saving transformer: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves edited Transformer in database.
     *
     * @param transformer Edited Transformer to be saved.
     * @return True if save with success.
     */
    public boolean editTransformer(Transformer transformer){
        TransformerRequest tr = new TransformerRequest(transformer);
        String json = gson.toJson(tr);
        RequestBody requestBody = RequestBody.create(JSON_TYPE, json);
        Request request = new Request.Builder()
                .url(URL_TRANSFORMERS)
                .put(requestBody)
                .addHeader(HEADER_AUTHORIZATION, getBearer())
                .build();
        try {
            Response response = http.newCall(request).execute();
            String body = response.body().string();
            Transformer savedTransformer = gson.fromJson(body, Transformer.class);
            if(transformer.getId().equals(savedTransformer.getId())){
                return true;
            } else {
                Log.e(TAG, "Server return different Transformer id");
                return false;
            }

        } catch (Exception e) {
            Log.e(TAG, "Error on saving transformer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a Transformer based on a valid ID.
     *
     * @param id Valid id;
     * @return A Transformer if found, null otherwise.
     */
    public Transformer getTransformer(String id) {
        for(Transformer t : transformers){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    /**
     * Async task to request API Token.
     */
    public class TokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            Request request = new Request.Builder()
                    .url(URL_TOKEN)
                    .build();
            String res = null;
            try {
                Response response = http.newCall(request).execute();
                res = response.body().string();
            } catch (Exception e) {
                Log.e(TAG, "An error occurred while retrieving token: " + e.getMessage());
            }

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == null){
                listener.onApiFailed(REASON_TOKEN_FAIL);
            } else {
                token = result;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SAVED_TOKEN, token);
                editor.apply();
                listener.onApiReady();
            }
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
        String id = transformer.getId();
        if(id == null){
            Log.e(TAG, "Null id on delete");
            return false;
        }
        String url = URL_TRANSFORMERS + "/" + id;
        Log.d(TAG, "Request delete URL: " + url);
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader(HEADER_AUTHORIZATION, getBearer())
                .build();
        try {
            Response response = http.newCall(request).execute();
            boolean ok = response.isSuccessful();
            if(ok){
                transformers.remove(transformer);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            Log.e(TAG, "Error on deleting transformer id: " + id + " error:" + e.getMessage());
            return false;
        }
    }
}
