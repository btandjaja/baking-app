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
        boolean noImage = recipe.getImagePath() == null;
        int imageDrawable;

        switch (position) {
            case 0:
                imageDrawable = R.drawable.nutella_pie;
                break;
            case 1:
                imageDrawable = R.drawable.brownies;
                break;
            case 2:
                imageDrawable = R.drawable.moist_yellow_cake;
                break;
            case 3:
                imageDrawable = R.drawable.cheese_cake;
                break;
            default:
                imageDrawable = R.drawable.no_image;
                break;
        }

        if (noImage) {
            Picasso.with(mContext).load(recipe.getImagePath()).into(recipeViewHolder.mRecipeSnapShot);
        } else {
            Picasso.with(mContext).load(imageDrawable).into(recipeViewHolder.mRecipeSnapShot);
        }
        recipeViewHolder.mBriefInfo.setText(recipe.getRecipeName());
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
        @BindView(R.id.iv_recipe_snapshot)
        ImageView mRecipeSnapShot;
        @BindView(R.id.tv_brief_recipe_info)
        TextView mBriefInfo;

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
