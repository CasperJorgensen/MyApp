package com.example.casper.myapp;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> recipeList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, servings, meal;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.recipe_card_title);
            servings = (TextView) view.findViewById(R.id.recipe_card_servings);
            meal = (TextView) view.findViewById(R.id.recipe_card_meal);
            imageView = (ImageView) view.findViewById(R.id.recipe_image);
        }
    }

    public RecipeAdapter(Context mContext, List<Recipe> recipeList) {
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

        FirebaseStorage storage;
        StorageReference storageReference;

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Recipe recipe = recipeList.get(position);

        Glide.with(mContext)
                .load("https://madenimitliv.dk/wp-content/uploads/2019/06/DSC_0014.jpg")
                .into(holder.imageView);

//        storageReference.child(recipe.getPicturePath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(mContext)
//                        .load("https://madenimitliv.dk/wp-content/uploads/2019/06/DSC_0014.jpg")
////                        .apply(new RequestOptions().centerCrop())
//                        .into(holder.imageView);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Glide.with(mContext)
//                        .load("https://madenimitliv.dk/wp-content/uploads/2019/06/DSC_0014.jpg")
//                        .into(holder.imageView);
//            }
//        });
//        holder.title.setText(recipe.getTitle());
//        holder.servings.setText("Serves: " + recipe.getNumberOfServings());
//        holder.meal.setText("Meal: " + recipe.getMeal());

        holder.title.setText("Title");
        holder.servings.setText("Serves: ");
        holder.meal.setText("Meal: ");


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}
