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

public class NewsDetailWebViewFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "NewsWebFragment";

    @BindView(R.id.btnChange_to_detail) Button btnChange;
    @BindView(R.id.webView_news) WebView webViewNews;
    @BindView(R.id.toolbar_web_detail) Toolbar toolbarWebDetail;

    private News mNews;

    private NewsDetailChangeListener newsDetailChangeListener;

    public NewsDetailWebViewFragment() {}

    public static NewsDetailWebViewFragment newInstance(News news) {
        NewsDetailWebViewFragment fragment = new NewsDetailWebViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(News.class.getName(), Parcels.wrap(news));
        fragment.setArguments(args);
        return fragment;
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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setToolbar();

        webViewNews.loadUrl(mNews.getUrl());
        btnChange.setOnClickListener(this);
    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarWebDetail);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnChange_to_detail :
                newsDetailChangeListener.changeDetailFragment(this);
                break;
        }
    }
}
