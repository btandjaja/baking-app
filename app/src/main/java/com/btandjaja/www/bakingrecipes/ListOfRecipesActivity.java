package com.btandjaja.www.bakingrecipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfRecipesActivity extends AppCompatActivity {
    @BindView(R.id.test_display_tv) TextView testText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_recipes);
        ButterKnife.bind(this);

    }
}
