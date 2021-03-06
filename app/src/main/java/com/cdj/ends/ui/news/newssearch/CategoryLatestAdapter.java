package com.cdj.ends.ui.news.newssearch;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.constraint.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.ui.news.NewsItemViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Dongjin on 2017. 8. 16..
 */

public class CategoryLatestAdapter extends PagerAdapter {

    private Context mContext;

    private List<NewsItemViewModel> categories;

    public CategoryLatestAdapter() { setList(Collections.<NewsItemViewModel> emptyList());}

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    public CategoryLatestAdapter(Context context) {
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
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        final View viewItem = inflater.inflate(R.layout.item_category_latest, container, false);

        ConstraintLayout newsItem = (ConstraintLayout) viewItem.findViewById(R.id.container_category_latest);
        TextView txtCategory = (TextView) viewItem.findViewById(R.id.txtCategory);
        TextView txtCategoryTitle = (TextView) viewItem.findViewById(R.id.txtCategory_title);
        ImageView imgCategory = (ImageView) viewItem.findViewById(R.id.imgCategory);

        newsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.get(position).onNewsItemClick(viewItem);
            }
        });

        txtCategory.setText(categories.get(position).getCategory());
        txtCategoryTitle.setText(categories.get(position).getTitle());

        Glide.with(mContext)
                .load(categories.get(position).getUrlToImage())
                .bitmapTransform(new BlurTransformation( mContext ) )
                .centerCrop()
                .into(imgCategory);

        container.addView(viewItem);
        return viewItem;
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
