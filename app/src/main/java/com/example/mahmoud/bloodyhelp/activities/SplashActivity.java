package com.example.mahmoud.bloodyhelp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.activities.MainActivity;
import com.example.mahmoud.bloodyhelp.util.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    Context mContext;
    @BindView(R.id.splash_tv)
    TextView splash_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        ButterKnife.bind(this);
        splash_tv.setTypeface(Utilities.getTypeface_toms(mContext));


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SplashActivity.this, splash_tv, mContext.getString(R.string.key_shared_trans));

                startActivity(intent, options.toBundle());
                finish();

            }


        }, 3000);
    }
}
