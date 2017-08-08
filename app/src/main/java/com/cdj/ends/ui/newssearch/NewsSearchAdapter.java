package com.cdj.ends.ui.newssearch;


/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class NewsSearchAdapter extends RecyclerView.Adapter<NewsSearchAdapter.NewsSearchViewHolder> {


    @Override
    public NewsSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NewsSearchViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class NewsSearchViewHolder extends RecyclerView.ViewHolder {

        public NewsSearchViewHolder(View itemView) {
            super(itemView);
        }
    }
}
