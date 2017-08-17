package com.cdj.ends.ui.newssource;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.ui.newssource.viewmodel.SourceItemViewModel;

import java.util.Collections;
import java.util.List;
import static com.cdj.ends.Config.LOCAL_HOST_URL;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceAdapter.NewsSourceViewHolder> {

    private static final String TAG = "NewsSourceAdapter";

    private List<SourceItemViewModel> mSourceList;

    public NewsSourceAdapter() {
        setList(Collections.<SourceItemViewModel>emptyList());
    }

    @Override
    public NewsSourceAdapter.NewsSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_source, parent, false);
        NewsSourceViewHolder viewHolder = new NewsSourceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsSourceAdapter.NewsSourceViewHolder holder, int position) {
        holder.bind(mSourceList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSourceList.size();
    }


    private void setList(List<SourceItemViewModel> sources) {
        this.mSourceList = checkNotNull(sources);
    }

    public void replaceData(List<SourceItemViewModel> sources) {
        setList(sources);
        notifyDataSetChanged();
    }

    static class NewsSourceViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSourceLogo;
        TextView txtSourceTitle;
        TextView txtSourceDes;
        LinearLayout itemSource;

        public NewsSourceViewHolder(View itemView) {
            super(itemView);

            itemSource = (LinearLayout) itemView.findViewById(R.id.item_source);
            txtSourceTitle = (TextView) itemView.findViewById(R.id.txtSource_title);
            txtSourceDes = (TextView) itemView.findViewById(R.id.txtSource_des);
            imgSourceLogo = (ImageView) itemView.findViewById(R.id.imgSource_Logo);
        }

        void bind(final SourceItemViewModel viewModel) {
            itemSource.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.onSourceItemClick(itemView);
                }
            });

            txtSourceTitle.setText(viewModel.getName());
            txtSourceDes.setText(viewModel.getDescription());

            Glide.with(itemView.getContext())
                    .load(LOCAL_HOST_URL + "images/" + viewModel.getName() + ".PNG")
                    .fitCenter()
                    .placeholder(R.drawable.aljazeeraenglish)
                    .into(imgSourceLogo);
            Log.d(TAG, LOCAL_HOST_URL + "images/" + viewModel.getName() + ".PNG");
        }
    }
}
