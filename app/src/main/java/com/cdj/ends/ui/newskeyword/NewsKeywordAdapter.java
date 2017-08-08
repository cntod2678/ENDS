package com.cdj.ends.ui.newskeyword;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsKeywordAdapter extends RecyclerView.Adapter<NewsKeywordAdapter.NewsViewHolder> {

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_keyword, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNewsKeywordItem;
        TextView txtTitleNews;

        public NewsViewHolder(View itemView) {
            super(itemView);
//            imgNewsKeywordItem = (ImageView) itemView.findViewById(R.id.img_news_keyword_item);
//            txtTitleNews = (TextView) itemView.findViewById(R.id.txtTitle_news);
        }

        void bind() {

        }
    }
}
