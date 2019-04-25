package com.taapps.diceprobcalc;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class ProbabilityCalculator {
    private Dice setDice; //Dice that we will roll to get desiredDice values
    private int[] setDiceValues; // Stores set dice values
    private int numberOfRolls = 1;
    private int turns = 0;
    private int successes = 0;
    private boolean isSetRoll = false;
    private DiceList desiredDice;
    private boolean[] reRoll;
    private int numWildcards = 0;

    public ProbabilityCalculator(int numberOfDice, int minFace, int maxFace, int numberOfRolls, ArrayList<Integer> desRoll, @Nullable ArrayList<Integer> setRoll) {
        // Initialize all dice and number of rolls
        if (desRoll.size() > 0 && numberOfRolls > 0) {
            this.numberOfRolls = numberOfRolls;
            desiredDice = new DiceList();
            for (int i = 0; i < numberOfDice; i++) {
                int roll = desRoll.get(i);
                if (roll == Die.WILDCARD || (roll >= minFace && roll <= maxFace)) {
                    desiredDice.add(roll);
                    if (roll == Die.WILDCARD) {
                        numWildcards += 1;
                    }
                }
            }
        }
        if (setRoll != null && setRoll.size() > 0) {
            isSetRoll = true;
        }
        setDice = new Dice(numberOfDice, minFace, maxFace);
        setDiceValues = new int[numberOfDice];
        // Sets the dice if we have a set roll
        if (isSetRoll) {
            for (int i = 0; i < numberOfDice; i++) {
                setDice.setDice(i, setRoll.get(i));
                setDiceValues[i] = setRoll.get(i);
            }
        } else { //Resets the dice if there is no set roll
            for (int i = 0; i < numberOfDice; i++) {
                setDice.setDice(i, Die.WILDCARD);
            }

        }
        // Sets up our reRoll comparison array, compareRolls initializes
        reRoll = new boolean[numberOfDice];
        compareRolls();
    }

    /**
     * Calculates the probability of the roll
     *
     * @param sims Number of simulations, recommended at least 100000
     * @return The probability of achieving the desired roll
     */
    public double calculate(int sims) {
        if (sims < 1) {
            return 0.0;
        }


        do {
            int rolls = 0;
            // Take required number of rolls
            compareRolls(); // Check to see what should be rolled the first time
            while (rolls < numberOfRolls) {
                setDice.rollDice(reRoll); // Rolls dice that need to be rolled
                compareRolls(); // Changes reroll array for next roll
                if (isSuccess()) {
                    successes += 1;
                    break;
                }
                rolls++;
            }
            turns++;
            //Reinitalize set roll dice
            if (isSetRoll) {
                for (int i = 0; i < setDice.getDiceValues().length; i++) {
                    setDice.setDice(i, setDiceValues[i]);
                }
            } else { // Reinitalizes to blank roll for not set roll
                for (int i = 0; i < setDice.getDiceValues().length; i++) {
                    setDice.setDice(i, Die.WILDCARD);
                }
            }
        } while (turns < sims);
        double probability = (successes + 0.0000000001) / turns;
        successes = 0;
        turns = 0;

        //Reinitalize set roll dice
        if (isSetRoll) {
            for (int i = 0; i < setDice.getDiceValues().length; i++) {
                setDice.setDice(i, setDiceValues[i]);
            }
        } else { // Reinitalizes to blank roll for not set roll
            for (int i = 0; i < setDice.getDiceValues().length; i++) {
                setDice.setDice(i, Die.WILDCARD);
            }
        }
        return probability;
    }

    /**
     * Checks to see if reroll boolean array is all false after using compareRolls(). If it is, then success.
     *
     * @return true or false
     */
    private boolean isSuccess() {
        compareRolls();
        for (int i = 0; i < reRoll.length; i++) {
            if (reRoll[i] == true) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares the dice roll to the desired dice roll and number of wildcard dice to update the reroll boolean[] array
     */
    private void compareRolls() {
        int WILDCARD = Die.WILDCARD;
        DiceList unique = desiredDice.getUnique(); //Gets all unique values
        DiceList rerollDice = new DiceList(); //Creates a new list to store dice that will be rerolled
        int[] setDiceValues = setDice.getDiceValues();
        int reRollLength = desiredDice.size(); // Stores number of dice that will be rerollen
        for (int i = 0; i < setDiceValues.length; i++) {
            int value = setDiceValues[i];
            if (value == Die.WILDCARD) {//Must roll any wildcards as they are not real faces
                reRoll[i] = true;
            } else if (!unique.contains(value)) { //Checks to see if value is needed
                reRoll[i] = true;
            } else if (rerollDice.getCount(value) < desiredDice.getCount(value)) { //Checks to see how much of value is needed vs what we have
                rerollDice.add(value); // Adds value to reroll list so we reroll the proper amount of dice
                reRoll[i] = false;
                reRollLength -= 1;
            } else {
                reRoll[i] = true;
            }
        }
        //Wildcard behaviour, wildcard value is 999
        // We want to check if number of wildcards on desired dice is equal to number of dice we have set to roll
        if (numWildcards == reRollLength) {
            for (int i = 0; i < reRoll.length; i++) {
                reRoll[i] = false;
            }
        }

    }

}

