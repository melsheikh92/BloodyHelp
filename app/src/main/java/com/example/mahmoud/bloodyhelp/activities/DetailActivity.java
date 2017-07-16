package com.example.mahmoud.bloodyhelp.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.fragments.DetailFragment;
import com.example.mahmoud.bloodyhelp.util.CustomAdapter;
import com.example.mahmoud.bloodyhelp.util.Utilities;
import com.google.firebase.crash.FirebaseCrash;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.framelayout_details)
    FrameLayout framelayout_details;
    Context mcontext;
    static FragmentManager fragmentManager;
    static FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mcontext = this;
        FirebaseCrash.log("Activity created");

        tv_title.setTypeface(Utilities.getTypeface_toms(mcontext));
        setSupportActionBar(toolbar);
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
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailFragment.KEY_DONOR_DETAILS_BUNDLE, getIntent().getParcelableExtra(CustomAdapter.KEY_DONOR_DETAILS));
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.framelayout_details, fragment);
        ft.commit();
    }
}
