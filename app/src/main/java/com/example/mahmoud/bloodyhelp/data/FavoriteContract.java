package com.example.mahmoud.bloodyhelp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mahmoud on 29/06/2017.
 */

public class FavoriteContract {
    private static final String AUTHORITY = "com.example.mahmoud.bloodyhelp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITES = "FAVORITES";


    public static final class FavEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorites";


        public static final String KEY_ID = "_id";
        public static final String KEY_NAME = "_name";
        public static final String KEY_DESC = "_desc";
        public static final String KEY_CITY = "_city";
        public static final String KEY_BLOOD_TYPE = "_blood_type";
        public static final String KEY_PHONE= "_phone";
        public static final String KEY_EMAIL= "_email";
    }
}
