package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.StepsViewHolder> {
    private Context mContext;
    private Recipe mRecipe;

    public InstructionAdapter(Context context, Recipe recipe) {
        mContext = context;
        mRecipe = recipe;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdStepList = R.layout.fragment_recipe_steps_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdStepList, parent, shouldAttachToParentImmediately);
        final StepsViewHolder viewHolder = new StepsViewHolder(view);
//        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(InstructionAdapter.class.getSimpleName(), "****inside oncreateviewholder");
//            }
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        String shortDescription = mRecipe.getShortDescriptionList().get(position);
        String description = mRecipe.getDescriptionList().get(position);
        String videoUrl = mRecipe.getVideoUrlList().get(position);

        if (position == 0) {
            holder.mDescription.setText(mRecipe.getIngredientsString());
            holder.mShortDescription.setText(mContext.getString(R.string.ingredients));
        } else {
            holder.mDescription.setText(description);
            holder.mShortDescription.setText(shortDescription);
        }

        int displayLogo;
        String videoAvailability;
        if (TextUtils.isEmpty(videoUrl)) {
            displayLogo = R.drawable.ic_do_not_disturb_red_24dp;
            videoAvailability = mContext.getString(R.string.video_not_available);
        } else {
            displayLogo = R.drawable.ic_ondemand_video_24dp;
            videoAvailability = mContext.getString(R.string.video_available);
        }

        holder.mCurrentStep.setText(String.valueOf(position));
        holder.mValidVideo.setImageResource(displayLogo);
        holder.mVideoAvailability.setText(videoAvailability);
    }

    @Override
    public long getItemId(int position) { return 0; }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getItemCount() { return mRecipe == null ? 0 : mRecipe.getSteps(); }

    public static class StepsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_short_description) TextView mShortDescription;
        @BindView(R.id.tv_description) TextView mDescription;
        @BindView(R.id.tv_current_step) TextView mCurrentStep;
        @BindView(R.id.iv_valid_video) ImageView mValidVideo;
        @BindView(R.id.tv_video_availability) TextView mVideoAvailability;
        @BindView(R.id.card_view) CardView mCardView;


        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

//    /**
//     * Creates a new cardView for each item referenced by the adapter
//     */
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        ViewHolder holder;
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.recipe_instruction_card, parent, false);
//            holder = new ViewHolder(view);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//        String shortDescription = mRecipe.getShortDescriptionList().get(position);
//        String description = mRecipe.getDescriptionList().get(position);
//        String videoUrl = mRecipe.getVideoUrlList().get(position);
//        if (shortDescription == null) shortDescription = "No Short Description Provided!\n";
//        if (description == null) description = "No Description Provided!\n";
//        if (videoUrl == null) videoUrl = "No Video Link Provided!\n";
//
//        int displayLogo;
//        String videoAvailability;
//
//        if (TextUtils.isEmpty(videoUrl)) {
//            displayLogo = R.drawable.ic_do_not_disturb_red_24dp;
//            videoAvailability = mContext.getString(R.string.video_not_available);
//        } else {
//            displayLogo = R.drawable.ic_ondemand_video_24dp;
//            videoAvailability = mContext.getString(R.string.video_available);
//        }
//
//        if (position == 0) {
//            holder.mDescription.setText(mRecipe.getIngredientsString());
//            holder.mShortDescription.setText(mContext.getString(R.string.ingredients));
//        } else {
//            holder.mDescription.setText(description);
//            holder.mShortDescription.setText(shortDescription);
//        }
//
//        holder.mCurrentStep.setText(String.valueOf(position));
//        holder.mValidVideo.setImageResource(displayLogo);
//        holder.mVideoAvailability.setText(videoAvailability);
//        return view;
//    }
//
//    static class ViewHolder {
//        @BindView(R.id.tv_short_description) TextView mShortDescription;
//        @BindView(R.id.tv_description) TextView mDescription;
//        @BindView(R.id.tv_current_step) TextView mCurrentStep;
//        @BindView(R.id.iv_valid_video) ImageView mValidVideo;
//        @BindView(R.id.tv_video_availability) TextView mVideoAvailability;
//
//        public ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }
}
