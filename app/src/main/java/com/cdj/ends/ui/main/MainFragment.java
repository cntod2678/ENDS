package com.cdj.ends.ui.main;

/**
 * Created by Dongjin on 2017. 8. 12..
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdj.ends.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private static String CURRENT_PAGE_NUM = "CURRENT_PAGE_NUM";

    @BindView(R.id.viewPager_main) ViewPager viewPagerMain;
    Unbinder unbinder;

    private MainFragmentAdapter mainPagerAdapter;

    private MainViewChange mainViewChange ;

    private static int current_page;

    public MainFragment() {}

    public static MainFragment newInstance(int page_num) {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_PAGE_NUM, page_num);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity) {
            try {
                mainViewChange = (MainViewChange) getContext();
            } catch (ClassCastException cce) {
                throw new ClassCastException(context.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mainViewChange = (MainViewChange) activity;
        } catch(ClassCastException cce) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            current_page = getArguments().getInt(CURRENT_PAGE_NUM);
        } else {
            current_page = 1;
        }
        mainPagerAdapter = new MainFragmentAdapter(getActivity().getSupportFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager();
    }

    private void setViewPager() {
        viewPagerMain.setAdapter(mainPagerAdapter);
        viewPagerMain.setCurrentItem(current_page);

        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mainViewChange.pageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mainViewChange.pageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mainViewChange.pageScrollStateChanged(state);
            }
        });
    }

    interface MainViewChange {
        void pageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void pageSelected(int position);
        void pageScrollStateChanged(int state);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
