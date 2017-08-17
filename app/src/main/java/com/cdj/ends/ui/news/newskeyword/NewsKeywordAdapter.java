package com.cdj.ends.ui.news.newskeyword;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.ui.news.NewsItemViewModel;

import java.util.Collections;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsKeywordAdapter extends RecyclerView.Adapter<NewsKeywordAdapter.NewsViewHolder> {

    private List<NewsItemViewModel> mNewsList;

    public NewsKeywordAdapter() {
        setList(Collections.<NewsItemViewModel>emptyList());
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_keyword, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    private void setList(List<NewsItemViewModel> newsList) {
        this.mNewsList = checkNotNull(newsList);
    }

    public void replaceData(List<NewsItemViewModel> newsList) {
        setList(newsList);
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView newsItem;
        TextView txtSource;
        TextView txtTitle;
        TextView txtAuthor;
        TextView txtPublished;
        TextView txtDescription;
        ImageView imgNews;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsItem = (CardView) itemView.findViewById(R.id.news_item);
            txtSource = (TextView) itemView.findViewById(R.id.txtSource);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtPublished = (TextView) itemView.findViewById(R.id.txtPublished);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);
        }

        void bind(final NewsItemViewModel viewModel) {
            newsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.onNewsItemClick(itemView);
                }
            });

            txtSource.setText(viewModel.getSource());
            txtTitle.setText(viewModel.getTitle());

            if(viewModel.getAuthor() == null) {
                txtAuthor.setVisibility(View.GONE);
            } else {
                txtAuthor.setText(viewModel.getAuthor());
            }

            txtPublished.setText(viewModel.getPublishedAt());
            txtDescription.setText(viewModel.getDescription());

            Glide.with(itemView.getContext())
                    .load(viewModel.getUrlToImage())
                    .centerCrop()
                    .placeholder(R.drawable.abc)
                    .into(imgNews);
        }
    }
}
