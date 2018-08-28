package com.btandjaja.www.bakingrecipes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.btandjaja.www.bakingrecipes.DetailActivity;
import com.btandjaja.www.bakingrecipes.ListOfRecipesActivity;
import com.btandjaja.www.bakingrecipes.R;
import com.btandjaja.www.bakingrecipes.data.InstructionAdapter;
import com.btandjaja.www.bakingrecipes.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {
    // define a new interface OnStepClickListener that triggers a callback to the host activity
    OnStepClickListener mCallback;
    Recipe mRecipe;
    @BindView(R.id.rv_recipe_instruction_list) RecyclerView mRecyclerView;
    @BindView(R.id.tv_recipe_title) TextView mRecipeName;

    public StepsFragment() {}

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    // OnStepClickListener interface, calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_list, container, false);
        ButterKnife.bind(this,rootView);
        mRecipe = DetailActivity.mRecipe;
        mRecipeName.setText(mRecipe.getRecipeName());
        InstructionAdapter instructionAdapter = new InstructionAdapter(getContext(), mRecipe);

        // default GridLayout columns
        int spanCount = Integer.valueOf(getString(R.string.default_gridView_1));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        mRecyclerView.setAdapter(instructionAdapter);

        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onStepSelected(mRecyclerView.getChildAdapterPosition(v) );
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Recipe.RECIPE, mRecipe);
    }
}
