package com.taapps.diceprobcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final int OPTIONS_REQUEST = 101;
    protected OptionSet opt = new OptionSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        launchChoice();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Fragment fragment = null;
        FragmentMethods methods = null;
        Intent intent = null;

        switch (id) {
            case R.id.nav_choice:
                fragment = new ChoiceFragment();
                break;

            case R.id.nav_options:
                intent = new Intent(this,OptionsActivity.class);
            default:
                break;

        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.getFragments().get(0); // Gets the current fragment

        if (fragment != null) {
            // Check to see if the fragment is the same type as open. If it is, keep the current fragment.
            methods = (FragmentMethods) fragment;
            if(!methods.isSameFragment(currentFragment)) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame, fragment);
                ft.commit();
            }
        } else if(intent!=null) {
            if (id == R.id.nav_options) {
                startActivityForResult(intent, OPTIONS_REQUEST);
            }
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void launchChoice() {
        Fragment fragment = new ChoiceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    public void launchOptions() {
        //setContentView(R.layout.options);


        Intent intent = new Intent(this, OptionsActivity.class);
        startActivityForResult(intent, OPTIONS_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == OPTIONS_REQUEST) {
            try {
                opt = (OptionSet) data.getSerializableExtra("optionSet");
                if (opt.isSumMode()) {
                    setContentView(R.layout.activity_sum);
                } else {
                    setContentView(R.layout.activity_main);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error: Couldn't get options.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
