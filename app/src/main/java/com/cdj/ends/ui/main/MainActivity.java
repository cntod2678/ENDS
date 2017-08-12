package com.cdj.ends.ui.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentTransaction;
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
import com.cdj.ends.ui.keyword.KeywordActivity;
import com.cdj.ends.ui.word.WordActivity;


import static com.cdj.ends.ui.main.MainFragmentAdapter.PAGE_NUM;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    MainFragment.MainViewChange {

    private PageSwipeCommand dotViewIndicator;
    private Toolbar toolbar_main;
    private DrawerLayout drawerLayout;
    private boolean mTerminateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, MainFragment.newInstance(), "MAIN_TAG").commit();
        }

        initView();
        setToolbar();
        setNavDrawer();
    }

    private void initView() {
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dotViewIndicator = (ViewPagerDotView) findViewById(R.id.dotView_indicator);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dotViewIndicator.setPageTurnCommand(new ViewPagerDotView(this));
        dotViewIndicator.setNumOfCircles(PAGE_NUM, getResources().getDimensionPixelSize(R.dimen.indicator_radius_2));
    }

    private void setNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        }
        else if(!mTerminateFlag) {
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_source) {

        } else if (id == R.id.nav_keyword) {
            getSupportFragmentManager().popBackStack();
            fragmentTransaction.replace(R.id.main_frame, MainFragment.newInstance()).addToBackStack(null).commit();
        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_word) {
//            getSupportFragmentManager().popBackStack();
//            fragmentTransaction.replace(R.id.main_frame, WordFragment.newInstance()).addToBackStack(null).commit();
            Intent intent = new Intent(getApplicationContext(), WordActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_add_keyword) {
            Intent intent = new Intent(getApplicationContext(), KeywordActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "등록 안해줬다", Toast.LENGTH_SHORT).show();
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

    /* mainFragment 의 viewpager listener 등록 */
    @Override
    public void pageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        dotViewIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void pageSelected(int position) {
        dotViewIndicator.onPageSelected(position);
    }

    @Override
    public void pageScrollStateChanged(int state) {
        dotViewIndicator.onPageScrollStateChanged(state);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
