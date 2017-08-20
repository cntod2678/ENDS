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

public class MainFragment extends Fragment {
    private static final int VIEW_PAGER_CNT = 3;

    private ViewPager viewPager_main;
    private MainFragmentAdapter mainPagerAdapter;

    private MainViewChange mainViewChange ;

    private static String CURRENT_PAGE_NUM = "CURRENT_PAGE_NUM";

    private static int current_page;

    public MainFragment () {}

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        viewPager_main = (ViewPager) view.findViewById(R.id.viewPager_main);
        setViewPager();
        return view;
    }

    private void setViewPager() {
        mainPagerAdapter = new MainFragmentAdapter(getActivity().getSupportFragmentManager());
        viewPager_main.setAdapter(mainPagerAdapter);
        viewPager_main.setCurrentItem(current_page);
        Log.d(CURRENT_PAGE_NUM, "curr : " + current_page);
        viewPager_main.setOffscreenPageLimit(VIEW_PAGER_CNT);

        viewPager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainViewChange = null;
    }
}
