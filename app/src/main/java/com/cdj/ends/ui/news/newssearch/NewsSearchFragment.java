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
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.design.widget.TabLayout;

import com.cdj.ends.R;
import com.cdj.ends.base.command.PageSwipeCommand;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.ui.news.NewsItemViewModel;
import com.cdj.ends.ui.news.newssearch.viewmodel.CategoriesViewModel;
import com.cdj.ends.ui.news.newssearch.viewmodel.CategoriesViewModelImpl;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewsSearchFragment extends Fragment {
    @BindView(R.id.viewPager_category) ViewPager viewPagerCategory;
    @BindView(R.id.tabLayout_favorite_category) TabLayout tabLayoutFavoriteCategory;
    @BindView(R.id.recvCategory_favorite) RecyclerView recvCategoryFavorite;
    @BindView(R.id.btnSpand_recv_height) Button btnSpandRecvHeight;
    @BindView(R.id.txtDate_category) TextView txtDateCategory;
    Unbinder unbinder;

    private static final String TAG = "NewsSearchFragment";

    private static final String STATE_SCROLL_POSITION = "STATE_SCROLL_POSITION";

    private static final int CATEGORY_PAGE_NUM = 5;

    private CategoryLatestAdapter categoryLatestAdapter;

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

        categoryLatestAdapter = new CategoryLatestAdapter(getActivity());
        categoriesLatestViewModel = new CategoriesViewModelImpl(getActivity());
        categoriesLatestViewModel.setUpdateCategoryLatestListener(new NotifyUpdateViewModelListener<List<NewsItemViewModel>>() {
            @Override
            public void onUpdatedViewModel(List<NewsItemViewModel> viewModel) {
                categoryLatestAdapter.setData(viewModel);
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
        setDate();
        setViewPager();
        setRecv();
        setTabs();
    }

    @Override
    public void onStart() {
        super.onStart();
        categoriesLatestViewModel.fetchLatest();
        categoriesFavoriteViewModel.fetchFamous(tabLayoutFavoriteCategory.getTabAt(tabLayoutFavoriteCategory.getSelectedTabPosition()).getText().toString());
    }

    private void setDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.getDefault());
        String strDate = dateFormat.format(date);

        txtDateCategory.setText(strDate);
    }

    private void setViewPager() {
        viewPagerCategory.setAdapter(categoryLatestAdapter);

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

        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 3000);
    }

    private void setRecv() {
        recvCategoryFavorite.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        recvCategoryFavorite.setAdapter(categoryFavoriteAdapter);
        recvCategoryFavorite.setNestedScrollingEnabled(false);
    }

    private void setTabs() {
        tabLayoutFavoriteCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                categoriesFavoriteViewModel.fetchFamous(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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

        } else {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (getActivity().getApplicationContext().getResources().getDimension(R.dimen.recv_category_recv_original_height)));
            recv_height_flag = false;
            btnSpandRecvHeight.setSelected(false);
        }

        recvCategoryFavorite.setLayoutParams(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        timer.cancel();
    }
}
