package com.cdj.ends.ui.newskeyword;

/**
 * Created by Dongjin on 2017. 8. 8..
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdj.ends.R;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.ui.newskeyword.viewmodel.NewsItemViewModel;
import com.cdj.ends.ui.newskeyword.viewmodel.NewsViewModel;
import com.cdj.ends.ui.newskeyword.viewmodel.NewsViewModelImpl;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.List;

public class NewsKeywordFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;

    private ShimmerRecyclerView recvNewsKeyword;

    private NewsKeywordAdapter newsKeywordAdapter;

    private NewsViewModel newsViewModel;

    private LinearLayoutManager layoutManager;

    private FloatingActionButton fabAddKeywrod;

    //private static NewsKeywordFragment newsKeywordFragment;

    public NewsKeywordFragment() {}

    public static NewsKeywordFragment newInstance() {
//        if(newsKeywordFragment == null) {
//            synchronized (NewsKeywordFragment.class) {
//                if(newsKeywordFragment == null) {
//                    newsKeywordFragment = new NewsKeywordFragment();
//                }
//            }
//        }

        NewsKeywordFragment newsKeywordFragment = new NewsKeywordFragment();
        Bundle args = new Bundle();
        newsKeywordFragment.setArguments(args);
        return newsKeywordFragment;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsKeywordAdapter = new NewsKeywordAdapter();
        newsViewModel = new NewsViewModelImpl(getActivity().getApplicationContext());
        newsViewModel.setUpdateViewModelListener(new NotifyUpdateViewModelListener<List<NewsItemViewModel>>() {
            @Override
            public void onUpdatedViewModel(List<NewsItemViewModel> viewModel) {
                newsKeywordAdapter.replaceData(viewModel);
                showRecvLoadiing();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_keyword, container, false);
        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyler();
        newsViewModel.fetchNews();

        fabAddKeywrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        recvNewsKeyword = (ShimmerRecyclerView) view.findViewById(R.id.recv_news_keyword);
        fabAddKeywrod = (FloatingActionButton) view.findViewById(R.id.fabAdd_keyword);
    }

    private void setRecyler() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsViewModel.refreshNews();
            }
        });

        layoutManager = new LinearLayoutManager(getActivity());
        recvNewsKeyword.setLayoutManager(layoutManager);
        recvNewsKeyword.setAdapter(newsKeywordAdapter);
        showRecvLoadiing();
    }

    private void showRecvLoadiing() {
        recvNewsKeyword.showShimmerAdapter();
        recvNewsKeyword.postDelayed(new Runnable() {
            @Override
            public void run() {
                recvNewsKeyword.hideShimmerAdapter();
            }
        }, 2000);
    }
}
