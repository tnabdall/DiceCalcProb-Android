package com.taapps.diceprobcalc;

public class Dice {

    private Die[] dice;
    private int faceMin;
    private int faceMax;

    /**
     * Creates an array of 6 1-6 dice
     */
    public Dice(){
        dice = new Die[6];
        for (int i = 0; i< dice.length; i++){
            dice[i] = new Die(1,6);
            dice[i].rollDie();
        }
    }

    /**
     * Used to set up new dice
     * @param numDice how many dice you would like
     * @param maxFace The maximum face value on a die
     * @param minFace The minimum face value on a die
     */
    public Dice(int numDice, int minFace, int maxFace){
        if(numDice>0 && minFace>-1 && maxFace>minFace) {
            this.faceMin = minFace;
            this.faceMax = maxFace;
            dice = new Die[numDice];
            for (int i = 0; i < dice.length; i++) {
                dice[i] = new Die(minFace, maxFace);
                dice[i].rollDie();
            }
        }
    }

    /**
     * Rolls the dice specified by the boolean array
     * @param rollThis An array specifying which dice to reroll
     */
    public void rollDice(boolean[] rollThis){
        for (int i = 0; i<dice.length; i++){
            if (rollThis[i]==true && rollThis.length == dice.length){
                dice[i].rollDie();
            }
        }
    }

    /**
     * Allows us to set the value of a die
     * @param diceNum index in Array
     * @param diceValue 1,2,3, 4 for att, 5 for he, 6 for en, 7 for any
     */
    public void setDice(int diceNum, int diceValue){
        if (diceNum>-1&&diceNum<dice.length&&(diceValue==Die.WILDCARD || (diceValue>=faceMin&&diceValue<=faceMax))){
            dice[diceNum].setDie(diceValue);
        }
    }

    /**
     * Gives an array of all dice values (1-7)
     * @return integer array of all dice values
     */
    public int[] getDiceValues(){
        int[] array = new int[dice.length];
        for (int i = 0; i<dice.length; i++){
            array[i] = dice[i].getDie();
        }
        return array;
    }

    @Override
    public String toString() {
        String x = "";
        for (int i = 0; i< dice.length; i++){
            x=x.concat(Integer.toString(dice[i].getDie())).concat(" ");
        }
        return x;
    }
}
