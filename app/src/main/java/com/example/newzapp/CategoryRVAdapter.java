package com.example.newzapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModalArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModalArrayList = categoryRVModalArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    // 1. set view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_rv_item, parent, false);

        return new ViewHolder(view);
    }
    //2 create class ViewHolder  initialize textView and other views to java
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView TVCategory;
        private ImageView IVCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVCategory = itemView.findViewById(R.id.TVCategory);
            IVCategory = itemView.findViewById(R.id.IVCategory);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {

        CategoryRVModal categoryRVModal = categoryRVModalArrayList.get(position);
        holder.TVCategory.setText(categoryRVModal.getCategory());
        Picasso.get().load(categoryRVModal.getCategoryImageUrl()).into(holder.IVCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModalArrayList.size();
    }



    interface CategoryClickInterface{
        void onCategoryclick(int position);
    }
}
