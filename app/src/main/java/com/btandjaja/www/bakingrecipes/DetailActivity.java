package com.btandjaja.www.bakingrecipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_recipe_title) TextView mRecipeName;
    @BindView(R.id.tv_ingredients) TextView mIngredients;

    private static Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getRecipe();
        setIntroduction();
    }

    private void getRecipe() {
        Bundle data = getIntent().getExtras();
        mRecipe = data.getParcelable(Recipe.RECIPE);
    }
    public void setIntroduction() {
        mRecipeName.setText(mRecipe.getRecipeName());
        mIngredients.setText(mRecipe.getIngredientsString());
    }
}
