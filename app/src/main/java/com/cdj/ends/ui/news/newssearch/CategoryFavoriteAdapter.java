package com.cdj.ends.ui.news.newssearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.ui.news.NewsItemViewModel;

import java.util.Collections;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Dongjin on 2017. 8. 16..
 */

public class CategoryFavoriteAdapter extends RecyclerView.Adapter<CategoryFavoriteAdapter.CategoryViewHolder> {

    private List<NewsItemViewModel> mNewsList;

    public CategoryFavoriteAdapter() { setList(Collections.<NewsItemViewModel>emptyList()); }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_favorite, parent, false);
        CategoryViewHolder categoryHolder = new CategoryViewHolder(view);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.bind(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    private void setList(List<NewsItemViewModel> newsList) {
        this.mNewsList = checkNotNull(newsList);
    }

    public void replaceData(List<NewsItemViewModel> newsItemViewModelList) {
        setList(newsItemViewModelList);
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemCategory;
        TextView txtCategoryItemTitle;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            imgItemCategory = (ImageView) itemView.findViewById(R.id.imgItem_category);
            txtCategoryItemTitle = (TextView) itemView.findViewById(R.id.txtCategory_item_title);
        }

        public void bind(final NewsItemViewModel viewModel) {
            Glide.with(itemView.getContext())
                    .load(viewModel.getUrlToImage())
                    .centerCrop()
                    .into(imgItemCategory);

            txtCategoryItemTitle.setText(viewModel.getTitle());
        }
    }
}
