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
        dest.writeInt(mServings);
        dest.writeString(mRecipeName); dest.writeString(mImagePath);
        dest.writeList(mIngredients);
        dest.writeList(mShortDescription);
        dest.writeList(mDescription);
        dest.writeList(mVideoUrl);
        dest.writeList(mThumbnailUrl);
    }

    public static final String RECIPE = "recipe";
    private int mServings;
    private String mRecipeName, mImagePath;
    private ArrayList<String> mIngredients, mShortDescription, mDescription, mVideoUrl, mThumbnailUrl;

    public Recipe() {
        mRecipeName = null;
        constructorHelper();
    }

    public Recipe(Parcel in) {
        mServings = in.readInt();
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
    public void setShortDescriptionList(ArrayList<String> shortDescriptionList) { mShortDescription = shortDescriptionList; }
    public void setDescriptionList(ArrayList<String> descriptionList) { mDescription = descriptionList; }
    public void setVideoUrlList(ArrayList<String> videoUrlList) { mVideoUrl = videoUrlList; }
    public void setThumbnailUrlList(ArrayList<String> thumbnailUrlList) { mThumbnailUrl = thumbnailUrlList; }

    /* getter */
    public int getServings() { return mServings; }
    public String getRecipeName() { return mRecipeName; }
    public String getImagePath() { return mImagePath; }
    public String getIngredientsString() { return stringCombineHelper(mIngredients); }
    public ArrayList<String> getIngredientsArrList() { return mIngredients; }
    public ArrayList<String> getShortDescriptionList() { return mShortDescription; }
    public ArrayList<String> getDescriptionList() { return mDescription; }
    public ArrayList<String> getVideoUrlList() { return mVideoUrl; }
    public ArrayList<String> getThumbnailUrlList() { return mThumbnailUrl; }

    /* helper methods */
    public static Recipe copyRecipe(Recipe recipe) {
        Recipe recipeCopy = new Recipe();
        recipeCopy.setServings(recipe.getServings());
        recipeCopy.setRecipeName(recipe.getRecipeName());
        recipeCopy.setImagePath(recipe.getImagePath());
        recipeCopy.setIngredients(recipe.getIngredientsArrList());
        recipeCopy.setShortDescriptionList(recipe.getShortDescriptionList());
        recipeCopy.setDescriptionList(recipe.getDescriptionList());
        recipeCopy.setVideoUrlList(recipe.getVideoUrlList());
        recipeCopy.setThumbnailUrlList(recipe.getThumbnailUrlList());
        return recipeCopy;
    }

    public int getSteps() { return mDescription.size(); }

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
        if (validString == null || TextUtils.isEmpty(validString)) return "";
        return validString;
    }

    private void constructorHelper() {
        mServings= 0;
        mImagePath = null;
        mIngredients = new ArrayList<>();
        mShortDescription = new ArrayList<>();
        mDescription = new ArrayList<>();
        mVideoUrl = new ArrayList<>();
        mThumbnailUrl = new ArrayList<>();
    }
}
