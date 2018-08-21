package com.btandjaja.www.bakingrecipes.utilities;

import android.content.Context;
import android.text.TextUtils;

import com.btandjaja.www.bakingrecipes.R;
import com.btandjaja.www.bakingrecipes.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecipesUtils {
    public static String getRecipesListJsonString(String recipeUrlString) {
        try {
            if (recipeUrlString == null || TextUtils.isEmpty(recipeUrlString)) return null;
            URL recipeUrl = new URL(recipeUrlString);
            return NetworkUtils.getResponseFromHttpUrl(recipeUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getRecipesList(Context context, String jsonString, ArrayList<Recipe> recipesList) {
        try {
            JSONArray recipesJsonArr = new JSONArray(jsonString);
            for (int i = 0; i < recipesJsonArr.length(); i++) {
                JSONObject recipeJSON = recipesJsonArr.getJSONObject(i);
                Recipe recipe = new Recipe();

                /* get recipe name */
                recipe.setRecipeName(recipeJSON.getString(context.getString(R.string.name)));

                /* get ingredients */
                JSONArray ingredientJsonArr = recipeJSON.getJSONArray(context.getString(R.string.ingredients));
                for(int j = 0; j < ingredientJsonArr.length(); j++) {
                    JSONObject ingredientJson = ingredientJsonArr.getJSONObject(j);
                    String quantity = ingredientJson.getString(context.getString(R.string.quantity));
                    String measure = ingredientJson.getString(context.getString(R.string.measure));
                    String ingredient = ingredientJson.getString(context.getString(R.string.ingredient));
                    String ingredientString = quantity + " " + measure + " " + ingredient;
                    recipe.setIngredient(ingredientString);
                }
                /* get steps */
                JSONArray stepsJsonArr = recipeJSON.getJSONArray(context.getString(R.string.steps));
                for(int j = 0; j < stepsJsonArr.length(); j++) {
                    JSONObject stepElement = stepsJsonArr.getJSONObject(j);
                    String shortDescription = stepElement.getString(context.getString(R.string.short_description));
                    String description = stepElement.getString(context.getString(R.string.description));
                    String videoUrl = stepElement.getString(context.getString(R.string.video_url));
                    String thumbnailUrl = stepElement.getString(context.getString(R.string.thumbnail_url));
                    recipe.setShortDescription(shortDescription);
                    recipe.setDescription(description);
                    recipe.setVideoUrl(videoUrl);
                    recipe.setThumbnailUrl(thumbnailUrl);
                }

                /* get serving */
                recipe.setServings( recipeJSON.getInt(context.getString(R.string.servings)) );

                /* get image */
                recipe.setImagePath( recipeJSON.getString(context.getString(R.string.image)) );

                /* add recipe to list */
                recipesList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}