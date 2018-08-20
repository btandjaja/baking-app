package com.btandjaja.www.bakingrecipes.utilities;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.btandjaja.www.bakingrecipes.R;
import com.btandjaja.www.bakingrecipes.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecipesUtils {

    //TODO remove TAG
    private static final String TAG = RecipesUtils.class.getSimpleName();

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
//                String recipeName = new String (recipeJSON.getString(context.getString(R.string.name)));
                /* set recipe name */
//                recipesList.add(new Recipe(recipeName));
                recipesList.add(new Recipe(recipeJSON.getString(context.getString(R.string.name))));

                printArrayList(recipesList);

//                JSONObject recipeJson = recipesJsonArr.getJSONObject(i);
//                /* get ingredients */
//                JSONArray ingredientJsonArr = recipeJson.getJSONArray(context.getString(R.string.ingredients));
//                for(int j = 0; j < ingredientJsonArr.length(); j++) {
//                    JSONObject ingredientJson = ingredientJsonArr.getJSONObject(j);
//                    String quantity = ingredientJson.getString(context.getString(R.string.quantity));
//                    String measure = ingredientJson.getString(context.getString(R.string.measure));
//                    String ingredient = ingredientJson.getString(context.getString(R.string.ingredient));
//                    String ingredientString = quantity + " " + measure + " " + ingredient;
//                    recipesList.get(last).setIngredient(ingredientString);
//                }
//                /* get steps */
//                JSONArray stepsJsonArr = recipeJson.getJSONArray(context.getString(R.string.steps));
//                for(int j = 0; j < stepsJsonArr.length(); j++) {
//                    JSONObject stepElement = stepsJsonArr.getJSONObject(j);
//                    String shortDescription = stepElement.getString(context.getString(R.string.short_description));
//                    String description = stepElement.getString(context.getString(R.string.description));
//                    String videoUrl = stepElement.getString(context.getString(R.string.video_url));
//                    String thumbnailUrl = stepElement.getString(context.getString(R.string.thumbnail_url));
//                    recipesList.get(last).setShortDescription(shortDescription);
//                    recipesList.get(last).setDescription(description);
//                    recipesList.get(last).setVideoUrl(videoUrl);
//                    recipesList.get(last).setThumbnailUrl(thumbnailUrl);
//                }
//                /* get serving */
//                recipesList.get(last).setServings( recipeJson.getInt(context.getString(R.string.servings)) );
//                /* get image */
//                recipesList.get(last).setImagePath( recipeJson.getString(context.getString(R.string.image)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            printArrayList(recipesList);
        }
    }

    private static void printArrayList(ArrayList<Recipe> recipesList) {
        for (int i = 0; i < recipesList.size(); i++) {
            Log.d(TAG, "recipe name: " + recipesList.get(i).getRecipeName());
        }
        Log.d(TAG, "***************");
    }
}