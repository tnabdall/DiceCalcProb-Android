package com.taapps.diceprobcalc;



import java.io.Serializable;

public class OptionSet implements Serializable {

    private int numberSims = 100000;
    private int numberDice = 6;
    private int maxFace = 6;
    private boolean sumMode = false;



    public int getNumberSims() {
        return numberSims;
    }

    public void setNumberSims(int numberSims) {
        if(numberSims>10 && numberSims<1000000000) {
            this.numberSims = numberSims;
        }
    }

    public int getNumberDice() {
        return numberDice;
    }

    public void setNumberDice(int numberDice) {
        if(numberDice>0 && numberDice<6) {
            this.numberDice = numberDice;
        }
    }

    public int getMaxFace() {
        return maxFace;
    }

    public void setMaxFace(int maxFace) {
        if(maxFace>0 && maxFace<21) {
            this.maxFace = maxFace;
        }
    }

    public boolean isSumMode() {
        return sumMode;
    }

    public void setSumMode(boolean sumMode) {
        this.sumMode = sumMode;
    }
}
