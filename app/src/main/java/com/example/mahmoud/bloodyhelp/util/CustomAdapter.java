package com.example.mahmoud.bloodyhelp.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.activities.DetailActivity;
import com.example.mahmoud.bloodyhelp.activities.MainActivity;
import com.example.mahmoud.bloodyhelp.models.Donor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Mahmoud on 29/06/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    public static final String KEY_DONOR_DETAILS = "com.example.mahmoud.bloodyhelp.details_key";
    Context mcontext;

    ArrayList<Donor> data;

    public CustomAdapter(Context context, ArrayList<Donor> donors, RecyclerView recyclerView) {

        mcontext = context;
        data = donors;
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mcontext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Intent intent = new Intent(mcontext, DetailActivity.class);

                    intent.putExtra(KEY_DONOR_DETAILS, data.get(position));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(intent);
                    if (MainActivity.twoPaneFlag) {
                        MainActivity.loadDetailedFragment(data.get(position));
                    }
                } catch (Exception exp) {

                    exp.printStackTrace();
                }
            }
        }));
    }

    View itemView;

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(mcontext).inflate(R.layout.listitem, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getName());
        switch (data.get(position).getTypeId()) {

            case 1:
                holder.iv_blood.setImageResource(R.mipmap.op);
                holder.iv_blood.setContentDescription(mcontext.getString(R.string.blood_op));

                break;
            case 2:
                holder.iv_blood.setImageResource(R.mipmap.am);
                holder.iv_blood.setContentDescription(mcontext.getString(R.string.blood_am));

                break;
            case 3:
                holder.iv_blood.setImageResource(R.mipmap.ap);
                holder.iv_blood.setContentDescription(mcontext.getString(R.string.blood_ap));

                break;
            case 4:
                holder.iv_blood.setImageResource(R.mipmap.bm);
                holder.iv_blood.setContentDescription(mcontext.getString(R.string.blood_bm));

                break;
            case 5:
                holder.iv_blood.setImageResource(R.mipmap.bp);
                holder.iv_blood.setContentDescription(mcontext.getString(R.string.blood_bp));

                break;
            case 6:
                holder.iv_blood.setImageResource(R.mipmap.abm);
                holder.iv_blood.setContentDescription(mcontext.getString(R.string.blood_abm));

                break;
            case 7:
                holder.iv_blood.setImageResource(R.mipmap.abp);
                holder.iv_blood.setContentDescription(mcontext.getString(R.string.blood_abp));

                break;

        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        ;
        @BindView(R.id.iv_blood)
        ImageView iv_blood;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
