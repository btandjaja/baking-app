package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    /* declarations */
    private Context mContext;
    private ArrayList<Recipe> mRecipesList;
    private RecipeAdapterOnClickHandler mClickHandler;

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
        String recipeName = recipe.getRecipeName();
        int imageDrawable;
        /* get image */
        if (recipeName.equals(mContext.getString(R.string.nutella_pie))) {
            imageDrawable = R.drawable.nutella_pie;
        } else if (recipeName.equals(mContext.getString(R.string.brownies))) {
            imageDrawable = R.drawable.brownies;
        } else if (recipeName.equals(mContext.getString(R.string.yellow_cake))) {
            imageDrawable = R.drawable.moist_yellow_cake;
        } else if (recipeName.equals(mContext.getString(R.string.cheesecake))) {
            imageDrawable = R.drawable.cheese_cake;
        } else {
            imageDrawable = R.drawable.no_image;
        }
        /* set image */
        Picasso.with(mContext).load(imageDrawable).into(recipeViewHolder.mRecipeSnapShot);
        /* set recipe name */
        recipeViewHolder.mRecipeName.setText(recipe.getRecipeName());
    }

    @Override
    public int getItemCount() {
        return mRecipesList == null ? 0 : mRecipesList.size();
    }

    public RecipesAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

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
        @BindView(R.id.iv_recipe_snapshot) ImageView mRecipeSnapShot;
        @BindView(R.id.tv_recipe_name) TextView mRecipeName;

        private RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
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
