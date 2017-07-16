package com.example.mahmoud.bloodyhelp.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.fragments.SettingsFragment;
import com.example.mahmoud.bloodyhelp.util.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        tv_title.setTypeface(Utilities.getTypeface_toms(this));
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                }

        );
        getFragmentManager().beginTransaction().replace(R.id.settings_framelayout,
                new SettingsFragment()).commit();

    }
}
