package com.taapps.diceprobcalc;

import android.support.annotation.NonNull;

public class Die {

    private int face;
    private int faceMin=1;
    private int faceMax=6;
    public static int WILDCARD = 999;

    public Die(@NonNull int faceMin,@NonNull int faceMax){
        if (faceMin>-1 && faceMax>faceMin){
            this.faceMin = faceMin;
            this.faceMax = faceMax;
        }
    }

    /**
     * Gets the value of the die
     * @return value of die
     */
    public int getDie(){
        return face;
    }

    /**
     * Rolls the die between faceMin and faceMax inclusive
     */
    public void rollDie(){
        face = (int) (faceMin+Math.random()*(faceMax-faceMin+1));
    }

    /**
     * Sets die value to number allowed (1-7)
     * @param num Number to set (1-7)
     */
    public void setDie(int num){
        if (num==WILDCARD ||(num>=faceMin && num<=faceMax)){
            face = num;
        }
    }

    public int getFaceMin() {
        return faceMin;
    }

    public int getFaceMax() {
        return faceMax;
    }

    /**
     * Prints value of die to console
     */
    public String toString(){
        return Integer.toString(face);
    }
}
