package com.cdj.ends.ui.newssource;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.cdj.ends.R;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.NewsSource;
import com.cdj.ends.ui.newssource.viewmodel.SourceItemViewModel;
import com.cdj.ends.ui.newssource.viewmodel.SourceViewModel;
import com.cdj.ends.ui.newssource.viewmodel.SourceViewModelImpl;

import java.util.ArrayList;
import java.util.List;

public class NewsSourceFragment extends Fragment {

    private static final String TAG = "NewsSourceFragment";

    private RecyclerView recvNewsSource;

    private SourceViewModel sourceViewModel;

    private List<NewsSource> sourceList;

    private NewsSourceAdapter newsSourceAdapter;

    public NewsSourceFragment() {}

    public static NewsSourceFragment newInstance() {
        Bundle args = new Bundle();
        NewsSourceFragment fragment = new NewsSourceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsSourceAdapter = new NewsSourceAdapter();

        sourceList = new ArrayList<>();

        sourceViewModel = new SourceViewModelImpl();
        sourceViewModel.setUpdateViewModelListener(new NotifyUpdateViewModelListener<List<SourceItemViewModel>>() {
            @Override
            public void onUpdatedViewModel(List<SourceItemViewModel> viewModel) {
                newsSourceAdapter.replaceData(viewModel);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_source, container, false);
        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecycler();
        sourceViewModel.fetchSource();
    }

    private void initView(View view) {
        recvNewsSource = (RecyclerView) view.findViewById(R.id.recv_news_source);
    }

    private void setRecycler() {
        recvNewsSource.setLayoutManager(new LinearLayoutManager(getActivity()));
        recvNewsSource.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recvNewsSource.setAdapter(newsSourceAdapter);
    }
}
