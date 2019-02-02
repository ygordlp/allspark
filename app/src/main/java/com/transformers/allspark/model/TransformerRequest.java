package com.transformers.allspark.model;

public class TransformerRequest {

    /** Uniquely generated ID. */
    public int id;

    /** Transformer name. */
    public String name;

    /** Transformer team, either “A” or “D” (Autobot or Decepticon). */
    public String team;

    /** Strength value, must be between 1 and 10. */
    public int strength;

    /** Intelligence value, must be between 1 and 10. */
    public int intelligence;

    /** Speed value, must be between 1 and 10. */
    public int speed;

    /** Endurance value, must be between 1 and 10. */
    public int endurance;

    /** Rank value, must be between 1 and 10. */
    public int rank;

    /**  Courage value, must be between 1 and 10. */
    public int courage;

    /** Firepower value, must be between 1 and 10. */
    public int firepower;

    /** Skill value, must be between 1 and 10. */
    public int skill;

}
