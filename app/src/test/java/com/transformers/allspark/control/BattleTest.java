package com.transformers.allspark.control;

import com.transformers.allspark.model.Transformer;

import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for Battle.
 */
public class BattleTest {

    @Test
    public void no_transformers(){
        Battle battle = new Battle(new ArrayList<>());
        Battle.Result result = battle.getBattleResult();

        assertTrue(result.invalid );
    }

    @Test
    public void not_enough_transformers(){
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(create("Test", "A", 2, 3, 4, 5, 6, 7, 8, 9));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertTrue(result.invalid );
    }

    @Test
    public void all_same_team(){
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(create("Test", "A", 2, 3, 4, 5, 6, 7, 8, 9));
        transformers.add(create("Test2", "A", 2, 3, 4, 5, 6, 7, 8, 9));
        transformers.add(create("Test3", "A", 2, 3, 4, 5, 6, 7, 8, 9));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertTrue(result.invalid );
    }


    /**
     * If any fighter is down 4 or more points of courage and 3 or more points of strength
     * compared to their opponent, the opponent automatically wins the face-off regardless of
     * overall rating (opponent has ran away)
     */
    @Test
    public void battle_one_vs_one_case1(){
        List<Transformer> transformers = new ArrayList<>();
        String winner = "Good1";
        String loser = "Evil1";

        transformers.add(create(winner, "A", 2, 6, 4, 5, 6, 7, 8, 9));
        transformers.add(create(loser, "D", 2, 3, 4, 5, 6, 3, 8, 9));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertFalse(result.invalid);
        assertFalse(result.allDestroyed);
        assertFalse(result.tie);
        assertEquals(result.battleCount, 1);
        assertEquals(result.winners.get(0), winner);
        assertEquals(result.winnerTeamName, Battle.AUTOBOTS);
    }

    /**
     * Otherwise, if one of the fighters is 3 or more points of skill above their opponent, they win
     * the fight regardless of overall rating
     */
    @Test
    public void battle_one_vs_one_case2(){
        List<Transformer> transformers = new ArrayList<>();
        String winner = "Good1";
        String loser = "Evil1";

        transformers.add(create(winner, "A", 2, 3, 4, 5, 6, 7, 8, 9));
        transformers.add(create(loser, "D", 2, 3, 4, 5, 6, 7, 8, 3));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertFalse(result.invalid);
        assertFalse(result.allDestroyed);
        assertFalse(result.tie);
        assertEquals(result.battleCount, 1);
        assertEquals(result.winners.get(0), winner);
        assertEquals(result.winnerTeamName, Battle.AUTOBOTS);
    }

    /**
     * The winner is the Transformer with the highest overall rating
     */
    @Test
    public void battle_one_vs_one_case3(){
        List<Transformer> transformers = new ArrayList<>();
        String winner = "Good1";
        String loser = "Evil1";

        transformers.add(create(winner, "A", 2, 4, 4, 5, 6, 7, 8, 9));
        transformers.add(create(loser, "D", 2, 3, 4, 5, 6, 7, 8, 9));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertFalse(result.invalid);
        assertFalse(result.allDestroyed);
        assertFalse(result.tie);
        assertEquals(result.battleCount, 1);
        assertEquals(result.winners.get(0), winner);
        assertEquals(result.winnerTeamName, Battle.AUTOBOTS);
    }

    /**
     * TIE
     */
    @Test
    public void battle_one_vs_one_case4(){
        List<Transformer> transformers = new ArrayList<>();

        transformers.add(create("Good1", "A", 2, 3, 4, 5, 6, 7, 8, 9));
        transformers.add(create("Evil1", "D", 2, 3, 4, 5, 6, 7, 8, 9));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertFalse(result.invalid);
        assertFalse(result.allDestroyed);
        assertEquals(result.battleCount, 1);

        assertTrue(result.tie);
    }

    /**
     * Any Transformer named Optimus Prime or Predaking wins his fight automatically regardless of
     * any other criteria
     */
    @Test
    public void battle_one_vs_one_optimus_prime(){
        List<Transformer> transformers = new ArrayList<>();
        String winner = Battle.AUTOBOTS_BOSS;
        String loser = "Evil1";

        transformers.add(create(winner, "A", 1, 1, 1, 1, 1, 1, 1, 1));
        transformers.add(create(loser, "D", 2, 3, 4, 5, 6, 7, 8, 3));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertFalse(result.invalid);
        assertFalse(result.allDestroyed);
        assertFalse(result.tie);
        assertEquals(result.battleCount, 1);
        assertEquals(result.winners.get(0), winner);
        assertEquals(result.winnerTeamName, Battle.AUTOBOTS);
    }

    @Test
    public void battle_one_vs_one_predaking(){
        List<Transformer> transformers = new ArrayList<>();
        String winner = Battle.DECEPTICONS_BOSS;
        String loser = "Good1";

        transformers.add(create(loser, "A", 1, 1, 1, 1, 1, 1, 1, 1));
        transformers.add(create(winner, "D", 2, 3, 4, 5, 6, 7, 8, 3));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertFalse(result.invalid);
        assertFalse(result.allDestroyed);
        assertFalse(result.tie);
        assertEquals(result.battleCount, 1);
        assertEquals(result.winners.get(0), winner);
        assertEquals(result.winnerTeamName, Battle.DECEPTICONS);
    }

    /**
     * In the event either of the above face each other (or a duplicate of each other), the game
     * immediately ends with all competitors destroyed
     */
    @Test
    public void optimus_prime_vs_predaking(){
        List<Transformer> transformers = new ArrayList<>();

        transformers.add(create(Battle.AUTOBOTS_BOSS, "A", 1, 1, 1, 1, 1, 1, 1, 1));
        transformers.add(create(Battle.DECEPTICONS_BOSS, "D", 2, 3, 4, 5, 6, 7, 8, 3));
        Battle battle = new Battle(transformers);
        Battle.Result result = battle.getBattleResult();

        assertFalse(result.invalid);
        assertFalse(result.tie);
        assertEquals(result.battleCount, 1);

        assertTrue(result.allDestroyed);
    }

    private Transformer create(String name, String team, int rank, int strength, int intelligence,
                               int speed, int endurance, int courage, int firepower, int skill){
        Transformer t = new Transformer();
        t.setName(name);
        t.setTeam(team);
        t.setRank(rank);
        t.setStrength(strength);
        t.setIntelligence(intelligence);
        t.setSpeed(speed);
        t.setEndurance(endurance);
        t.setCourage(courage);
        t.setFirepower(firepower);
        t.setSkill(skill);

        return t;
    }
}
