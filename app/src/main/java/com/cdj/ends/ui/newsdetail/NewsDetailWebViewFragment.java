package com.cdj.ends.ui.newsdetail;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.cdj.ends.R;
import com.cdj.ends.data.News;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewsDetailWebViewFragment extends Fragment {

    private static final String TAG = "NewsWebFragment";

    @BindView(R.id.btnChange_to_detail) Button btnChange;
    @BindView(R.id.webView_news) WebView webViewNews;
    @BindView(R.id.toolbar_detail_web) Toolbar toolbarDetailWeb;
    Unbinder unbinder;

    private News mNews;

    private NewsDetailChangeListener newsDetailChangeListener;

    private static NewsDetailWebViewFragment newsDetailWebViewFragment;

    public NewsDetailWebViewFragment() {}

    public static NewsDetailWebViewFragment newInstance(News news) {
        if(newsDetailWebViewFragment == null) {
            synchronized (NewsDetailFragment.class) {
                if(newsDetailWebViewFragment == null) {
                    newsDetailWebViewFragment = new NewsDetailWebViewFragment();
                }
            }
        }

        Bundle args = new Bundle();
        args.putParcelable(News.class.getName(), Parcels.wrap(news));
        newsDetailWebViewFragment.setArguments(args);
        return newsDetailWebViewFragment;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            newsDetailChangeListener = (NewsDetailChangeListener) getContext();
        } catch(ClassCastException cce) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            newsDetailChangeListener = (NewsDetailChangeListener) activity;
        } catch(ClassCastException cce) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mNews = Parcels.unwrap(getArguments().getParcelable(News.class.getName()));
            Log.d(TAG, mNews.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail_web, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar();

        webViewNews.loadUrl(mNews.getUrl());
    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarDetailWeb);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick(R.id.btnChange_to_detail)
    public void onChangeToDetailClicked(View v) {
        newsDetailChangeListener.changeDetailFragment(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
