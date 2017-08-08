package com.cdj.ends.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cdj.ends.ui.newskeyword.NewsKeywordFragment;
import com.cdj.ends.ui.newssearch.NewsSearchFragment;
import com.cdj.ends.ui.newssource.NewsSourceFragment;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0) {
            fragment = NewsSourceFragment.newInstance();
        }
        else if(position == 1) {
            fragment = NewsKeywordFragment.newInstance();
        }
        else {
            fragment = NewsSearchFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
