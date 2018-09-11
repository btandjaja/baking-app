package com.btandjaja.www.bakingrecipes.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeContract {
    /* Add content provider constants to the Contract
     Clients need to know how to access the task data, and it's your job to provide
     these content URI's for the path to that data:
        1) Content authority,
        2) Base content URI,
        3) Path(s) to the tasks directory
        4) Content URI for data in the RecipeEntry class
      */

    public static final String AUTHORITY = "com.btandjaja.www.bakingrecipes";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_RECIPE_LIST = "list";

    public static final class RecipeEntry implements BaseColumns {
        // RecipeEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE_LIST).build();

        // RecipeList table and column names
        public static final String TABLE_NAME = "recipe_list";
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_ARRAYLIST_INDEX = "list_index";

        /*
        The above table structure looks something like the sample table below.
        With the name of the table and columns on top, and potential contents in rows

        Note: Because this implements BaseColumns, the _id column is generated automatically

        tasks
         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    recipe_name     |  list_index   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |    Brownies        |       0       |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    Yellow Cake     |       1       |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   addition recipe  |       42      |
         - - - - - - - - - - - - - - - - - - - - - -
         */
    }
}
