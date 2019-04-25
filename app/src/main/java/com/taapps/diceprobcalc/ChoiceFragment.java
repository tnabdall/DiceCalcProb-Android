package com.taapps.diceprobcalc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoiceFragment extends Fragment implements FragmentMethods {

    private int MAX_SPINNERS = 6; //Max spinners in layout (ie 6 dice per roll).
    private int NUM_DICE = 6;
    private int MIN_FACE = 1;
    private int MAX_FACE = 6;
    private int NUM_SIMULATIONS = 100000;

    public ChoiceFragment() {
        // Required empty public constructor
    }

    public void setNumDice(int numDice) {
        if(numDice>0&&numDice<=MAX_SPINNERS) {
            NUM_DICE = numDice;
        }
    }

    public void setMINMAXFACE(int minface, int maxface){
        if(minface>-1 && maxface>minface){
            MIN_FACE = minface;
            MAX_FACE = maxface;
        }
    }

    public void setNUM_SIMULATIONS(int NUM_SIMULATIONS) {
        if(NUM_SIMULATIONS>10&&NUM_SIMULATIONS<1000000000) {
            this.NUM_SIMULATIONS = NUM_SIMULATIONS;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View choiceView = inflater.inflate(R.layout.fragment_choice, container, false);

        //Set up all elements
        setUpSpinners(choiceView, null);
        setUpCheckBox(choiceView, false);
        setupButton(choiceView);

        return choiceView;
    }

    @Override
    public boolean isSameFragment(Fragment f) {
        return (f instanceof ChoiceFragment);
    }

    public void setupButton(View v) {
        // Setup references
        final Button button = (Button) v.findViewById(R.id.calculateButton);
        final TextView result = (TextView) v.findViewById(R.id.result);
        final EditText rollsText = (EditText) v.findViewById(R.id.numDiceRolls);

        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.setRollCheck);

        final ArrayList<Spinner> setSpinners = new ArrayList<Spinner>();
        final ArrayList<Spinner> desSpinners = new ArrayList<Spinner>();
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner1));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner2));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner3));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner4));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner5));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner6));

        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner1));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner2));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner3));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner4));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner5));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner6));



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> setRollChoices = null;
                ArrayList<Integer> desRollChoices = new ArrayList<Integer>();
                int rolls = Integer.parseInt(rollsText.getText().toString());
                if(checkBox.isChecked()){
                    setRollChoices = new ArrayList<Integer>();
                    for (int i = 0; i<setSpinners.size(); i++){
                        if (setSpinners.get(i).getVisibility() == View.VISIBLE){
                            setRollChoices.add(Integer.parseInt(setSpinners.get(i).getSelectedItem().toString()));
                        }
                    }
                }

                // Adds all desired roll elements to list
                for (int i = 0; i<desSpinners.size(); i++){
                    if (desSpinners.get(i).getVisibility() == View.VISIBLE){
                        desRollChoices.add(Integer.parseInt(desSpinners.get(i).getSelectedItem().toString()));
                    }
                }

                if(rolls>0){
                    ProbabilityCalculator prob = new ProbabilityCalculator(NUM_DICE, MIN_FACE, MAX_FACE, rolls,desRollChoices,setRollChoices);
                    double answer = prob.calculate(NUM_SIMULATIONS);
                    String resultProb = "The probability is ".concat( Double.toString((Math.round(answer*1000))/Double.parseDouble("10"))).concat("%");
                    result.setText(resultProb);
                }
            }
        });

        // Adds all visible set roll elements to list


    }

    /*
    Sets up spinners with program array for face on each die.
    Defaults to 6 if numFaces is null
     */
    private void setUpSpinners(View v, @Nullable Integer numFaces) {
        //Set up spinner lists
        ArrayList<Spinner> setSpinners = new ArrayList<Spinner>();
        ArrayList<Spinner> desSpinners = new ArrayList<Spinner>();
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner1));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner2));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner3));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner4));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner5));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner6));

        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner1));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner2));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner3));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner4));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner5));
        desSpinners.add((Spinner) v.findViewById(R.id.desSpinner6));

        //Set up array
        Integer[] array;
        if (numFaces == null) {
            array = new Integer[6];
        } else {
            array = new Integer[numFaces];
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }


        //Finalize with spinner adapters
        ArrayAdapter<Integer> diceAdapter = new ArrayAdapter<Integer>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, array);

        for (int i = 0; i < MAX_SPINNERS; i++) {
            setSpinners.get(i).setAdapter(diceAdapter);
            desSpinners.get(i).setAdapter(diceAdapter);
        }

    }

    /**
     * Sets up the checkbox at the beginning
     *
     * @param v       The view
     * @param checked whether it should start checked or not
     */
    private void setUpCheckBox(View v, @Nullable Boolean checked) {

        // Set up arrays, checkbox, and textbox
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.setRollCheck);

        if (checked == null) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(checked);
        }


        final TextView setLabel = v.findViewById(R.id.setDiceLabel);
        final LinearLayout spinnerLayout1 = (LinearLayout) v.findViewById(R.id.setSpinnerLayout1);
        final LinearLayout spinnerLayout2 = (LinearLayout) v.findViewById(R.id.setSpinnerLayout2);


        // Sets up the visibility
        if (checked == null || checked == false) {
            spinnerLayout1.setVisibility(View.GONE);
            spinnerLayout2.setVisibility(View.GONE);
            setLabel.setVisibility(View.INVISIBLE);
        } else {
            spinnerLayout1.setVisibility(View.VISIBLE);
            spinnerLayout2.setVisibility(View.VISIBLE);
            setLabel.setVisibility(View.VISIBLE);
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false) {
                    spinnerLayout1.setVisibility(View.GONE);
                    spinnerLayout2.setVisibility(View.GONE);
                    setLabel.setVisibility(View.INVISIBLE);
                } else {
                    spinnerLayout1.setVisibility(View.VISIBLE);
                    spinnerLayout2.setVisibility(View.VISIBLE);
                    setLabel.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
