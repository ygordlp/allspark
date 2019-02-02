package com.transformers.allspark.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the list of all created transformers.
 */
public class Transformers {

    private List<Transformer> transformers;

    /**
     * Gets all created transformers.
     *
     * @return All created transformers.
     */
    public List<Transformer> getTransformers(){
        if(this.transformers == null){
            this.transformers = new ArrayList<>();
        }

        return transformers;
    }
}
