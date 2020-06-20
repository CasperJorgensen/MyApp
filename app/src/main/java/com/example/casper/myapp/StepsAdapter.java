package com.example.casper.myapp;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> stepsList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView steps;

        public MyViewHolder(View view) {
            super(view);
            steps = (TextView) view.findViewById(R.id.recipe_card_steps);
        }
    }

    public StepsAdapter(Context mContext, List<Recipe> stepsList) {
        this.mContext = mContext;
        this.stepsList = stepsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card_steps, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Recipe recipe = stepsList.get(position);
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            holder.steps.append((i + 1)+ " - " + recipe.getSteps().get(i) + "\n");
        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }
}