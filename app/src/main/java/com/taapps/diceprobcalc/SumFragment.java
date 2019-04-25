package com.taapps.diceprobcalc;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SumFragment extends Fragment implements FragmentMethods {

    private boolean atLeastMode = false;
    public static int STARTDICENUM = 6;

    private AdView mAdView;

    public SumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View sumView = inflater.inflate(R.layout.fragment_sum, container, false);
        mAdView = sumView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        setupSpinner(sumView);
        setupCheckbox(sumView);
        setupButton(sumView);

        return sumView;
    }

    private void setupSpinner(View v) {
        final Spinner numDiceSpin = (Spinner) v.findViewById(R.id.numDiceSpinnerSum);
        numDiceSpin.setSelection(STARTDICENUM-1);
        final ArrayList<LinearLayout> dieLayoutList = new ArrayList<LinearLayout>();
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die1Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die2Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die3Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die4Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die5Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die6Layout));
        final LinearLayout row2Layout = (LinearLayout) v.findViewById(R.id.dieRow2Layout);

        numDiceSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int numDice = Integer.parseInt(numDiceSpin.getSelectedItem().toString());
                for (int i = 0; i < dieLayoutList.size(); i++) {
                    if (i < numDice) {
                        dieLayoutList.get(i).setVisibility(View.VISIBLE);
                    } else {
                        dieLayoutList.get(i).setVisibility(View.GONE);
                    }
                }
                if (numDice < 4) {
                    row2Layout.setVisibility(View.GONE);
                } else {
                    row2Layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupCheckbox(View v) {
        CheckBox atLeast = (CheckBox) v.findViewById(R.id.sumAtLeastCheck);
        final TextView sumDesLabel = (TextView) v.findViewById(R.id.sumDesLabel);
        atLeast.setChecked(false);

        atLeast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sumDesLabel.setText("Desired sum: At least ");
                    atLeastMode = true;
                } else {
                    sumDesLabel.setText("Desired sum: Only ");
                    atLeastMode = false;
                }
            }
        });
    }

    private void setupButton(View v) {
        Button calculate = (Button) v.findViewById(R.id.calculateButtonSum);
        final TextView resultView = (TextView) v.findViewById(R.id.resultSum);
        final EditText sumDesired = (EditText) v.findViewById(R.id.sum);
        final ArrayList<LinearLayout> dieLayoutList = new ArrayList<LinearLayout>();
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die1Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die2Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die3Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die4Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die5Layout));
        dieLayoutList.add((LinearLayout) v.findViewById(R.id.die6Layout));

        final ArrayList<EditText> minList = new ArrayList<EditText>();
        minList.add((EditText) v.findViewById(R.id.die1Min));
        minList.add((EditText) v.findViewById(R.id.die2Min));
        minList.add((EditText) v.findViewById(R.id.die3Min));
        minList.add((EditText) v.findViewById(R.id.die4Min));
        minList.add((EditText) v.findViewById(R.id.die5Min));
        minList.add((EditText) v.findViewById(R.id.die6Min));

        final ArrayList<EditText> maxList = new ArrayList<EditText>();
        maxList.add((EditText) v.findViewById(R.id.die1Max));
        maxList.add((EditText) v.findViewById(R.id.die2Max));
        maxList.add((EditText) v.findViewById(R.id.die3Max));
        maxList.add((EditText) v.findViewById(R.id.die4Max));
        maxList.add((EditText) v.findViewById(R.id.die5Max));
        maxList.add((EditText) v.findViewById(R.id.die6Max));

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setup diceList to include only visible elements
                ArrayList<int[]> diceList = new ArrayList<int[]>();

                boolean rightConditions = true;

                for(int i = 0; i<dieLayoutList.size(); i++){
                    if(dieLayoutList.get(i).getVisibility() == View.VISIBLE){
                        int[] x = new int[2];
                        x[0] = Integer.parseInt(minList.get(i).getText().toString());
                        x[1] = Integer.parseInt(maxList.get(i).getText().toString());
                        if(x[1]<=x[0]){
                            rightConditions = false;
                            break;
                        }
                        else{
                            diceList.add(x);
                        }
                    }
                }
                if(!rightConditions){
                    resultView.setText("Error in conditions. Check that minimum face values are less than maximum.");
                }
                else {
                    //Calculate probability
                    SumProbabilityThread thread = new SumProbabilityThread();
                    ProbabilityCalculatorSum prob = new ProbabilityCalculatorSum(diceList, Integer.parseInt(sumDesired.getText().toString()), atLeastMode);
                    thread.execute(prob);
                }
            }
        });

    }

    @Override
    public boolean isSameFragment(Fragment f) {
        return (f instanceof SumFragment);
    }

    private class SumProbabilityThread extends AsyncTask<ProbabilityCalculatorSum, Void, Double> {

        private ProbabilityCalculatorSum prob;
        public double result;
        TextView resultView;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prob = null;
            result = 0.0;
            try {
                resultView = getView().findViewById(R.id.resultSum);
                resultView.setText("Calculating...");
            } catch (Exception e) {
                //Do nothing
            }
        }

        @Override
        protected Double doInBackground(ProbabilityCalculatorSum... probabilityCalculatorSums) {
            try {
                prob = probabilityCalculatorSums[0];
                result = prob.Calculate(MainActivity.NUM_SIMULATIONS);
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
                resultView = getView().findViewById(R.id.resultSum);
                String resultProb = "The probability is ".concat(Double.toString((Math.round(aDouble * 1000)) / Double.parseDouble("10"))).concat("%");
                resultView.setText(resultProb);
            } catch (Exception e) {
                //Do nothing
            }
        }

    }
}
