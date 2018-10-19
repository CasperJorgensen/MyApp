package com.example.casper.myapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.casper.myapp.helper.OnStartDragListener;
import com.example.casper.myapp.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;


public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener, OnStartDragListener {
    private List<String> stepsList;

    private ItemTouchHelper mItemTouchHelper;

    Context context;

    private RecyclerListAdapter adapter;

    private RecyclerView recyclerView;
    private Button buttonTest;
    private EditText editText;
    List<String> stringArrayList;
    String listing;
    private String TAG = "Woo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        stringArrayList = new ArrayList<>();

        adapter = new RecyclerListAdapter(context, stringArrayList, this);

        recyclerView = findViewById(R.id.recycler_view_create_recipe_steps);

        buttonTest = findViewById(R.id.buttonTest);
        editText = findViewById(R.id.editTextTest);
        findViewById(R.id.buttonTest).setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonTest) {
            stringArrayList.add(editText.getText().toString());
            adapter.notifyDataSetChanged();
            editText.setText("");
        }

    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}