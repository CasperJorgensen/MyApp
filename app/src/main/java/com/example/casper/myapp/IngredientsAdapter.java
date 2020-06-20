package com.example.casper.myapp;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> ingredientsList;
    private Map<String, List<String>> testIngredient;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredients, ingredientList;

        public MyViewHolder(View view) {
            super(view);
            ingredients = (TextView) view.findViewById(R.id.recipe_card_ingredient);
            ingredientList = (TextView) view.findViewById(R.id.recipe_card_ingredient_list);
        }
    }

    public IngredientsAdapter(Context mContext, List<Recipe> ingredientsList) {
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
        int l = 0;
        int i = 0;
        Recipe recipe = ingredientsList.get(position);

        Map<String, List<String>> ingredientList = recipe.getIngredients();
        for (Map.Entry<String, List<String>> entry : ingredientList.entrySet()) {
            String key = entry.getKey();
            List value = entry.getValue();
            holder.ingredients.setText(key);
            for (i = 0; i < value.size(); i++) {
                holder.ingredientList.append(value.get(i).toString() + "\n");
            }

        }

//        for (i = 0; i < ingredientList.get().size(); i++) {
//            holder.ingredients.append(eachElement + "");
//            for (l = 0; l < ingredients; l++)
//            holder.ingredients.append("\n" + elementIngredients);
//
//        }
//        for (int i = 0; i < course.getIngredients().size(); i++) {
//            holder.ingredients.append(course.getIngredients().get(i) + "\n");
//        }
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }
}
