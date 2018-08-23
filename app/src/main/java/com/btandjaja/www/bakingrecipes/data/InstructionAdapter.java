package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>{
    private Context mContext;
    private RecipeOnClickHandler mHandler;
    private String mIngredients;
    private ArrayList<String> mShortDescriptions, mDescriptions, mVideoUrl;

    public InstructionAdapter(RecipeOnClickHandler handler) { mHandler = handler; }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForRecipeList = R.layout.recipe_instruction_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForRecipeList, parent, shouldAttachToParentImmediately);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        String shortDescription = mShortDescriptions.get(position);
        String description = mDescriptions.get(position);
        String videoUrl = mVideoUrl.get(position);
        if (shortDescription == null || description == null || videoUrl == null) return;

        int displayPlayLogo;
        String videoAvailability;
        if (videoUrl == null || TextUtils.isEmpty(videoUrl)) {
            displayPlayLogo = R.drawable.ic_do_not_disturb_red_24dp;
            videoAvailability = mContext.getString(R.string.video_not_available);
        } else {
            displayPlayLogo = R.drawable.ic_ondemand_video_24dp;
            videoAvailability = mContext.getString(R.string.video_available);
        }

        if (position == 0) {
            holder.mDescription.setText(mIngredients);
            holder.mShortDescription.setText(mContext.getString(R.string.ingredients));
        } else {
            holder.mDescription.setText(description);
            holder.mShortDescription.setText(shortDescription);
        }

        holder.mCurrentStep.setText(String.valueOf(position));
        holder.mValidVideo.setImageResource(displayPlayLogo);
        holder.mVideoAvailability.setText(videoAvailability);
    }

    @Override
    public int getItemCount() { return mVideoUrl == null ? 0 : mVideoUrl.size(); }

    /**
     * Set ingredients list
     */
    public void setList(Context context, ArrayList<String> shortDescriptions, ArrayList<String> descriptions,
                        ArrayList<String> videoUrl, String ingredients) {
        mContext = context;
        mShortDescriptions = shortDescriptions;
        mDescriptions = descriptions;
        mVideoUrl = videoUrl;
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public interface RecipeOnClickHandler {
        void OnClick(String videoUrl);
    }

    /* view holder */
    public class InstructionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_short_description) TextView mShortDescription;
        @BindView(R.id.tv_description) TextView mDescription;
        @BindView(R.id.tv_current_step) TextView mCurrentStep;
        @BindView(R.id.iv_valid_video) ImageView mValidVideo;
        @BindView(R.id.tv_video_availability) TextView mVideoAvailability;

        public InstructionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            String videoUrl = mVideoUrl.get(getAdapterPosition());
            mHandler.OnClick(videoUrl);
        }
    }
}
