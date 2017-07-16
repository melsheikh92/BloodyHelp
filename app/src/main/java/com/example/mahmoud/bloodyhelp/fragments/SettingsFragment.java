package com.example.mahmoud.bloodyhelp.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahmoud.bloodyhelp.R;

import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 29/06/2017.
 */

public class SettingsFragment extends PreferenceFragment {


    

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
