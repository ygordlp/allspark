package com.transformers.allspark.util;

import com.transformers.allspark.model.Transformer;

/**
 * The AllSpark. Contains all information about Transformers.
 *
 * Generates Transformers.
 */
public class AllSpark {

    private static final String AUTOBOTS_FOLDER = "file:///android_asset/Autobots/";
    private static final String DECEPTICONS_FOLDER = "file:///android_asset/Decepticons/";

    private String[] autobotsNames;
    private String[] decepticonsNames;

    /**
     * AllSpark constructor.
     * Requires the list of all possible transformers names.
     *
     * @param autobotsNames The list of all possible Autobots names.
     * @param decepticonsNames The List of all possible Deceptions names.
     */
    public AllSpark(String[] autobotsNames, String[] decepticonsNames){
        this.autobotsNames = autobotsNames;
        this.decepticonsNames = decepticonsNames;
    }

    /**
     * Generate a random transformer.
     *
     * @return A Transformer.
     */
    public Transformer randomGenerate() {
        Transformer transformer = new Transformer();

        return transformer;
    }
}
