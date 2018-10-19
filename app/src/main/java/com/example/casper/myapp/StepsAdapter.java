package com.example.casper.myapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Course> stepsList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView steps;

        public MyViewHolder(View view) {
            super(view);
            steps = (TextView) view.findViewById(R.id.recipe_card_steps);
        }
    }

    public StepsAdapter(Context mContext, List<Course> stepsList) {
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
        Course course = stepsList.get(position);
        for (int i = 0; i < course.getSteps().size(); i++) {
            holder.steps.append((i + 1)+ " - " + course.getSteps().get(i) + "\n");
        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }
}