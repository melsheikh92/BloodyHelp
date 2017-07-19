package com.example.mahmoud.bloodyhelp.fragments;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.activities.MainActivity;
import com.example.mahmoud.bloodyhelp.data.FavoriteProvider;
import com.example.mahmoud.bloodyhelp.models.Donor;
import com.example.mahmoud.bloodyhelp.util.CustomAdapter;
import com.example.mahmoud.bloodyhelp.util.Utilities;
import com.google.firebase.crash.FirebaseCrash;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Mahmoud on 12/07/2017.
 */

public class SavedFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Donor>> {
    Context mcontext;
    @BindView(R.id.progressbar)

    ProgressBar progressBar;
    @BindView(R.id.recyclerview)

    RecyclerView recyclerView;
    LoaderManager loaderManager;
    Loader<ArrayList<Donor>> mloader;
    private final static int loader_donors_key = 121578;
    static ArrayList<Donor> donors_array;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        ButterKnife.bind(this, view);
        try {

            mcontext = getActivity().getApplicationContext();
            loaderManager = getActivity().getLoaderManager();
            mloader = loaderManager.getLoader(loader_donors_key);
            Bundle bundle = this.getArguments();
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mcontext, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            if (mloader == null) {
                mloader = loaderManager.initLoader(loader_donors_key, null, this);
            } else {

                mloader = loaderManager.restartLoader(loader_donors_key, null, this);

            }
            swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateRecyclerView();
                }
            });
        } catch (Exception ex) {

            FirebaseCrash.log(ex.toString());

        }
        return view;
    }

    private void updateRecyclerView() {
        loaderManager.initLoader(loader_donors_key, null, this).forceLoad();

    }

    @Override
    public Loader<ArrayList<Donor>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Donor>>(mcontext) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (donors_array != null) {
                    deliverResult(donors_array);
                } else {
                    if (Utilities.isConnected(mcontext)) {
                        progressBar.setVisibility(View.VISIBLE);

                        forceLoad();
                    } else {
                        Toast.makeText(mcontext, getContext().getString(R.string.no_internet), Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void deliverResult(ArrayList<Donor> data) {
                donors_array = data;
                super.deliverResult(data);
            }

            @Override
            public ArrayList<Donor> loadInBackground() {


                return parseFavCursor(getContext().getContentResolver().query(FavoriteProvider.CONTENT_URI,
                        null,
                        null,
                        null,
                        null));


            }

        };
    }

    private ArrayList<Donor> parseFavCursor(Cursor query) {
        ArrayList<Donor> saved_donors = new ArrayList<>();
        try {
            while (query.moveToNext()) {

                Donor d = new Donor();
                d.setId(Integer.parseInt(query.getString(0)));
                d.setName(query.getString(1));
                d.setDescription(query.getString(2));
                d.setCityId(Integer.parseInt(query.getString(3)));
                d.setTypeId(Integer.parseInt(query.getString(4)));
                d.setPhone(query.getString(5));
                d.setEmail(query.getString(6));
                saved_donors.add(d);
            }
        } catch (Exception exeption) {
            exeption.printStackTrace();
            FirebaseCrash.log(exeption.toString());

        }
        return saved_donors;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Donor>> loader, ArrayList<Donor> data) {
        if (data != null) {
            if (data.size() > 0) {
                donors_array = data;

                recyclerView.setAdapter(new CustomAdapter(mcontext, data, recyclerView));
                progressBar.setVisibility(View.INVISIBLE);
                if (MainActivity.twoPaneFlag) {

                    MainActivity.loadDetailedFragment(data.get(0));
                }
            }

        } else {
            progressBar.setVisibility(View.INVISIBLE);

            Toast.makeText(mcontext, mcontext.getString(R.string.err_data), Toast.LENGTH_LONG).show();


        }

        swiperefresh.setRefreshing(false);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Donor>> loader) {

    }


}
