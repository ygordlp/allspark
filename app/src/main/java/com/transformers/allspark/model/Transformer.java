package com.transformers.allspark.model;

/**
 * Class that represents a transformer.
 */
public class Transformer {

    /** Default value for strings with invalid data. */
    private static final String NOT_SET = "NOT SET";

    /** Default value for integer properties with invalid set data. */
    private static final int INVALID_VALUE = 0;

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

    /**
     * Gets Transformer's name.
     * @return Transformer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets Transformer's name.
     * @param name A valid non-empty string.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets Transformer's team.
     * A - Autobot.
     * B - Decepticon.
     *
     * @return Transformer's team, either “A” or “D” (Autobot or Decepticon).
     */
    public String getTeam() {
        return team;
    }

    /**
     * Sets Transformer's team, either “A” or “D” (Autobot or Decepticon).
     * Default value A.
     * @param team Transformer's team.
     */
    public void setTeam(String team) {
        if(team == null || team.isEmpty() || (!team.equals("A") && !team.equals("D"))){
            this.team = "A";
        } else {
            this.team = team;
        }
    }

    /**
     * Gets Transformer's strength, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's strength.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Sets Transformer's strength.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param strength Transformer's strength
     */
    public void setStrength(int strength) {
        if(inRange(strength)){
            this.strength = strength;
        } else {
            this.strength = INVALID_VALUE;
        }
    }

    /**
     * Gets Transformer's intelligence, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's intelligence.
     */
    public int getIntelligence() {
        return intelligence;
    }

    /**
     * Sets Transformer's intelligence.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param intelligence Transformer's intelligence
     */
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    /**
     * Gets Transformer's speed, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets Transformer's speed.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param speed Transformer's strength
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Gets Transformer's endurance, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's endurance.
     */
    public int getEndurance() {
        return endurance;
    }

    /**
     * Sets Transformer's endurance.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param endurance Transformer's endurance
     */
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    /**
     * Gets Transformer's rank, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's rank.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets Transformer's rank.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param rank Transformer's rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Gets Transformer's courage, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's courage.
     */
    public int getCourage() {
        return courage;
    }

    /**
     * Sets Transformer's courage.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param courage Transformer's courage
     */
    public void setCourage(int courage) {
        this.courage = courage;
    }

    /**
     * Gets Transformer's firepower, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's firepower.
     */
    public int getFirepower() {
        return firepower;
    }

    /**
     * Sets Transformer's firepower.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param firepower Transformer's firepower
     */
    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    /**
     * Gets Transformer's skill, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's skill.
     */
    public int getSkill() {
        return skill;
    }

    /**
     * Sets Transformer's skill.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param skill Transformer's skill
     */
    public void setSkill(int skill) {
        this.skill = skill;
    }

    /**
     * Gets Transformer's team icon, a value between 1 and 10.
     * A value of ZERO means that Transformer has invalid data.
     *
     * @return Transformer's team icon.
     */
    public String getTeam_icon() {
        return team_icon;
    }

    /**
     * Sets Transformer's team icon.
     * Must be a value between 1 and 10.
     *
     * A value of ZERO is set for invalid data.
     * @param team_icon Transformer's team icon
     */
    public void setTeam_icon(String team_icon) {
        this.team_icon = team_icon;
    }

    /**
     * The “overall rating” of a Transformer is the following formula:
     * (Strength + Intelligence + Speed + Endurance + Firepower).
     * @return The overall rating
     */
    public int getOverallRating(){
        return this.strength + this.intelligence + this.speed + this.endurance + this.firepower;
    }

    /**
     * Checks is a value is in the 1-10 range.
     *
     * @param i Value to be checked.
     * @return True if value is in range.
     */
    private boolean inRange(int i){
        return i > 0 && i <= 10;
    }
}
