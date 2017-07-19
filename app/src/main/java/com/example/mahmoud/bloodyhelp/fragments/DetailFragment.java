package com.example.mahmoud.bloodyhelp.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.data.FavoriteContract;
import com.example.mahmoud.bloodyhelp.data.FavoriteProvider;
import com.example.mahmoud.bloodyhelp.models.Donor;
import com.github.clans.fab.FloatingActionButton;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Mahmoud on 30/06/2017.
 */
@RuntimePermissions
public class DetailFragment extends android.support.v4.app.Fragment {
    public static final String KEY_DONOR_DETAILS_BUNDLE = "com.example.mahmoud.bloodyhelp.details_bundle_key";

    Donor donor;
    @BindView(R.id.iv_blood)
    de.hdodenhof.circleimageview.CircleImageView iv_blood;
    @BindView(R.id.tv_detail_name)
    TextView tv_detail_name;
    @BindView(R.id.tv_detail_email)
    TextView tv_detail_email;
    @BindView(R.id.tv_detail_phone)
    TextView tv_detail_phone;
    @BindView(R.id.tv_star)
    TextView tv_star;
    @BindView(R.id.chbox_fav)
    CheckBox chbox_fav;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.imgbtn_call)
    ImageButton imgbtn_call;
    @BindView(R.id.imgbtn_email)
    ImageButton imgbtn_email;
    private int donorId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            donor = savedInstanceState.getParcelable(KEY_DONOR_DETAILS_BUNDLE);
        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            donor = bundle.getParcelable(KEY_DONOR_DETAILS_BUNDLE);
        }
        donorId = donor.getId();
        tv_detail_name.setText(donor.getName());
        tv_detail_email.setText(donor.getEmail());
        tv_detail_phone.setText(donor.getPhone());

        switch (donor.getTypeId()) {

            case 1:
                iv_blood.setImageResource(R.mipmap.op);
                iv_blood.setContentDescription(getContext().getString(R.string.blood_op));
                break;
            case 2:
                iv_blood.setImageResource(R.mipmap.am);
                iv_blood.setContentDescription(getContext().getString(R.string.blood_am));

                break;
            case 3:
                iv_blood.setImageResource(R.mipmap.ap);
                iv_blood.setContentDescription(getContext().getString(R.string.blood_ap));

                break;
            case 4:
                iv_blood.setImageResource(R.mipmap.bm);
                iv_blood.setContentDescription(getContext().getString(R.string.blood_bm));

                break;
            case 5:
                iv_blood.setImageResource(R.mipmap.bp);
                iv_blood.setContentDescription(getContext().getString(R.string.blood_bp));

                break;
            case 6:
                iv_blood.setImageResource(R.mipmap.abm);
                iv_blood.setContentDescription(getContext().getString(R.string.blood_abm));

                break;
            case 7:
                iv_blood.setImageResource(R.mipmap.abp);
                iv_blood.setContentDescription(getContext().getString(R.string.blood_abp));

                break;

        }
        chbox_fav.setChecked(isExist(donorId));

        if (isExist(donorId)) {
            tv_star.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        } else {
            tv_star.setTextColor(ContextCompat.getColor(getContext(), R.color.colorSilver));
        }
        return view;
    }

    @OnCheckedChanged(R.id.chbox_fav)
    void chbox_fav_changed() {

        if (chbox_fav.isChecked()) {
            tv_star.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(FavoriteContract.FavEntry.KEY_ID, donor.getId());
                contentValues.put(FavoriteContract.FavEntry.KEY_NAME, donor.getName());
                contentValues.put(FavoriteContract.FavEntry.KEY_CITY, donor.getCityId());
                contentValues.put(FavoriteContract.FavEntry.KEY_DESC, donor.getDescription());
                contentValues.put(FavoriteContract.FavEntry.KEY_BLOOD_TYPE, donor.getTypeId());
                contentValues.put(FavoriteContract.FavEntry.KEY_PHONE, donor.getPhone());
                contentValues.put(FavoriteContract.FavEntry.KEY_EMAIL, donor.getEmail());
                Uri uri = getContext().getContentResolver().insert(FavoriteProvider.CONTENT_URI, contentValues);
                if (uri != null) {
                    Toast.makeText(getContext().getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {


                tv_star.setTextColor(ContextCompat.getColor(getContext(), R.color.colorSilver));
                Uri uri = FavoriteProvider.CONTENT_URI;
                uri = uri.buildUpon().appendPath(donorId + "").build();
                getContext().getContentResolver().delete(uri, null, null);

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

    }

    @OnClick(R.id.fab)
    void fab_click() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
           getContext().getString(R.string.msg1)     +" " + donor.getName() +   getContext().getString(R.string.msg2)+ donor.getEmail() + getContext().getString(R.string.msg3) +"  " + donor.getPhone());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);


    }

    @OnClick(R.id.imgbtn_call)
    void makeCall() {

        DetailFragmentPermissionsDispatcher.setImgbtn_call_clickWithCheck(this);

    }


    @NeedsPermission(android.Manifest.permission.CALL_PHONE)
    void setImgbtn_call_click() {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + donor.getPhone()));
        startActivity(intent);
    }

    @OnShowRationale(android.Manifest.permission.CALL_PHONE)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setMessage(getContext().getString(R.string.do_you_want_blood))
                .setPositiveButton(getContext().getString(R.string.allow), (dialog, button) -> request.proceed())
                .setNegativeButton(getContext().getString(R.string.deny), (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(android.Manifest.permission.CALL_PHONE)
    void showDeniedForCall() {
        Toast.makeText(getContext(), getContext().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(android.Manifest.permission.CALL_PHONE)
    void showNeverAskForCall() {
        Toast.makeText(getContext(), getContext().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imgbtn_email)
    void setImgbtn_email_click() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        String aEmailList[] = {donor.getEmail()};
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getContext().getString(R.string.bloody_help));
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

        startActivity(emailIntent);


    }

    private boolean isExist(int Id) {
        String selection = FavoriteContract.FavEntry.KEY_ID + " = '"
                + donorId + "'";

        return getContext().getContentResolver().query(FavoriteProvider.CONTENT_URI,
                null,
                selection,
                null,
                null).getCount() > 0 ? true : false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(KEY_DONOR_DETAILS_BUNDLE, donor);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DetailFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);


    }
}
