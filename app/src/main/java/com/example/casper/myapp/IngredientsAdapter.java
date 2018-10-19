package com.example.casper.myapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Course> ingredientsList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredients;

        public MyViewHolder(View view) {
            super(view);
            ingredients = (TextView) view.findViewById(R.id.recipe_card_ingredient);
        }
    }

    public IngredientsAdapter(Context mContext, List<Course> ingredientsList) {
        this.mContext = mContext;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card_ingredients, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Course course = ingredientsList.get(position);
        for (int i = 0; i < course.getIngredients().size(); i++) {
            holder.ingredients.append(course.getIngredients().get(i) + "\n");
        }
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }
}
