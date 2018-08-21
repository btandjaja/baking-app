package com.btandjaja.www.bakingrecipes.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;

public class Recipe implements Parcelable{
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Object[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(mStep); dest.writeInt(mServings);
        dest.writeString(mRecipeName); dest.writeString(mImagePath);
        dest.writeList(mIngredients);
        dest.writeList(mShortDescription);
        dest.writeList(mDescription);
        dest.writeList(mVideoUrl);
        dest.writeList(mThumbnailUrl);
    }

    public static final String RECIPE = "recipe";
    private int mStep, mServings;
    private String mRecipeName, mImagePath;
    private ArrayList<String> mIngredients, mShortDescription, mDescription, mVideoUrl, mThumbnailUrl;

    public Recipe() {
        mRecipeName = null;
        constructorHelper();
    }

    public Recipe(Parcel in) {
        mStep = in.readInt(); mServings = in.readInt();
        mRecipeName = in.readString(); mImagePath = in.readString();
        mIngredients = in.readArrayList(null);
        mShortDescription = in.readArrayList(null);
        mDescription = in.readArrayList(null);
        mVideoUrl = in.readArrayList(null);
        mThumbnailUrl = in.readArrayList(null);
    }

    /* setter */
    public void setServings(int serving) { mServings = serving; }
    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }
    public void setImagePath(String imagePath) { mImagePath = checkValidString(imagePath); }
    public void setIngredient(String ingredient) { mIngredients.add(ingredient); }
    public void setIngredients(ArrayList<String> ingredients) { mIngredients = ingredients; }
    public void setShortDescription(String shortDescription) { mShortDescription.add(shortDescription); }
    public void setDescription(String description) { mDescription.add(description); }
    public void setVideoUrl(String videoUrl) { mVideoUrl.add(checkValidString(videoUrl)); }
    public void setThumbnailUrl(String thumbnailUrl) { mThumbnailUrl.add(checkValidString(thumbnailUrl)); }

    /* getter */
    public int getServings() { return mServings; }
    public String getRecipeName() { return mRecipeName; }
    public String getImagePath() { return mImagePath; }
    public String getIngredientsString() { return stringCombineHelper(mIngredients); }
    public ArrayList<String> getIngredientsArrList() { return mIngredients; }
    public String getShortDescription() { return mShortDescription.get(mStep); }
    public String getDescription() { return mDescription.get(mStep); }
    public String getVideoUrl() { return mVideoUrl.get(mStep); }
    public String getThumbnailUrl() { return mThumbnailUrl.get(mStep); }

    /* helper methods */
    public static Recipe copyRecipe(Recipe recipe) {
        Recipe recipeCopy = new Recipe();
        recipeCopy.setServings(recipe.getServings());
        recipeCopy.setRecipeName(recipe.getRecipeName());
        recipeCopy.setImagePath(recipe.getImagePath());
        recipeCopy.setIngredients(recipe.getIngredientsArrList());
        recipeCopy.setShortDescription(recipe.getShortDescription());
        recipeCopy.setDescription(recipe.getDescription());
        recipeCopy.setVideoUrl(recipe.getVideoUrl());
        recipeCopy.setThumbnailUrl(recipe.getThumbnailUrl());
        return recipeCopy;
    }

    public void nextStep() { mStep++; }

    public void resetStep() { mStep = 0; }

    private String stringCombineHelper(ArrayList<String> stringList) {
        String result = "";
        for (int i = 0; i < stringList.size(); i++) {
            result += stringList.get(i);
            if ( i < stringList.size() - 1 ) {
                result += "\n";
            }
        }
        return result;
    }

    private String checkValidString(String validString) {
        if (validString == null || TextUtils.isEmpty(validString)) return null;
        return validString;
    }

    private void constructorHelper() {
        mStep = 0; mServings= 0;
        mImagePath = null;
        mIngredients = new ArrayList<>();
        mShortDescription = new ArrayList<>();
        mDescription = new ArrayList<>();
        mVideoUrl = new ArrayList<>();
        mThumbnailUrl = new ArrayList<>();
    }
}
