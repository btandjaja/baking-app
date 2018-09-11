package com.btandjaja.www.bakingrecipes.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipelist")
public class RecipeEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String recipeName;
    private int arrayListIndex;

    @Ignore
    public RecipeEntry(String recipeName, int arrayListIndex) {
        this.recipeName = recipeName;
        this.arrayListIndex = arrayListIndex;
    }

    public RecipeEntry(int id, String recipeName, int arrayListIndex) {
        this.id = id;
        this.recipeName = recipeName;
        this.arrayListIndex = arrayListIndex;
    }

    // Setter
    public void setId(int id) { this.id = id; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
    public void setArrayListIndex(int arrayListIndex) { this.arrayListIndex = arrayListIndex; }

    // Getter
    public int getId() { return this.id; }
    public int getArrayListIndex() { return this.arrayListIndex; }
    public String getRecipeName() {return this.recipeName;}
}
