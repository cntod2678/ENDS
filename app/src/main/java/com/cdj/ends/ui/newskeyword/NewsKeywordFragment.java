package com.cdj.ends.ui.newskeyword;

/**
 * Created by Dongjin on 2017. 8. 8..
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdj.ends.R;
import com.cdj.ends.ui.newskeyword.viewmodel.NewsViewModel;
import com.cdj.ends.ui.newskeyword.viewmodel.NewsViewModelImpl;

public class NewsKeywordFragment extends Fragment {

    public static final int PAGE_SIZE = 30;

    private boolean isLastPage = false;
    private int currentPage = 1;
    private int selectedSortByKey = 0;
    private int selectedSortOrderKey = 1;
    private boolean isLoading = false;

    private SwipeRefreshLayout refreshLayout;

    private RecyclerView recvNewsKeyword;

    private NewsKeywordAdapter newsKeywordAdapter;

    private NewsViewModel newsViewModel;

    private LinearLayoutManager layoutManager;

    public NewsKeywordFragment() {}

    public static NewsKeywordFragment newInstance() {
        Bundle args = new Bundle();
        NewsKeywordFragment fragment = new NewsKeywordFragment();
        fragment.setArguments(args);
        return fragment;
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
        newsViewModel = new NewsViewModelImpl();
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
    }

    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        recvNewsKeyword = (RecyclerView) view.findViewById(R.id.recv_news_keyword);
    }

    private void setRecyler() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsViewModel.refreshNews();
            }
        });

        /***/
        layoutManager = new LinearLayoutManager(getActivity());

        recvNewsKeyword.setLayoutManager(layoutManager);
        recvNewsKeyword.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recvNewsKeyword.setAdapter(newsKeywordAdapter);

        /***/
        recvNewsKeyword.addOnScrollListener(recyclerViewOnScrollListener);
    }

    /***/
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    // region Helper Methods
    private void loadMoreItems() {
        isLoading = true;

        currentPage += 1;
    }

}
