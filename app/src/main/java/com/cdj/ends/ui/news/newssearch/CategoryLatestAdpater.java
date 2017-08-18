package com.cdj.ends.ui.news.newssearch;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.ui.news.NewsItemViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Dongjin on 2017. 8. 16..
 */

public class CategoryLatestAdpater extends PagerAdapter {

    private Context mContext;

    private List<NewsItemViewModel> categories;


    public CategoryLatestAdpater() { setList(Collections.<NewsItemViewModel> emptyList());}

    @Override
    public int getCount() {
        return categories.size();
    }

    public CategoryLatestAdpater(Context context) {
        mContext = context;
        categories = new ArrayList<>();
        setList(Collections.<NewsItemViewModel> emptyList());
    }

    private void setList(List<NewsItemViewModel> newsList) {
        this.categories = checkNotNull(newsList);
    }

    public void setData(List<NewsItemViewModel> newsItemViewModels) {
        setList(newsItemViewModels);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.item_category_latest, container, false);

        TextView txtCategoryTitle = (TextView) viewItem.findViewById(R.id.txtCategory_title);
        txtCategoryTitle.setText(categories.get(position).getTitle());

        ImageView imgCategory = (ImageView) viewItem.findViewById(R.id.imgCategory);
        Glide.with(mContext)
                .load(categories.get(position).getUrlToImage())
                .centerCrop()
                .into(imgCategory);

        container.addView(viewItem);
        return viewItem;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}
