package com.btandjaja.www.bakingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.btandjaja.www.bakingrecipes.data.Recipe;
import com.btandjaja.www.bakingrecipes.ui.StepsFragment;

public class DetailActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener{

    public static Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecipe();
        setContentView(R.layout.activity_detail);
    }

    private void getRecipe() {
        Bundle data = getIntent().getExtras();
        if (data == null) {
            Toast.makeText(this, getResources().getString(R.string.invalid_recipe), Toast.LENGTH_LONG).show();
            finish();
        }
        mRecipe = data.getParcelable(Recipe.RECIPE);
    }

    @Override
    public void onStepSelected(String videoUrl, int step, String recipeName) {
        if (TextUtils.isEmpty(videoUrl)) {
            Toast.makeText(this, getResources().getString(R.string.video_not_available),
                    Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra(getResources().getString(R.string.video_url), videoUrl);
        intent.putExtra(getResources().getString(R.string.step), step);
        intent.putExtra(getResources().getString(R.string.name), recipeName);
        startActivity(intent);
    }
}
