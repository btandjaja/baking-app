package com.btandjaja.www.bakingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.btandjaja.www.bakingrecipes.data.InstructionAdapter;
import com.btandjaja.www.bakingrecipes.data.Recipe;
import com.btandjaja.www.bakingrecipes.ui.StepsFragment;

import org.w3c.dom.Text;

import butterknife.BindView;

public class DetailActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener,
    InstructionAdapter.InstructionAdapterOnClickHandler{

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

    /**
     * Never called.
     */
    @Override
    public void onStepSelected(int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
    }

    /**
     * Video logic for tablet and phone
     */
    @Override
    public void OnClick(int position) {
        String videoUrl = mRecipe.getVideoUrlList().get(position);
        if (ListOfRecipesActivity.mTabletMode) {
            TextView shortDesription = findViewById(R.id.tv_short_description_tablet);
            TextView description = findViewById(R.id.tv_description_tablet);
        } else {
            if (TextUtils.isEmpty(videoUrl)) {
                Toast.makeText(this, getResources().getString(R.string.video_not_available), Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, VideoActivity.class);
                intent.putExtra(getResources().getString(R.string.video_url), videoUrl);
                intent.putExtra(getResources().getString(R.string.step), position);
                intent.putExtra(getResources().getString(R.string.name), mRecipe.getRecipeName());
                startActivity(intent);
            }
        }
    }
}
