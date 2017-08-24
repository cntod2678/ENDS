package com.cdj.ends.ui.main;

import android.support.v4.app.FragmentManager;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cdj.ends.R;
import com.cdj.ends.base.command.PageSwipeCommand;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.base.view.ViewPagerDotView;
import com.cdj.ends.data.Scrap;
import com.cdj.ends.ui.scrap.ScrapAdapter;
import com.cdj.ends.ui.scrap.ScrapFragment;
import com.cdj.ends.ui.keyword.KeywordActivity;
import com.cdj.ends.ui.settings.SettingFragment;
import com.cdj.ends.ui.word.WordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.cdj.ends.ui.main.MainFragmentAdapter.PAGE_NUM;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    MainFragment.MainViewChange, ScrapAdapter.DeleteScrap {

    private static final String TAG = "MainActivity";

    private final String MAIN_PAGE = "MAIN_PAGE";
    private static int CURRENT_PAGE = 1;

    @BindView(R.id.toolbar_main) Toolbar toolbarMain;
    @BindView(R.id.guide_main) LinearLayout guideMain;

    private PageSwipeCommand dotViewIndicator;
    private DrawerLayout drawerLayout;
    private boolean mTerminateFlag = false;

    Handler mKillHandler;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);
        mRealm = RealmBuilder.getRealmInstance();

        if(getIntent() != null) {
            CURRENT_PAGE = getIntent().getIntExtra(MAIN_PAGE, 1);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, MainFragment.newInstance(CURRENT_PAGE)).commit();

        initView();
        setToolbar();
        setNavDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();

        /* 종료버튼이 한번 더 눌리지 않으면 Flag 값 복원 */
        mKillHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0) {
                    mTerminateFlag = false;
                }
            }
        };
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dotViewIndicator = (ViewPagerDotView) findViewById(R.id.dotView_indicator);
    }

    private void setToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dotViewIndicator.setPageTurnCommand(new ViewPagerDotView(this));
        dotViewIndicator.setNumOfCircles(PAGE_NUM, getResources().getDimensionPixelSize(R.dimen.indicator_radius_2));
    }

    private void setNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_source) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MAIN_PAGE, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (id == R.id.nav_keyword) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MAIN_PAGE, 1);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (id == R.id.nav_search) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MAIN_PAGE, 2);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (id == R.id.nav_word) {
            Intent intent = new Intent(getApplicationContext(), WordActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_add_keyword) {
            Intent intent = new Intent(getApplicationContext(), KeywordActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_manage) {
            guideMain.setVisibility(View.INVISIBLE);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame, SettingFragment.newInstance())
                    .commit();
        }
        else {
            guideMain.setVisibility(View.INVISIBLE);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame, ScrapFragment.newInstance())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
    public void deleteScrap(String title, String date) {
        RealmResults<Scrap> deleteScrapList = mRealm.where(Scrap.class).equalTo("title", title)
                .equalTo("scrapDate", date).findAll();

        if(deleteScrapList.size() != 0) {
            mRealm.beginTransaction();
            deleteScrapList.deleteFirstFromRealm();
            Log.d(TAG, title + ", " + date + " : " + "success");
            mRealm.commitTransaction();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mKillHandler != null) {
            mKillHandler.removeMessages(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
