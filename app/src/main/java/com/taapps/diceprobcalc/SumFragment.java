package com.taapps.diceprobcalc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SumFragment extends Fragment implements FragmentMethods {


    public SumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sum, container, false);
    }

    @Override
    public boolean isSameFragment(Fragment f) {
        return (f instanceof SumFragment);
    }
}
