package com.cdj.ends.ui.newsdetail;

/**
 * Created by Dongjin on 2017. 8. 9..
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.data.News;
import com.cdj.ends.base.util.ClickableSpanTranslate;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailFragment extends Fragment implements View.OnClickListener {

    private final static String TAG = "NewsDetailFragment";

    @BindView(R.id.imgMain_news) ImageView imgMainNews;
    @BindView(R.id.txtTitle_detail) TextView txtTitleDetail;
    @BindView(R.id.txtDescription_detail) TextView txtDescriptionDetail;
    @BindView(R.id.btnChange_to_web) Button btnChangePage;
    @BindView(R.id.toolbar_news_detail) Toolbar toolbarNewsDetail;

    private News mNews;

    private NewsDetailChangeListener newsDetailChangeListener;

    public NewsDetailFragment() {}

    public static NewsDetailFragment newInstance(News news) {
        NewsDetailFragment fragment = new NewsDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setToolbar();
        setView();

        btnChangePage.setOnClickListener(this);
    }

    private void initView(View view) {
        toolbarNewsDetail = (Toolbar) view.findViewById(R.id.toolbar_news_detail);
        txtDescriptionDetail = (TextView) view.findViewById(R.id.txtDescription_detail);
    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarNewsDetail);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setView() {
        Glide.with(getActivity())
                .load(mNews.getUrlToImage())
                .fitCenter()
                .into(imgMainNews);

        makeTextViewClickable(mNews.getTitle(), txtTitleDetail);
        makeTextViewClickable(mNews.getDescription(), txtDescriptionDetail);
    }

    private void makeTextViewClickable(final String text, final TextView tv) {
        tv.setText("");
        String[] split = text.split("(?= )");
        SpannableString spannableString = null;

        for(String s : split) {
            spannableString=  new SpannableString(s);
            int length = s.length();
            String tag = s.trim();
            spannableString.setSpan(new ClickableSpanTranslate(getContext(), tag), 1, length,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv.append(spannableString);
        }
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.btnChange_to_web :
                newsDetailChangeListener.changeDetailFragment(this);
                break;
        }

    }
}
