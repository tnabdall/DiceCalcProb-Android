package com.taapps.diceprobcalc;


import android.os.AsyncTask;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoiceFragment extends Fragment implements FragmentMethods {

    public static int MAX_SPINNERS = 6; //Max spinners in layout (ie 6 dice per roll).
    private AdView mAdView;


    public ChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View choiceView = inflater.inflate(R.layout.fragment_choice, container, false);

        mAdView = choiceView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Set up all elements
        setUpSpinners(choiceView);
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
                if (checkBox.isChecked()) {
                    setRollChoices = new ArrayList<Integer>();
                    for (int i = 0; i < setSpinners.size(); i++) {
                        if (setSpinners.get(i).getVisibility() == View.VISIBLE) {
                            setRollChoices.add(Integer.parseInt(setSpinners.get(i).getSelectedItem().toString()));
                        }
                    }
                }

                // Adds all desired roll elements to list
                for (int i = 0; i < desSpinners.size(); i++) {
                    if (desSpinners.get(i).getVisibility() == View.VISIBLE) {
                        if(desSpinners.get(i).getSelectedItem().toString().equalsIgnoreCase("*")){
                            desRollChoices.add(Die.WILDCARD);
                        }
                        else {
                            desRollChoices.add(Integer.parseInt(desSpinners.get(i).getSelectedItem().toString()));
                        }
                    }
                }

                if (rolls > 0) {
                    ChoiceProbabilityThread thread = new ChoiceProbabilityThread();
                    ProbabilityCalculator prob = new ProbabilityCalculator(MainActivity.NUM_DICE_CHO, MainActivity.MIN_FACE_CHO, MainActivity.MAX_FACE_CHO, rolls, desRollChoices, setRollChoices);
                    thread.execute(prob);
                }
            }
        });

        // Adds all visible set roll elements to list


    }

    /*
    Sets up spinners with program array for face on each die.
    Defaults to 6 if numFaces is null
     */
    private void setUpSpinners(View v) {
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
        String[] setArray = new String[MainActivity.MAX_FACE_CHO - MainActivity.MIN_FACE_CHO + 1];
        String[] desArray = new String[MainActivity.MAX_FACE_CHO - MainActivity.MIN_FACE_CHO + 2];
        for (int i = 0; i < setArray.length; i++) {
            setArray[i] = Integer.toString(i + MainActivity.MIN_FACE_CHO);
            desArray[i] = Integer.toString(i + MainActivity.MIN_FACE_CHO);
        }
        desArray[desArray.length-1] = "*";


        //Finalize spinner adapters
        ArrayAdapter<String> setDiceAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, setArray);
        ArrayAdapter<String> desDiceAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, desArray);

        for (int i = 0; i < MAX_SPINNERS; i++) {
            setSpinners.get(i).setAdapter(setDiceAdapter);
            desSpinners.get(i).setAdapter(desDiceAdapter);
        }

        //Set visibilities based on number of dice
        for (int i = 0; i < desSpinners.size(); i++) {
            if (i < MainActivity.NUM_DICE_CHO) {
                desSpinners.get(i).setVisibility(View.VISIBLE);
            } else {
                desSpinners.get(i).setVisibility(View.GONE);
            }

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
        final LinearLayout spinLayout1 = v.findViewById(R.id.setSpinnerLayout1);
        final LinearLayout spinLayout2 = v.findViewById(R.id.setSpinnerLayout2);
        final ArrayList<Spinner> setSpinners = new ArrayList<Spinner>();
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner1));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner2));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner3));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner4));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner5));
        setSpinners.add((Spinner) v.findViewById(R.id.setSpinner6));


        // Sets up the visibility
        if (checked == null || checked == false) {
            spinLayout1.setVisibility(View.GONE);
            spinLayout2.setVisibility(View.GONE);
            for (int i = 0; i < setSpinners.size(); i++) {
                setSpinners.get(i).setVisibility(View.GONE);
            }
            setLabel.setVisibility(View.INVISIBLE);
        } else {
            spinLayout1.setVisibility(View.VISIBLE);
            spinLayout2.setVisibility(View.VISIBLE);
            for (int i = 0; i < setSpinners.size(); i++) {
                if (i < MainActivity.NUM_DICE_CHO) {
                    setSpinners.get(i).setVisibility(View.VISIBLE);
                } else {
                    setSpinners.get(i).setVisibility(View.GONE);
                }
            }
            setLabel.setVisibility(View.VISIBLE);
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false) {
                    spinLayout1.setVisibility(View.GONE);
                    spinLayout2.setVisibility(View.GONE);
                    for (int i = 0; i < setSpinners.size(); i++) {
                        setSpinners.get(i).setVisibility(View.GONE);
                    }
                    setLabel.setVisibility(View.INVISIBLE);
                } else {
                    spinLayout1.setVisibility(View.VISIBLE);
                    spinLayout2.setVisibility(View.VISIBLE);
                    for (int i = 0; i < setSpinners.size(); i++) {
                        if (i < MainActivity.NUM_DICE_CHO) {
                            setSpinners.get(i).setVisibility(View.VISIBLE);
                        } else {
                            setSpinners.get(i).setVisibility(View.GONE);
                        }
                    }
                    setLabel.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private class ChoiceProbabilityThread extends AsyncTask<ProbabilityCalculator, Void, Double> {

        private ProbabilityCalculator prob;
        public double result;
        TextView resultView;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prob = null;
            result = 0.0;
            try {
                resultView = getView().findViewById(R.id.result);
                resultView.setText("Calculating...");
            } catch (Exception e) {
                //Do nothing
            }
        }

        @Override
        protected Double doInBackground(ProbabilityCalculator... probabilityCalculators) {
            try {
                prob = probabilityCalculators[0];
                result = prob.calculate(MainActivity.NUM_SIMULATIONS);
                return result;
            } catch (Exception e) {
                //Do nothing
            }
            return 0.0;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            try {
                resultView = getView().findViewById(R.id.result);
                String resultProb = "The probability is ".concat(Double.toString((Math.round(aDouble * 1000)) / Double.parseDouble("10"))).concat("%");
                resultView.setText(resultProb);
            } catch (Exception e) {
                //Do nothing
            }
        }

    }
}
