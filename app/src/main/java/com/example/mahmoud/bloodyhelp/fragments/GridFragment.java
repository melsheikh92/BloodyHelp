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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.activities.MainActivity;
import com.example.mahmoud.bloodyhelp.models.Donor;
import com.example.mahmoud.bloodyhelp.util.CustomAdapter;
import com.example.mahmoud.bloodyhelp.util.UpdateFrag;
import com.example.mahmoud.bloodyhelp.util.Utilities;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud on 28/06/2017.
 */

public class GridFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Donor>>, UpdateFrag {
    Context mcontext;
    @BindView(R.id.progressbar)

    ProgressBar progressBar;
    @BindView(R.id.recyclerview)

    RecyclerView recyclerView;
    LoaderManager loaderManager;
    Loader<ArrayList<Donor>> mloader;
    private final static int loader_donors_key = 1215;
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
        MainActivity.mfiltertype = 0;
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
                        Toast.makeText(mcontext, "No Internet connection.", Toast.LENGTH_LONG).show();

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


                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(mcontext.getString(R.string.url_root) + "GetAllUsers")
                            .build();


                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String s = response.body().string();
                        JSONObject jsonobj = new JSONObject(s);

                        JSONArray jarr = jsonobj.getJSONArray("GetAllUsersResult");
                        donors_array = new ArrayList<Donor>();
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject ob = (JSONObject) jarr.get(i);
                            Donor d = new Donor();
                            d.setId(ob.getInt("id"));
                            d.setName(ob.getString("Name"));
                            d.setCityId(ob.getInt("CityId"));
                            d.setDescription(ob.getString("Description"));
                            d.setEmail(ob.getString("Email"));
                            d.setPhone(ob.getString("Phone"));
                            d.setTypeId(ob.getInt("TypeId"));

                            donors_array.add(d);
                        }

                        return donors_array;

                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ;
                    Log.d("myerror", "widget error");
                    return null;

                }

            }

        };
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Donor>> loader, ArrayList<Donor> data) {
        if (data != null) {
            donors_array = data;

            ArrayList<Donor> donors = new ArrayList<Donor>();
            if (MainActivity.mfiltertype != 0) {
                for (Donor d : donors_array) {
                    if (MainActivity.mfiltertype == d.getId()) {
                        donors.add(d);
                    }

                }
            } else {
                donors = donors_array;

            }


            recyclerView.setAdapter(new CustomAdapter(mcontext, data, recyclerView));
            progressBar.setVisibility(View.INVISIBLE);
            if (MainActivity.twoPaneFlag) {

                MainActivity.loadDetailedFragment(data.get(0));
            }


        } else {
            progressBar.setVisibility(View.INVISIBLE);

            Toast.makeText(mcontext, "Error while fetching data.", Toast.LENGTH_LONG).show();


        }

        swiperefresh.setRefreshing(false);
    }

    public static void recycler_filter(int filter, RecyclerView rc) {


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Donor>> loader) {

    }

    @Override
    public void updatefrag(int filter) {
        ArrayList<Donor> donors = new ArrayList<Donor>();
        if (filter != 0) {
            for (Donor d : donors_array) {
                if (filter == d.getId()) {
                    donors.add(d);
                }

            }
        } else {
            donors = donors_array;

        }

        recyclerView.setAdapter(new CustomAdapter(getActivity().getApplicationContext(), donors, recyclerView));
        progressBar.setVisibility(View.INVISIBLE);
        if (MainActivity.twoPaneFlag) {

            MainActivity.loadDetailedFragment(donors_array.get(0));
        }
    }
}

