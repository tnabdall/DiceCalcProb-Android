package com.taapps.diceprobcalc;

import java.util.ArrayList;
import java.util.Random;

public class ProbabilityCalculatorSum {

    private ArrayList<int[]> diceList = new ArrayList<int[]>();
    private int[] rolls;
    private int sumDesired;
    private boolean atLeastMode = false;

    private int turns = 0;
    private int successes = 0;

    public ProbabilityCalculatorSum(ArrayList<int[]> diceList, int sumDesired, boolean atLeastMode){
        for(int i = 0; i<diceList.size(); i++){
            int faceMin = diceList.get(i)[0];
            int faceMax = diceList.get(i)[1];
            if(faceMin<faceMax){
                this.diceList.add(new int[]{faceMin,faceMax});
            }
        }
        rolls = new int[diceList.size()];
        this.sumDesired = sumDesired;
        this.atLeastMode = atLeastMode;
    }

    private void rollDice(){
        if(diceList.size()>0){
            for (int i = 0; i<diceList.size(); i++){
                rolls[i] = diceList.get(i)[0] + new Random().nextInt(diceList.get(i)[1]-diceList.get(i)[0]+1);
            }
        }
    }

    public double Calculate(int sims){
        if(sims<1){
            return 0.0;
        }
        else{
            do {
                int sum = 0;
                rollDice();
                for (int i = 0; i < rolls.length; i++) {
                    sum += rolls[i];
                }
                if(atLeastMode && sum>=sumDesired){
                    successes+=1;
                }
                else if (!atLeastMode && sum==sumDesired){
                    successes+=1;
                }
                turns+=1;
            }while(turns<sims);
            double prob = (successes+0.000000000001)/turns;
            successes=0;
            turns=0;
            return prob;
        }
    }

}
