package com.example.casper.myapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Course> recipeList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, servings, meal;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.recipe_card_title);
            servings = (TextView) view.findViewById(R.id.recipe_card_servings);
            meal = (TextView) view.findViewById(R.id.recipe_card_meal);
        }
    }

    public RecipeAdapter(Context mContext, List<Course> recipeList) {
        this.mContext = mContext;
        this.recipeList = recipeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Course course = recipeList.get(position);
        holder.title.setText(course.getCourseName());
        holder.servings.setText("Serves: " + course.getNumberOfServings());
        holder.meal.setText("Meal: " + course.getMeal());


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}
