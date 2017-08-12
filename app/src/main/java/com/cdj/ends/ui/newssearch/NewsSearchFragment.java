package com.cdj.ends.ui.newssearch;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdj.ends.R;

public class NewsSearchFragment extends Fragment {

    //private NewsSearchFragment newsSearchFragment;

    public NewsSearchFragment() {}

    public static NewsSearchFragment newInstance() {
//        if(newsSearchFragment == null) {
//            synchronized (NewsSearchFragment.class) {
//                if(newsSearchFragment == null) {
//                    newsSearchFragment = new NewsSearchFragment();
//
//                }
//            }
//        }
        NewsSearchFragment newsSearchFragment = new NewsSearchFragment();
        Bundle args = new Bundle();
        newsSearchFragment.setArguments(args);
        return newsSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_search, container, false);
    }
}
