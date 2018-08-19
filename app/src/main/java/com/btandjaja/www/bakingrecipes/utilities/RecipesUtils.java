package com.btandjaja.www.bakingrecipes.utilities;

import android.text.TextUtils;

import java.io.IOException;
import java.net.URL;

public class RecipesUtils {

    public static String getRecipesList( String recipeUrlString) {
        try {
            if (recipeUrlString == null || TextUtils.isEmpty(recipeUrlString)) return null;
            URL recipeUrl = new URL(recipeUrlString);
            String recipesJson = NetworkUtils.getResponseFromHttpUrl(recipeUrl);

            return recipesJson;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
