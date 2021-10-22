package com.example.newzapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_rv_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subheading.setText(articles.getDescription());
        holder.newsheading.setText(articles.getTitle());
        //Picasso get the image from url and .into will set that image to imageview
        Picasso.get().load(articles.getUrlToImage()).into(holder.IVNews);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("description",articles.getDescription());
                intent.putExtra("image",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView newsheading,subheading;
        private ImageView IVNews;
        public ViewHolder(View view) {
            super(view);
            newsheading = view.findViewById(R.id.newsheading);
            subheading = view.findViewById(R.id.subheading);
            IVNews = view.findViewById(R.id.IVNews);
        }
    }

}
