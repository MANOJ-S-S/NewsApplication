package com.test.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.newsapplication.model.Article;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

Context context;
ArrayList<Article> newsList;

    public MyRecyclerAdapter(Context context, ArrayList<Article> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfeed_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.newsTitle.setText(newsList.get(position).getTitle());
        holder.description.setText(newsList.get(position).getDescription());
        holder.publish_time.setText(newsList.get(position).getPublishedAt());
        Glide.with(context).load(newsList.get(position).getUrlToImage()).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle,description,publish_time;
        ImageView newsImage;
        View myItemview;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle= itemView.findViewById(R.id.news_title);
            newsImage= itemView.findViewById(R.id.news_image);
            description= itemView.findViewById(R.id.description);
            publish_time= itemView.findViewById(R.id.published_time);

        }
    }
}
