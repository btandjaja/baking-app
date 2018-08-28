package com.btandjaja.www.bakingrecipes.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.R;

import butterknife.BindView;

public class RightPlaneFragment extends Fragment {
    @BindView(R.id.tv_short_description_tablet) TextView mShortDescription;
    @BindView(R.id.tv_description_tablet) TextView mDescription;

    public RightPlaneFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
