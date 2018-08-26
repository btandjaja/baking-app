package com.btandjaja.www.bakingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.data.Recipe;
import com.btandjaja.www.bakingrecipes.ui.StepsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener{
    @BindView(R.id.tv_recipe_title) TextView mRecipeName;
    @BindView(R.id.rv_recipe_instruction) RecyclerView mRecyclerView;

    private static Recipe mRecipe;
//    private InstructionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getRecipe();
        setTitle();
//        createAdapter();
//        setRecyclerView();
    }

    private void getRecipe() {
        Bundle data = getIntent().getExtras();
        mRecipe = data.getParcelable(Recipe.RECIPE);
    }
    public void setTitle() { mRecipeName.setText(mRecipe.getRecipeName()); }

    @Override
    public void onStepSelected(int position) {

    }

//    private void createAdapter() { mAdapter = new InstructionAdapter(this); }

//    private void setRecyclerView() {
//        mAdapter.setList(this, mRecipe.getShortDescriptionList(),
//                mRecipe.getDescriptionList(), mRecipe.getVideoUrlList(), mRecipe.getIngredientsString());
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        mRecyclerView.setAdapter(mAdapter);
//    }
//
//    @Override
//    public void OnClick(String videoUrl) {
//        if (TextUtils.isEmpty(videoUrl)) {
//            Toast.makeText(this, getResources().getString(R.string.video_not_available),
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        Intent intent = new Intent(this, VideoActivity.class);
//        intent.putExtra(getResources().getString(R.string.video_url), videoUrl);
//        startActivity(intent);
//    }
}
