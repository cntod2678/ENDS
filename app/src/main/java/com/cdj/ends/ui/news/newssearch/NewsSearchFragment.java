package com.cdj.ends.ui.news.newssearch;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cdj.ends.R;
import com.cdj.ends.base.command.PageSwipeCommand;
import com.cdj.ends.base.view.ViewPagerDotView;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.News;
import com.cdj.ends.ui.news.NewsItemViewModel;
import com.cdj.ends.ui.news.newssearch.viewmodel.CategoriesViewModel;
import com.cdj.ends.ui.news.newssearch.viewmodel.CategoriesViewModelImpl;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.constraint.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewsSearchFragment extends Fragment {
    @BindView(R.id.viewPager_category) ViewPager viewPagerCategory;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.recvCategory_latest) RecyclerView recvCategoryLatest;
    @BindView(R.id.btnSpand_recv_height) Button btnSpandRecvHeight;
    Unbinder unbinder;

    private GuideAdapter guideAdapter;

    private CategoryAdapter categoryAdapter;

    private List<News> newsList;

    private List<String> strList;

    private PageSwipeCommand viewPagerIndicatorCategory;

    private CategoriesViewModel categoriesViewModel;

    private static final int CATEGORY_PAGE_NUM = 3;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private Timer timer;

    private static boolean recv_height_flag = false;

    public NewsSearchFragment() {}

    public static NewsSearchFragment newInstance() {
        NewsSearchFragment newsSearchFragment = new NewsSearchFragment();
        Bundle args = new Bundle();
        newsSearchFragment.setArguments(args);
        return newsSearchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        categoryAdapter = new CategoryAdapter();

        strList = new ArrayList<>();
        strList.add("1");
        strList.add("2");
        strList.add("3");

        guideAdapter = new GuideAdapter(getActivity(), strList);

        categoriesViewModel = new CategoriesViewModelImpl(getActivity());
        categoriesViewModel.setUpdateViewModelListener(new NotifyUpdateViewModelListener<List<NewsItemViewModel>>() {
            @Override
            public void onUpdatedViewModel(List<NewsItemViewModel> viewModel) {
                categoryAdapter.replaceData(viewModel);
            }
        });
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        timer = new Timer();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager();
        setSpinner();
        setRecv();
//        categoriesViewModel.fetchCategory(spinner.getItemAtPosition(0).toString());
    }

    private void setViewPager() {
        viewPagerCategory.setAdapter(guideAdapter);
        viewPagerCategory.setCurrentItem(0);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            int currentPage = 0;
            public void run() {
                if (currentPage == CATEGORY_PAGE_NUM) {
                    currentPage = 0;
                }
                viewPagerCategory.setCurrentItem(currentPage++, true);
            }
        };
        timer .schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);
    }

    private void setRecv() {
        recvCategoryLatest.setLayoutManager(staggeredGridLayoutManager);
        recvCategoryLatest.setAdapter(categoryAdapter);
    }

    private void setSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriesViewModel.fetchCategory(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
    }

    @OnClick(R.id.btnSpand_recv_height)
    public void onSpandRecvHeightClicked(View view) {
        LinearLayout.LayoutParams params;
        if(!recv_height_flag) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            recv_height_flag = true;
            btnSpandRecvHeight.setSelected(true);
            recvCategoryLatest.setNestedScrollingEnabled(false);
        } else {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (getActivity().getApplicationContext().getResources().getDimension(R.dimen.recv_category_recv_original_height)));
            recv_height_flag = false;
            btnSpandRecvHeight.setSelected(false);
            recvCategoryLatest.setNestedScrollingEnabled(true);
        }

        recvCategoryLatest.setLayoutParams(params);
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
