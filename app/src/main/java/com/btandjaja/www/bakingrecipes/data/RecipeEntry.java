package com.btandjaja.www.bakingrecipes.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipelist")
public class RecipeEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mRecipeName, mIngredient, mVideoLink, mShortDescription, mDescription, mThumbnailUrl;
    private int mStepNum;

    @Ignore
    public RecipeEntry(String recipeName, String ingredient, String videoLink, String shortDescription,
                       String description, String thumbnailUrl, int stepNum) {
        this.mRecipeName = recipeName;
        this.mIngredient = ingredient;
        this.mVideoLink = videoLink;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mThumbnailUrl = thumbnailUrl;
        this.mStepNum = stepNum;
    }

    public RecipeEntry(int id, String recipeName, String ingredient, String videoLink, String shortDescription,
                       String description, String thumbnailUrl, int stepNum) {
        this.id = id;
        this.mRecipeName = recipeName;
        this.mIngredient = ingredient;
        this.mVideoLink = videoLink;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mThumbnailUrl = thumbnailUrl;
        this.mStepNum = stepNum;
    }

    // Setter
    public void setId(int id) { this.id = id; }
    public void setRecipeName(String recipeName) { this.mRecipeName = recipeName; }
    public void setIngredient(String ingredient) { this.mIngredient = ingredient; }
    public void setVideoLink(String videoLink) { this.mVideoLink = mVideoLink; }
    public void setStepNum(int stepNum) { this.mStepNum = stepNum; }

    // Getter
    public int getId() { return this.id; }
    public String getRecipeName() { return this.mRecipeName; }
    public String getIngredient() { return this.mIngredient; }
    public String getVideoLink() { return this.mVideoLink; }
    public int getStepNum() { return this.mStepNum; }
}
