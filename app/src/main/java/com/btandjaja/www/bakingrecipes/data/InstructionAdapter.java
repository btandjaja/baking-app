package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionAdapter extends BaseAdapter {
    private Context mContext;
    private Recipe mRecipe;
//    private RecipeOnClickHandler mHandler;
//    private String mIngredients;
//    private ArrayList<String> mShortDescriptions, mDescriptions, mVideoUrl;

    @BindView(R.id.tv_short_description) TextView mShortDescription;
    @BindView(R.id.tv_description) TextView mDescription;
    @BindView(R.id.tv_current_step) TextView mCurrentStep;
    @BindView(R.id.iv_valid_video) ImageView mValidVideo;
    @BindView(R.id.tv_video_availability) TextView mVideoAvailability;

    public InstructionAdapter(Context context, Recipe recipe) {
        mContext = context;
        mRecipe = recipe;
    }

    /**
     * Returns the number of items the adapter will display
     * @return
     */
    @Override
    public int getCount() { return mRecipe == null ? 0 : mRecipe.getDescriptionList().size(); }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return 0; }

    /**
     * Creates a new cardView for each item referenced by the adapter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ButterKnife.bind(this, convertView);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recipe_instruction_card, parent, false);
        }
        String shortDescription = mRecipe.getShortDescriptionList().get(position);
        String description = mRecipe.getDescriptionList().get(position);
        String videoUrl = mRecipe.getVideoUrlList().get(position);
        if (shortDescription == null) shortDescription = "No Short Description Provided!\n";
        if (description == null) description = "No Description Provided!\n";
        if (videoUrl == null) videoUrl = "No Video Link Provided!\n";

        int displayLogo;
        String videoAvailability;

        if (TextUtils.isEmpty(videoUrl)) {
            displayLogo = R.drawable.ic_do_not_disturb_red_24dp;
            videoAvailability = mContext.getString(R.string.video_not_available);
        } else {
            displayLogo = R.drawable.ic_ondemand_video_24dp;
            videoAvailability = mContext.getString(R.string.video_available);
        }

        if (position == 0) {
            mDescription.setText(mRecipe.getIngredientsString());
            mShortDescription.setText(mContext.getString(R.string.ingredients));
        } else {
            mDescription.setText(mRecipe.getDescriptionList().get(position));
            mShortDescription.setText(mRecipe.getShortDescriptionList().get(position));
        }
        mCurrentStep.setText(String.valueOf(position));
        mValidVideo.setImageResource(displayLogo);
        mVideoAvailability.setText(videoAvailability);
        return convertView;
    }

//    public InstructionAdapter(RecipeOnClickHandler handler) { mHandler = handler; }

//    @NonNull
//    @Override
//    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        int layoutIdForRecipeList = R.layout.recipe_instruction_card;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        boolean shouldAttachToParentImmediately = false;
//        View view = inflater.inflate(layoutIdForRecipeList, parent, shouldAttachToParentImmediately);
//        return new InstructionViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
//        String shortDescription = mShortDescriptions.get(position);
//        String description = mDescriptions.get(position);
//        String videoUrl = mVideoUrl.get(position);
//        if (shortDescription == null || description == null || videoUrl == null) return;
//
//        int displayPlayLogo;
//        String videoAvailability;
//        if (videoUrl == null || TextUtils.isEmpty(videoUrl)) {
//            displayPlayLogo = R.drawable.ic_do_not_disturb_red_24dp;
//            videoAvailability = mContext.getString(R.string.video_not_available);
//        } else {
//            displayPlayLogo = R.drawable.ic_ondemand_video_24dp;
//            videoAvailability = mContext.getString(R.string.video_available);
//        }
//
//        if (position == 0) {
//            holder.mDescription.setText(mIngredients);
//            holder.mShortDescription.setText(mContext.getString(R.string.ingredients));
//        } else {
//            holder.mDescription.setText(description);
//            holder.mShortDescription.setText(shortDescription);
//        }
//
//        holder.mCurrentStep.setText(String.valueOf(position));
//        holder.mValidVideo.setImageResource(displayPlayLogo);
//        holder.mVideoAvailability.setText(videoAvailability);
//    }

//    @Override
//    public int getItemCount() { return mVideoUrl == null ? 0 : mVideoUrl.size(); }

    /**
     * Set ingredients list
     */
//    public void setList(Context context, ArrayList<String> shortDescriptions, ArrayList<String> descriptions,
//                        ArrayList<String> videoUrl, String ingredients) {
//        mContext = context;
//        mShortDescriptions = shortDescriptions;
//        mDescriptions = descriptions;
//        mVideoUrl = videoUrl;
//        mIngredients = ingredients;
//        notifyDataSetChanged();
//    }

//    public interface RecipeOnClickHandler {
//        void OnClick(String videoUrl);
//    }

    /* view holder */
//    public class InstructionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        @BindView(R.id.tv_short_description) TextView mShortDescription;
//        @BindView(R.id.tv_description) TextView mDescription;
//        @BindView(R.id.tv_current_step) TextView mCurrentStep;
//        @BindView(R.id.iv_valid_video) ImageView mValidVideo;
//        @BindView(R.id.tv_video_availability) TextView mVideoAvailability;
//
//        public InstructionViewHolder(View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//            ButterKnife.bind(this, itemView);
//        }
//
//        @Override
//        public void onClick(View v) {
//            String videoUrl = mVideoUrl.get(getAdapterPosition());
//            mHandler.OnClick(videoUrl);
//        }
//    }
}
