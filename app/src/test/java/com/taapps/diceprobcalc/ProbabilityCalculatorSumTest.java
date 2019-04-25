package com.taapps.diceprobcalc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ProbabilityCalculatorSumTest {

    private ProbabilityCalculatorSum prob;

    @Test
    public void probTwoDice7(){

        ArrayList<int[]> diceList = new ArrayList<int[]>();
        diceList.add(new int[]{1,6});
        diceList.add(new int[]{1,6});

        prob = new ProbabilityCalculatorSum(diceList,7,false);

        assertEquals(6.0/36,prob.Calculate(100000),0.03);

    }

    @Test
    public void probTwoDice12(){

        ArrayList<int[]> diceList = new ArrayList<int[]>();
        diceList.add(new int[]{1,6});
        diceList.add(new int[]{1,6});

        prob = new ProbabilityCalculatorSum(diceList,12,true);

        assertEquals(1.0/36,prob.Calculate(100000),0.03);
    }

    @Test
    public void probTwoDiceAtLeast7(){

        ArrayList<int[]> diceList = new ArrayList<int[]>();
        diceList.add(new int[]{1,6});
        diceList.add(new int[]{1,6});

        prob = new ProbabilityCalculatorSum(diceList,7,true);

        assertEquals(0.58333,prob.Calculate(100000),0.03);

    }

    @Test
    public void probTwoDiceAtLeast12(){

        ArrayList<int[]> diceList = new ArrayList<int[]>();
        diceList.add(new int[]{1,6});
        diceList.add(new int[]{1,6});

        prob = new ProbabilityCalculatorSum(diceList,12,true);

        assertEquals(1.0/36,prob.Calculate(100000),0.03);
    }

}
