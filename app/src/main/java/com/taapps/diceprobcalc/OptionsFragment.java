package com.taapps.diceprobcalc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment implements FragmentMethods {

    public static String simsDefault = "100000";
    public static String numDiceChoDefault = "6";
    public static String minFaceChoDefault = "1";
    public static String maxFaceChoDefault = "6";
    public static String numDiceSumDefault = "6";
    public static String minFaceSumDefault = "1";
    public static String maxFaceSumDefault = "6";


    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View optionsView = inflater.inflate(R.layout.fragment_options, container, false);

        setupSaveButton(optionsView);

        return optionsView;
    }

    private void setupSaveButton(View v) {
        Button save = v.findViewById(R.id.saveOptionsButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sims = getView().findViewById(R.id.numSimulations);
                EditText diceCho = getView().findViewById(R.id.numDiceCho);
                EditText minCho = getView().findViewById(R.id.minFaceCho);
                EditText maxCho = getView().findViewById(R.id.maxFaceCho);

                setNUM_SIMULATIONS(Integer.parseInt(sims.getText().toString()));
                setNumDiceCho(Integer.parseInt(diceCho.getText().toString()));
                setMINMAXFACECho(Integer.parseInt(minCho.getText().toString()),Integer.parseInt(maxCho.getText().toString()));
            }
        });

    }

    @Override
    public boolean isSameFragment(Fragment f) {
        return (f instanceof OptionsFragment);
    }

    public void setNumDiceCho(int numDice) {
        if (numDice > 0 && numDice <= ChoiceFragment.MAX_SPINNERS) {
            MainActivity.NUM_DICE_CHO = numDice;
        } else {
            EditText dice = getView().findViewById(R.id.numDiceCho);
            dice.setText(numDiceChoDefault);
            MainActivity.NUM_DICE_CHO = Integer.parseInt(numDiceChoDefault);
        }
    }

    public void setMINMAXFACECho(int minface, int maxface) {
        if (minface > -1 && maxface > minface) {
            MainActivity.MIN_FACE_CHO = minface;
            MainActivity.MAX_FACE_CHO = maxface;
        } else {
            EditText min = getView().findViewById(R.id.minFaceCho);
            min.setText(minFaceChoDefault);
            EditText max = getView().findViewById(R.id.maxFaceCho);
            max.setText(maxFaceChoDefault);
            MainActivity.MIN_FACE_CHO = Integer.parseInt(minFaceChoDefault);
            MainActivity.MAX_FACE_CHO = Integer.parseInt(maxFaceChoDefault);
        }
    }

    public void setNUM_SIMULATIONS(int NUM_SIMULATIONS) {
        if (NUM_SIMULATIONS > 10 && NUM_SIMULATIONS < 1000000000) {
            MainActivity.NUM_SIMULATIONS = NUM_SIMULATIONS;
        } else {
            EditText sims = getView().findViewById(R.id.numSimulations);
            sims.setText(simsDefault);
            MainActivity.NUM_SIMULATIONS = Integer.parseInt(simsDefault);
        }
    }
}
