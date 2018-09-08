package com.btandjaja.www.bakingrecipes.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class BakingContract {
    public static final String AUTHORITY = "com.btandjaja.www.baking_recipe";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_LIST = "list";

    public static final class BakingEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LIST).build();

        public static final String TABLE_NAME = "recipe_list";
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
    }
}
