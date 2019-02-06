package com.transformers.allspark.control;

import com.transformers.allspark.model.Transformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * All battle logic.
 */
public class Battle {

    public static final String AUTOBOTS = "Autobots";
    public static final String DECEPTICONS = "Deceptisons";
    public static final String AUTOBOTS_BOSS = "Optimus Prime";
    public static final String DECEPTICONS_BOSS = "Predaking";

    /**
     * Battle result.
     */
    public class Result {
        /** Number of battles that happened. */
        public int battleCount = 0;
        /** True when Optimus Prime fights Predaking. */
        public boolean allDestroyed = false;
        /** True in case of a tie. */
        public boolean tie = false;
        /** The name of the winner team. */
        public String winnerTeamName;
        /** The name of the loser team. */
        public String loserTeamName;
        /** List of the names of the winners. */
        public List<String> winners;
        /** List of the names of the survivors of the loser team. */
        public List<String> survivors;
        /** True if there is not enough Transformers to start a battle. */
        public boolean invalid = false;
    }

    private List<Transformer> autobots = new ArrayList<>();
    private List<Transformer> decepticons = new ArrayList<>();

    /**
     * Creates a battle with the given transformers.
     * The list will be separated in autobots and decepticons.
     * @param allTransformers
     */
    public Battle(List<Transformer> allTransformers){
        for(Transformer t : allTransformers){
            if(t.getTeam().equals(Transformer.TEAM_AUTOBOTS)){
                autobots.add(t);
            } else {
                decepticons.add(t);
            }
        }
    }

    /**
     * Checks if there is enough Transformers to start battle;
     *
     * @return True if it is possible to battle.
     */
    private boolean isValid(){
        return autobots.size() > 0 && decepticons.size() > 0;
    }

    /**
     * Gets the fight results between an Autobot and a Decepticon.
     * A battle between opponents uses the following rules:
     *  - If any fighter is down 4 or more points of courage and 3 or more points of strength
     * compared to their opponent, the opponent automatically wins the face-off regardless of
     * overall rating (opponent has ran away)
     *  - Otherwise, if one of the fighters is 3 or more points of skill above their opponent, they win
     * the fight regardless of overall rating
     *  - The winner is the Transformer with the highest overall rating
     *
     * @param a Autobot Transformer
     * @param d Decepticon Transformer.
     * @return Winner Transformer, null if battle is a tie.
     */
    protected Transformer fight(Transformer a, Transformer d){
        Transformer winner;

        if((a.getCourage() - d.getCourage() >= 4) && (a.getStrength() - d.getStrength() >= 3)){
            winner = a;
        } else if((d.getCourage() - a.getCourage() >= 4) && (d.getStrength() - a.getStrength() >= 3)){
            winner = d;
        } else if(a.getSkill() - d.getSkill() >= 3) {
            winner = a;
        } else if(d.getSkill() - a.getSkill() >= 3) {
            winner = d;
        } else {
            if(a.getOverallRating() == d.getOverallRating()){
                winner = null;
            } else if(a.getOverallRating() > d.getOverallRating()){
                winner = a;
            } else {
                winner = d;
            }
        }

        return winner;
    }

    /**
     * Starts the battle.
     * @return The battle results (winners, survivors ...)
     */
    public Result getBattleResult(){
        Result result = new Result();
        if(!isValid()){
            result.invalid = true;
            return result;
        }

        SortByRank comparator = new SortByRank();
        Collections.sort(autobots, comparator);
        Collections.sort(decepticons, comparator);

        int smallestSize = Math.min(autobots.size(), decepticons.size());

        List<Transformer> autobotsSurvivors = new ArrayList<>();
        List<Transformer> decepticonsSurvivors = new ArrayList<>();

        Transformer a, d, w;
        for(int i = 0; i < smallestSize; i++){
            a = autobots.get(i);
            d = decepticons.get(i);
            result.battleCount++;

            if(a.getName().equals(AUTOBOTS_BOSS)){
                if(d.getName().equals(DECEPTICONS_BOSS)){
                    result.allDestroyed = true;
                    break;
                } else {
                    autobotsSurvivors.add(a);
                }
            } else if(d.getName().equals(DECEPTICONS_BOSS)){
                decepticonsSurvivors.add(d);
            } else {
                w = fight(a, d);
                if(w != null){
                    if(w.getTeam().equals(Transformer.TEAM_AUTOBOTS)) {
                        autobotsSurvivors.add(a);
                    } else {
                        decepticonsSurvivors.add(d);
                    }
                }
            }
        }

        if(!result.allDestroyed){
            if(autobotsSurvivors.size() > decepticonsSurvivors.size()){
                result.winnerTeamName = AUTOBOTS;
                result.loserTeamName = DECEPTICONS;
                result.winners = getNamesFromList(autobotsSurvivors);
                result.survivors = getNamesFromList(decepticonsSurvivors);
            } else if(decepticonsSurvivors.size() > autobotsSurvivors.size()){
                result.winnerTeamName = DECEPTICONS;
                result.loserTeamName = AUTOBOTS;
                result.winners = getNamesFromList(decepticonsSurvivors);
                result.survivors = getNamesFromList(autobotsSurvivors);
            } else {
                result.tie = true;
            }
        }

        return result;
    }

    /**
     * Retrieve the names of the Transformers and put them on a list.
     * @param transformers Transformers list.
     * @return List with all he names
     */
    protected List<String> getNamesFromList(List<Transformer> transformers){
        List<String> names = new ArrayList<>();

        for(Transformer t : transformers){
            names.add(t.getName());
        }

        return names;
    }

    /**
     * Comparator to sort Transformers by rank.
     */
    private class SortByRank implements Comparator<Transformer> {

        @Override
        public int compare(Transformer transformer1, Transformer transformer2) {
            return transformer1.getRank() - transformer2.getRank();
        }
    }
}
