package com.example.casper.myapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private List<Course> recipeList;

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

        FirebaseStorage storage;
        StorageReference storageReference;

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Course course = recipeList.get(position);

        storageReference.child(course.getPicturePath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext)
                        .load(uri)
                        .apply(new RequestOptions().centerCrop())
                        .into(holder.imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Glide.with(mContext)
                        .load("https://firebasestorage.googleapis.com/v0/b/workingapp-28df8.appspot.com/o/images%2Fphone.png?alt=media&token=78ad2c6d-bdfe-4b82-8bc3-1648e8e604fd")
                        .into(holder.imageView);
            }
        });
        holder.title.setText(course.getCourseName());
        holder.servings.setText("Serves: " + course.getNumberOfServings());
        holder.meal.setText("Meal: " + course.getMeal());


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}
