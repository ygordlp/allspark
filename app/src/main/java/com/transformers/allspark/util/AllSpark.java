package com.transformers.allspark.util;

import android.support.annotation.NonNull;

import com.transformers.allspark.model.Transformer;

import java.util.Random;

/**
 * The AllSpark. Contains all information about Transformers.
 * <p>
 * Generates Transformers.
 */
public class AllSpark {

    private static final String AUTOBOTS_FOLDER = "file:///android_asset/Autobots/";
    private static final String DECEPTICONS_FOLDER = "file:///android_asset/Decepticons/";

    private Random random = new Random();

    private String[] autobotsNames;
    private String[] decepticonsNames;

    /**
     * AllSpark constructor.
     * Requires the list of all possible transformers names.
     *
     * @param autobotsNames    The list of all possible Autobots names.
     * @param decepticonsNames The List of all possible Deceptions names.
     */
    public AllSpark(@NonNull String[] autobotsNames, @NonNull String[] decepticonsNames) {
        this.autobotsNames = autobotsNames;
        this.decepticonsNames = decepticonsNames;
    }

    /**
     * Generate a random transformer.
     *
     * @param team Valid team Transformer.TEAM_AUTOBOTS or Transformer.TEAM_DECEPTICONS
     * @return
     */
    public Transformer randomGenerate(String team) {
        Transformer transformer = new Transformer();
        transformer.setTeam(team);
        transformer.setName(getRandomName(transformer.getTeam()));
        transformer.setStrength(randomValue(1,10));
        transformer.setIntelligence(randomValue(1,10));
        transformer.setSpeed(randomValue(1,10));
        transformer.setEndurance(randomValue(1,10));
        transformer.setRank(randomValue(1,10));
        transformer.setCourage(randomValue(1,10));
        transformer.setFirepower(randomValue(1,10));
        transformer.setSkill(randomValue(1,10));


        return transformer;
    }

    /**
     * Gets a random names from the possible names in the team.
     *
     * @param team Valid team Transformer.TEAM_AUTOBOTS or Transformer.TEAM_DECEPTICONS
     * @return Random name.
     */
    private String getRandomName(String team){
        String[] names;
        if(team.equals(Transformer.TEAM_AUTOBOTS)){
            names = autobotsNames;
        } else {
            names = decepticonsNames;
        }

        int max = names.length - 1;
        int randomIndex = randomValue(0, max);

        return names[randomIndex];
    }

    /**
     * Generates a random number in the possible range.
     *
     * @param min Minimum possible value.
     * @param max Maximum possible value.
     * @return A random number between min and max (inclusive).
     */
    private int randomValue(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
