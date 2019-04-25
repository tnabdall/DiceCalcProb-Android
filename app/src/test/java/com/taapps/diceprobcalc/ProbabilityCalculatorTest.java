package com.taapps.diceprobcalc;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ProbabilityCalculatorTest {

    private ArrayList<Integer> setList = new ArrayList<Integer>();
    private ArrayList<Integer> desList = new ArrayList<Integer>();
    ProbabilityCalculator prob;

    @Test
    public void probOneDiceOneRollNoSet_isCorrect(){
        setList.clear();
        desList.clear();
        desList.add(5);
        prob = new ProbabilityCalculator(1,1,6,1,desList,null);
        assertEquals(0.166666,prob.calculate(1000000),0.03);
    }

    @Test
    public void probOneDiceTwoRollNoSet_isCorrect(){
        setList.clear();
        desList.clear();
        desList.add(5);
        prob = new ProbabilityCalculator(1,1,6,2,desList,null);
        double result = prob.calculate(1000000);
        assertEquals(11.0/36,result,0.03);
        System.out.print(result);
    }

    @Test
    public void probSixDiceThreeRollsNoSet123456_isCorrect(){
        setList.clear();
        desList.clear();
        desList.add(1);
        desList.add(2);
        desList.add(3);
        desList.add(4);
        desList.add(5);
        desList.add(6);
        prob = new ProbabilityCalculator(desList.size(),1,6,3,desList,null);
        double result = prob.calculate(1000000);
        assertEquals(0.197,result,0.03);
        System.out.print(result);
    }

    @Test
    public void probSixDiceThreeRollsNoSet111www_isCorrect(){
        setList.clear();
        desList.clear();
        desList.add(1);
        desList.add(1);
        desList.add(1);
        desList.add(Die.WILDCARD);
        desList.add(Die.WILDCARD);
        desList.add(Die.WILDCARD);
        prob = new ProbabilityCalculator(desList.size(),1,6,3,desList,null);
        double result = prob.calculate(1000000);
        assertEquals(0.5,result,0.03);
        System.out.print(result);
    }

}
