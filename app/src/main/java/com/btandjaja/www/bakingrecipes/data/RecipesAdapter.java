package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btandjaja.www.bakingrecipes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    /* declarations */
    private Context mContext;
    private static ArrayList<Recipe> mRecipesList;
    private static RecipeAdapterOnClickHandler mClickHandler;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForRecipeCard = R.layout.recipe_cards;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForRecipeCard, viewGroup, shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        Recipe recipe = mRecipesList.get(position);
        if (recipe == null) return;
        Picasso.with(mContext).load(recipe.getImagePath()).into(recipeViewHolder.mRecipeSnapShot);
    }

    @Override
    public int getItemCount() {
        return mRecipesList == null ? 0 : mRecipesList.size();
    }

    public RecipesAdapter(RecipeAdapterOnClickHandler clickHandler) { mClickHandler = clickHandler; }

    public void setRecipeList(Context context, ArrayList<Recipe> recipeList) {
        mContext = context;
        mRecipesList = recipeList;
        notifyDataSetChanged();
    }

    /* interface for onClick */
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    /* view holder */
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mRecipeSnapShot;

        private RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mRecipeSnapShot = itemView.findViewById(R.id.iv_recipe_snapshot);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /* get recipe from the correct position */
            Recipe recipe = Recipe.copyRecipe(mRecipesList.get(getAdapterPosition()));
            if (recipe == null) return;
            /* open the recipe */
            mClickHandler.onClick(recipe);
        }
    }
}
