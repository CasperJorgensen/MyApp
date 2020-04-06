package com.example.casper.myapp;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private Context mContext;
    private List<Course> courseList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, date, author;
        public ImageView thumbnail, overflow, imageView;

        public MyViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.recipe_main_author);
            title = (TextView) view.findViewById(R.id.recipe_card_title);
            count = (TextView) view.findViewById(R.id.recipe_card_servings);
            thumbnail = (ImageView) view.findViewById(R.id.recipe_card_thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            date = (TextView) view.findViewById(R.id.recipe_main_date);
        }
    }

    public CourseAdapter(Context mContext, List<Course> courseList) {
        this.mContext = mContext;
        this.courseList = courseList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        String author = mContext.getString(R.string.recipe_author);
        String meal = mContext.getString(R.string.recipe_meal);
        String serves = mContext.getString(R.string.recipe_servers);
        String dates = mContext.getString(R.string.recipe_date_created);
        Course course = courseList.get(position);
        Date date = course.getDateCreated();

        FirebaseStorage storage;
        StorageReference storageReference;

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        storageReference.child(course.getPicturePath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext)
                        .load(uri)
                        .apply(new RequestOptions().centerCrop())
                        .into(holder.thumbnail);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Glide.with(mContext)
                        .load("https://firebasestorage.googleapis.com/v0/b/workingapp-28df8.appspot.com/o/images%2Fphone.png?alt=media&token=78ad2c6d-bdfe-4b82-8bc3-1648e8e604fd")
                        .into(holder.thumbnail);
            }
        });

        holder.author.setText(author + ": " + course.getAuthor());
        holder.title.setText(course.getCourseName());
        holder.count.setText(meal + ": " + course.getMeal() + "\n" + serves + ": " + course.getNumberOfServings());
        holder.date.setText(dates + ": " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(date));

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {}

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
