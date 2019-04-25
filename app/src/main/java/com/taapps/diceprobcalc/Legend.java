package com.taapps.diceprobcalc;

public class Legend {
    /**
     * Translates a substring to the corresponding integer value
     * @param x the substring
     * @return integer value corresponding to substring
     */
    public static int getInt(String x){
        int val = -1;
        switch(x){
            case "1": val = 1; break;
            case "2": val = 2; break;
            case "3": val = 3; break;
            case "a": val = 4; break;
            case "A": val = 4; break;
            case "attack": val = 4; break;
            case "h": val = 5; break;
            case "H": val = 5; break;
            case "heal": val = 5; break;
            case "e": val = 6; break;
            case "E": val = 6; break;
            case "energy": val = 6; break;
            case "w": val = 7; break;
            case "W": val = 7; break;
            case "any": val = 7; break;
            default: System.out.println("Error in roll setting"); System.exit(0);
        }
        return val;

    }
}
