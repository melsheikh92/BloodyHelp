package com.example.mahmoud.bloodyhelp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mahmoud on 29/06/2017.
 */

public class SqlDataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorites.db";

    private static final int DB_VERSION = 3;

    public SqlDataHelper(Context context) {

        super(context, DATABASE_NAME, null, DB_VERSION);


    }

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + FavoriteContract.FavEntry.TABLE_NAME + " (" +
                    FavoriteContract.FavEntry.KEY_ID + " integer PRIMARY KEY," +
                    FavoriteContract.FavEntry.KEY_NAME + "," +
                    FavoriteContract.FavEntry.KEY_DESC + "," +
                    FavoriteContract.FavEntry.KEY_CITY + "," +
                    FavoriteContract.FavEntry.KEY_BLOOD_TYPE + "," +
                    FavoriteContract.FavEntry.KEY_PHONE + "," +
                    FavoriteContract.FavEntry.KEY_EMAIL + "," +
                    " UNIQUE (" + FavoriteContract.FavEntry.KEY_ID + "));";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavEntry.TABLE_NAME);
        onCreate(db);
    }

}
