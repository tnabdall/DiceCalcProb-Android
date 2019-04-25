package com.taapps.diceprobcalc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment implements FragmentMethods {


    public OptionsFragment() {
        // Required empty public constructor
    }

    public void setNumDiceCho(int numDice) {
        if (numDice > 0 && numDice <= ChoiceFragment.MAX_SPINNERS) {
            MainActivity.NUM_DICE_CHO = numDice;
        }
    }

    public void setMINMAXFACECho(int minface, int maxface) {
        if (minface > -1 && maxface > minface) {
            MainActivity.MIN_FACE_CHO = minface;
            MainActivity.MAX_FACE_CHO = maxface;
        }
    }

    public void setNUM_SIMULATIONS(int NUM_SIMULATIONS) {
        if (NUM_SIMULATIONS > 10 && NUM_SIMULATIONS < 1000000000) {
            MainActivity.NUM_SIMULATIONS = NUM_SIMULATIONS;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public boolean isSameFragment(Fragment f) {
        return (f instanceof OptionsFragment);
    }
}
