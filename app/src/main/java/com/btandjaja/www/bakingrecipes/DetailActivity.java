package com.btandjaja.www.bakingrecipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_recipe_title) TextView mRecipeName;
    @BindView(R.id.tv_ingredients) TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    public void setIntroduction() {
//        mRecipeName.setText();
    }
}
