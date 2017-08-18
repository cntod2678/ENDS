package com.cdj.ends.ui.news.newssearch;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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

import com.cdj.ends.R;
import com.cdj.ends.base.command.PageSwipeCommand;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.News;
import com.cdj.ends.ui.news.NewsItemViewModel;
import com.cdj.ends.ui.news.newssearch.viewmodel.CategoriesViewModel;
import com.cdj.ends.ui.news.newssearch.viewmodel.CategoriesViewModelImpl;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    private static final String SPINNER_STATE = "SPINNER_STATE";

    private static final int CATEGORY_PAGE_NUM = 5;

    private CategoryLatestAdpater categoryLatestAdpater;

    private CategoryFavoriteAdapter categoryFavoriteAdapter;

    private PageSwipeCommand viewPagerIndicatorCategory;

    private CategoriesViewModel categoriesFavoriteViewModel;

    private CategoriesViewModel categoriesLatestViewModel;



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

        categoryLatestAdpater = new CategoryLatestAdpater(getActivity());
        categoriesLatestViewModel = new CategoriesViewModelImpl(getActivity());
        categoriesLatestViewModel.setUpdateCategoryLatestListener(new NotifyUpdateViewModelListener<List<NewsItemViewModel>>() {
            @Override
            public void onUpdatedViewModel(List<NewsItemViewModel> viewModel) {
                categoryLatestAdpater.setData(viewModel);
            }
        });

        categoryFavoriteAdapter = new CategoryFavoriteAdapter();
        categoriesFavoriteViewModel = new CategoriesViewModelImpl(getActivity());
        categoriesFavoriteViewModel.setUpdateViewModelListener(new NotifyUpdateViewModelListener<List<NewsItemViewModel>>() {
            @Override
            public void onUpdatedViewModel(List<NewsItemViewModel> viewModel) {
                categoryFavoriteAdapter.replaceData(viewModel);
            }
        });
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager();
        setSpinner();
        setRecv();

        spinner.setSelection(0);
        categoriesLatestViewModel.fetchLatest();
    }

    @Override
    public void onStart() {
        super.onStart();
        categoriesLatestViewModel.fetchLatest();
    }

    private void setViewPager() {
        viewPagerCategory.setCurrentItem(0);
        timer = new Timer();
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
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
                handler.post(update);
            }
        }, 500, 3000);

        viewPagerCategory.setAdapter(categoryLatestAdpater);
    }

    private void setRecv() {
        recvCategoryLatest.setLayoutManager(staggeredGridLayoutManager);
        recvCategoryLatest.setAdapter(categoryFavoriteAdapter);
    }

    private void setSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriesFavoriteViewModel.fetchFamous(parent.getItemAtPosition(position).toString());
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
    public void onSaveInstanceState(Bundle outState) {
        int spinnerSelectedState = spinner.getSelectedItemPosition();
        outState.putInt(SPINNER_STATE, spinnerSelectedState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            spinner.setSelection(savedInstanceState.getInt(SPINNER_STATE));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        timer.cancel();
    }
}
