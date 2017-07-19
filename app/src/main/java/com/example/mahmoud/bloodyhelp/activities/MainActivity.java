package com.example.mahmoud.bloodyhelp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.fragments.DetailFragment;
import com.example.mahmoud.bloodyhelp.fragments.GridFragment;
import com.example.mahmoud.bloodyhelp.fragments.SavedFragment;
import com.example.mahmoud.bloodyhelp.models.Donor;
import com.example.mahmoud.bloodyhelp.util.UpdateFrag;
import com.example.mahmoud.bloodyhelp.util.Utilities;
import com.example.mahmoud.bloodyhelp.util.ViewPagerAdapter;
import com.google.firebase.crash.FirebaseCrash;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static boolean twoPaneFlag;
    ProgressDialog progressDialog;
    static FragmentManager fragmentManager;
    static FragmentTransaction ft;
    int my_indicator = 0;
    private final static String MainActivity_Key = "MainActivity_Key";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Context mcontext;
    public static int mfiltertype;
    public static int saved_selected_filter = 0;
    Fragment savedFragment;
    Fragment gridFragment;
    private String MainActivity_filter_Key = "filter_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            mcontext = this;
            twoPaneFlag = false;
            if (findViewById(R.id.framelayout_secondpane) != null) {
                twoPaneFlag = true;

            }
            tv_title.setTypeface(Utilities.getTypeface_toms(mcontext));
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            if (savedInstanceState != null) {
                my_indicator = savedInstanceState.getInt(MainActivity_Key);
                mfiltertype = savedInstanceState.getInt(MainActivity_filter_Key);
            }
            fragmentManager = getSupportFragmentManager();
            ft = fragmentManager.beginTransaction();

            tabs.setupWithViewPager(viewpager);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            gridFragment = new GridFragment();
            adapter.addFrag(gridFragment, "Donors");
            savedFragment = new SavedFragment();

            adapter.addFrag(savedFragment, "Favorites");
            viewpager.setAdapter(adapter);
        } catch (Exception ex) {
            FirebaseCrash.log(ex.toString());

        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(MainActivity_Key, my_indicator);
        outState.putInt(MainActivity_filter_Key, mfiltertype);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_filter:
                filter();
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(mcontext, SettingsActivity.class));
                return true;
            case R.id.menu_about_me:
                startActivity(new Intent(mcontext, AboutMeActivity.class));

                return true;
            case R.id.menu_add:
                startActivity(new Intent(mcontext, DonorFormActivity.class));

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    UpdateFrag mUpdateFrag;

    private void filter() {
        String[] some_array = getResources().getStringArray(R.array.bloodtypes);

        final String sorttype[] = getResources().getStringArray(R.array.bloodtypes);
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(MainActivity.this);
        alertdialogbuilder.setTitle(mcontext.getString(R.string.blood_type));
        alertdialogbuilder.setSingleChoiceItems(sorttype, mfiltertype, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mfiltertype = which;
                dialog.dismiss();
                mUpdateFrag = (UpdateFrag) gridFragment;

                mUpdateFrag.updatefrag(mfiltertype);
            }
        });


        AlertDialog dialog = alertdialogbuilder.create();
        dialog.show();

    }


    public static void loadDetailedFragment(Donor donor) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailFragment.KEY_DONOR_DETAILS_BUNDLE, donor);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.framelayout_secondpane, fragment);
        ft.commit();
    }


}
