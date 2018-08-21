package com.btandjaja.www.bakingrecipes.data;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>{

    public InstructionAdapter() {
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class InstructionViewHolder extends RecyclerView.ViewHolder {

        public InstructionViewHolder(View itemView) {
            super(itemView);
        }
    }
}
