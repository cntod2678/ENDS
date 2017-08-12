package com.cdj.ends.ui.main;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cdj.ends.R;
import com.cdj.ends.base.command.PageSwipeCommand;
import com.cdj.ends.base.view.ViewPagerDotView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int VIEW_PAGER_MID = 1;
    private static final int VIEW_PAGER_CNT = 3;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar_main;
    private ViewPager viewPager_main;
    private MainPagerAdapter mainPagerAdapter;
    private PageSwipeCommand dotViewIndicator;

    private boolean mTerminateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setViewPager();
        setToolbar();
        setNavDrawer();
    }

    private void initView() {
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dotViewIndicator = (ViewPagerDotView) findViewById(R.id.dotView_indicator);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar_main);
        dotViewIndicator.setPageTurnCommand(new ViewPagerDotView(this));
        dotViewIndicator.setNumOfCircles(mainPagerAdapter.getCount(), getResources().getDimensionPixelSize(R.dimen.height_very_small));
    }

    private void setNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setViewPager() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager_main.setAdapter(mainPagerAdapter);
        viewPager_main.setCurrentItem(VIEW_PAGER_MID);
        viewPager_main.setOffscreenPageLimit(VIEW_PAGER_CNT);

        viewPager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dotViewIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                dotViewIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                dotViewIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //int depth = getSupportFragmentManager().getBackStackEntryCount();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(!mTerminateFlag) {
            Toast.makeText(MainActivity.this, "'뒤로' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            mTerminateFlag = true;
            mKillHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* 종료버튼이 한번 더 눌리지 않으면 Flag 값 복원 */
    Handler mKillHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                mTerminateFlag = false;
            }
        }
    };
}
