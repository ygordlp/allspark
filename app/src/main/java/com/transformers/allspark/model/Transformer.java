package com.transformers.allspark.model;

/**
 * Class that represents a transformer.
 */
public class Transformer {

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

    /** An image URL that represents what team the Transformer is on. */
    private String team_icon;

    /**
     * Gets Transformer's id.
     * @return Unique id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets Transformer's id
     * @param id A unique int value for id.
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCourage() {
        return courage;
    }

    public void setCourage(int courage) {
        this.courage = courage;
    }

    public int getFirepower() {
        return firepower;
    }

    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public String getTeam_icon() {
        return team_icon;
    }

    public void setTeam_icon(String team_icon) {
        this.team_icon = team_icon;
    }
}
