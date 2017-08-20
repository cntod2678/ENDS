package com.cdj.ends.base.command;

/**
 * Created by Dongjin on 2017. 8. 5..
 */

public interface PageSwipeCommand {
    void setNumOfCircles(int numOfCircles, float radius);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);

    void setPageTurnCommand(PageSwipeCommand pageSwipeCommand);

    void setVisibility(int mode);
}
