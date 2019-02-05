package com.transformers.allspark.model;

import android.support.annotation.NonNull;

/**
 * Class to send and receive Transformer data.
 * To be use in conjunction with GSON.
 */
public class TransformerRequest {

    /** Uniquely generated ID. */
    public String id;

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

    public TransformerRequest(@NonNull Transformer fromTransformer){
        this.id = fromTransformer.getId();
        this.team = fromTransformer.getTeam();
        this.strength = fromTransformer.getStrength();
        this.intelligence = fromTransformer.getIntelligence();
        this.speed = fromTransformer.getSpeed();
        this.endurance = fromTransformer.getEndurance();
        this.rank = fromTransformer.getRank();
        this.courage = fromTransformer.getCourage();
        this.firepower = fromTransformer.getFirepower();
        this.skill = fromTransformer.getSkill();
    }

    /**
     * Converts the TransformerRequest to a Transformer object.
     *
     * @return Transformer object.
     */
    public Transformer toTransformer(){
        Transformer transformer = new Transformer();
        transformer.setId(id);
        transformer.setTeam(team);
        transformer.setName(name);
        transformer.setStrength(strength);
        transformer.setIntelligence(intelligence);
        transformer.setSpeed(speed);
        transformer.setEndurance(endurance);
        transformer.setRank(rank);
        transformer.setCourage(courage);
        transformer.setFirepower(firepower);
        transformer.setSkill(skill);

        return transformer;
    }

}
