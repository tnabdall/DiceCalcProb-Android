package com.taapps.diceprobcalc;

import java.util.ArrayList;

public class DiceList extends ArrayList<Integer> {

    public DiceList(){
        super();
    }

    /**
     * See if the list contains the value
     * @param num Value to check
     * @return True or false
     */
    public boolean contains(int num){
        for(int i = 0; i<this.size(); i++){
            if(num == this.get(i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Counts how many times the number appears
     * @param num number to count
     * @return How many times it appears.
     */
    public int getCount(int num){
        int count = 0;
        for (int i = 0; i< this.size(); i++){
            if(num == this.get(i)){
                count++;
            }
        }
        return count;
    }

    /**
     * Gets a DiceList of all unique entries
     * @return DiceList of all unique entries
     */
    public DiceList getUnique(){
        DiceList unique = new DiceList();
        for (int i = 0; i<this.size(); i++){
            boolean thisUnique = true;
            for (int j = 0; j<i;j++){
                if (this.get(j)==this.get(i)){
                    thisUnique = false;
                    break;
                }
            }
            if(thisUnique){
                unique.add(this.get(i));
            }
        }
        return unique;
    }

}
