package com.transformers.allspark.control;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.model.TransformerRequest;
import com.transformers.allspark.model.Transformers;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to communicate with the Transformers API.
 */
public class TransformersAPI {

    public interface ApiReadyListener {
        void onReady();
        Context getContext();
    }

    private ApiReadyListener listener;
    private String token = null;

    /**
     * TransformersAPI that contains all API interface to
     * @param listener
     */
    public TransformersAPI(@NonNull ApiReadyListener listener){
        this.listener = listener;
        init();
    }

    /**
     * Initialize API.
     */
    private void init(){
        TokenTask tokenTask = new TokenTask();
        tokenTask.execute();
    }

    /**
     * Gets a list of the Transformers created using the POST API.
     * @return A maximum list of 50 Transformers starting from the oldest created Transformer.
     */
    public List<Transformer> getAllTransformers(){
        //TODO: Get data

        return new ArrayList<>();
    }

    /**
     * Creates a Transformer with the provided data in the request body.
     *
     * @param request A valid TransformerRequest
     * @return True if added with success.
     */
    public boolean addTransformer(TransformerRequest request){
        return  false;
    }

    /**
     * Gets a Transformer based on a valid ID.
     *
     * @param id Valid id;
     * @return A Transformer if found, null otherwise.
     */
    public Transformer getTransformer(int id){
        return null;
    }

    /**
     * Async task to request API Token.
     */
    public class TokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String token = "TEST";

            return  token;
        }

        @Override
        protected void onPostExecute(String result) {
            token = result;
            listener.onReady();
        }
    }
}
