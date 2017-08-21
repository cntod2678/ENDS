package com.cdj.ends.ui.news.scrap;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.ui.news.scrap.viewmodel.ScrapItemViewModel;

import java.util.Collections;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Dongjin on 2017. 8. 21..
 */

public class ScrapAdapter extends RecyclerView.Adapter<ScrapAdapter.ScrapItemHolder> {

    private List<ScrapItemViewModel> mScrapList;

    public ScrapAdapter() {
        setList(Collections.<ScrapItemViewModel>emptyList());
    }

    @Override
    public ScrapItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_scrap, parent, false);
        ScrapItemHolder scrapItemHolder = new ScrapItemHolder(view);
        return scrapItemHolder;
    }

    @Override
    public void onBindViewHolder(ScrapItemHolder holder, int position) {
        holder.bind(mScrapList.get(position));
    }

    @Override
    public int getItemCount() {
        return mScrapList.size();
    }

    private void setList(List<ScrapItemViewModel> newsList) {
        this.mScrapList = checkNotNull(newsList);
    }

    protected void replaceData(List<ScrapItemViewModel> scrapList) {
        setList(scrapList);
        notifyDataSetChanged();
    }

    class ScrapItemHolder extends RecyclerView.ViewHolder {
        CardView itemScrap;
        TextView txtScrapDate;
        TextView txtDeleteScrap;
        ImageView imgScrap;

        public ScrapItemHolder(View itemView) {
            super(itemView);
            itemScrap = (CardView) itemView.findViewById(R.id.item_scrap);
            txtScrapDate = (TextView) itemView.findViewById(R.id.txtScrap_date);
            txtDeleteScrap = (TextView) itemView.findViewById(R.id.txtDelete_scrap);
            imgScrap = (ImageView) itemView.findViewById(R.id.imgScrap);
        }

        final void bind(final ScrapItemViewModel viewModel) {
            itemScrap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.onScrapItemClick(itemView);
                }
            });

            Glide.with(itemView.getContext())
                    .load(viewModel.getUrlToImage())
                    .into(imgScrap);

            txtScrapDate.setText(viewModel.getScrapDate());

            txtDeleteScrap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mScrapList.remove(viewModel);
                    notifyDataSetChanged();
                }
            });

        }
    }
}
