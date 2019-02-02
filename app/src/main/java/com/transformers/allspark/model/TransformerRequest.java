package com.transformers.allspark.model;

public class TransformerRequest {

    /** Uniquely generated ID. */
    private int id;

    /** Transformer name. */
    private String name;

    /** Transformer team, either “A” or “D” (Autobot or Decepticon). */
    private String team;

    /** Strength value, must be between 1 and 10. */
    private int strength;

    /** Intelligence value, must be between 1 and 10. */
    private int intelligence;

    /** Speed value, must be between 1 and 10. */
    private int speed;

    /** Endurance value, must be between 1 and 10. */
    private int endurance;

    /** Rank value, must be between 1 and 10. */
    private int rank;

    /**  Courage value, must be between 1 and 10. */
    private int courage;

    /** Firepower value, must be between 1 and 10. */
    private int firepower;

    /** Skill value, must be between 1 and 10. */
    private int skill;

}
