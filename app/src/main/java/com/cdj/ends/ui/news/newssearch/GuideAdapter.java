package com.cdj.ends.ui.news.newssearch;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdj.ends.R;
import com.cdj.ends.ui.news.NewsItemViewModel;

import java.util.Collections;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Dongjin on 2017. 8. 16..
 */

public class GuideAdapter extends PagerAdapter {

    private Context mContext;

    private List<NewsItemViewModel> categories;

    private List<String> str;

    public GuideAdapter() { setList(Collections.<NewsItemViewModel>emptyList());}

    public GuideAdapter(Context context, List<String> str) {
        mContext = context;
        this.str = str;
        //setList(Collections.<NewsItemViewModel>emptyList());
    }

    @Override
    public int getCount() {
        return str.size();
    }


    private void setList(List<NewsItemViewModel> newsList) {
        this.categories = checkNotNull(newsList);
    }

    public void replaceData(List<NewsItemViewModel> newsItemViewModels) {
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
        View viewItem = inflater.inflate(R.layout.item_guide, container, false);

        TextView txtCategoryTitle = (TextView) viewItem.findViewById(R.id.txtCategory_title);
        txtCategoryTitle.setText(str.get(position));

//        ImageView imgCategory = (ImageView) viewItem.findViewById(R.id.imgCategory);
//        Glide.with(mContext)
//                .load(categories.get(position).getUrl())
//                .placeholder(R.drawable.bbcnews)
//                .centerCrop()
//                .into(imgCategory);

        container.addView(viewItem);
        return viewItem;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }


}
