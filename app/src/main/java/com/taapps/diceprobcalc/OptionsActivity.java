package com.taapps.diceprobcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    OptionSet opt = new OptionSet();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        Toast.makeText(this, "lel", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void finish() {
        Intent opInt = new Intent();
        opt.setNumberDice(Integer.parseInt(((Spinner) findViewById(R.id.diceSpinner) ).getSelectedItem().toString()));
        opt.setNumberSims(Integer.parseInt(((TextView) findViewById(R.id.simsInput) ).getText().toString()));
        opt.setSumMode(((Switch) findViewById(R.id.modeSwitch) ).isChecked());
        opt.setMaxFace(Integer.parseInt(((Spinner) findViewById(R.id.faceSpinner) ).getSelectedItem().toString()));
        opInt.putExtra("optionSet",opt);
        setResult(MainActivity.OPTIONS_REQUEST, opInt);
        super.finish();
    }
}
