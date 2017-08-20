package com.cdj.ends.ui.word;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.cdj.ends.R;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.data.Word;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dongjin on 2017. 8. 18..
 */

public class WordActivity extends AppCompatActivity implements ActionMode.Callback, WordAdapter.DeleteWordListener {

    @BindView(R.id.recv_word) RecyclerView recvWord;
    @BindView(R.id.toolbar_word) Toolbar toolbarWord;
    @BindView(R.id.appBar_word) AppBarLayout appBarWord;

    private static final String STATE_SCROLL_POSITION = "STATE_SCROLL";
    private static final String TAG = "WordActivity";

    GestureDetectorCompat mGestureDetector;
    ActionMode mActionMode;
    private WordAdapter mWordAdapter;

    private Realm mRealm;

    private List<Section> mSections = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        ButterKnife.bind(this);

        Realm.init(this);
        mRealm = RealmBuilder.getRealmInstance();

        RealmResults<Word> dateGroupByWords = mRealm.where(Word.class).distinct("date").sort("date", Sort.DESCENDING);
        for(int idx = 0; idx < dateGroupByWords.size(); idx++) {
            Section section = new Section();
            section.index = idx;
            section.header = dateGroupByWords.get(idx).getDate();

            RealmResults<Word> words = mRealm.where(Word.class).equalTo("date", dateGroupByWords.get(idx).getDate()).findAll();
            section.footer = Integer.toString(words.size());

            for(Word word : words) {
                section.items.add(word);
            }
            mSections.add(section);
        }

        mWordAdapter = new WordAdapter(this, mSections, true);
        recvWord.setLayoutManager(new StickyHeaderLayoutManager());
        recvWord.setAdapter(mWordAdapter);

        recvWord.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                mGestureDetector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        mGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                try {
                    View view = recvWord.findChildViewUnder(e.getX(), e.getY());
                    int adapterPosition = recvWord.getChildAdapterPosition(view);

                    if (mActionMode != null) {
                        toggleSelection(adapterPosition);
                    } else {

                            int sectionIndex = mWordAdapter.getSectionForAdapterPosition(adapterPosition);
                            int footerAdapterPosition = mWordAdapter.getAdapterPositionForSectionFooter(sectionIndex);

                            if (footerAdapterPosition == adapterPosition) {
                                onFooterTapped(sectionIndex);
                            } else {
                                int itemIndex = mWordAdapter.getPositionOfItemInSection(sectionIndex, adapterPosition);
                                if (itemIndex >= 0) {
                                    onItemTapped(sectionIndex, itemIndex);
                                } else {
                                    onSectionTapped(sectionIndex);
                                }
                            }
                    }
                } catch (Exception ex) {
                    onResume();
                    Log.d(TAG, ex.getMessage());
                }

                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mActionMode == null) {
                    mActionMode = startActionMode(WordActivity.this);

                    View view = recvWord.findChildViewUnder(e.getX(), e.getY());
                    int adapterPosition = recvWord.getChildAdapterPosition(view);
                    toggleSelection(adapterPosition);
                }
                super.onLongPress(e);
            }
        });

        setToolbar();
    }

    private void setToolbar() {
        setSupportActionBar(toolbarWord);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (toolbarWord != null) {
            toolbarWord.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_endless_scroll_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        appBarWord.setExpanded(false, true);

        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_cab_selectiondemo, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete: {
                mWordAdapter.deleteSelection();
                mode.finish();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.mActionMode = null;
        mWordAdapter.clearSelection();
    }

    void toggleSelection(int adapterPosition) {

        Log.d(TAG, "toggleSelection() called with: " + "adapterPosition = [" + adapterPosition + "]");

        try {
            int sectionIndex = mWordAdapter.getSectionForAdapterPosition(adapterPosition);
            int footerAdapterPosition = mWordAdapter.getAdapterPositionForSectionFooter(sectionIndex);

            if (footerAdapterPosition == adapterPosition) {
                Log.d(TAG, "toggleSelection: toggling selection for footer @ section " + sectionIndex);
                mWordAdapter.toggleSectionFooterSelection(sectionIndex);
            } else {
                int itemIndex = mWordAdapter.getPositionOfItemInSection(sectionIndex, adapterPosition);
                if (itemIndex >= 0) {
                    Log.d(TAG, "toggleSelection: toggling selection of item @ section: " + sectionIndex + " itemIndex: " + itemIndex);
                    mWordAdapter.toggleSectionItemSelected(sectionIndex, itemIndex);
                }
            }
        }
        catch (Exception e ) {
            Log.d(TAG, e.getMessage());
        }
        // update selected item count in CAB
        int selectedItemCount = mWordAdapter.getSelectedItemCount();
        String title = getString(R.string.cab_selected_count, selectedItemCount);
        mActionMode.setTitle(title);
    }

    void onItemTapped(int sectionIndex, int itemIndex) {
        Log.d(TAG, "onItemTapped() called with: " + "sectionIndex = [" + sectionIndex + "], itemIndex = [" + itemIndex + "]");
    }

    void onSectionTapped(int sectionIndex) {
        Log.d(TAG, "onSectionTapped() called with: " + "sectionIndex = [" + sectionIndex + "]");
    }

    void onFooterTapped(int sectionIndex) {
        Log.d(TAG, "onFooterTapped() called with: " + "sectionIndex = [" + sectionIndex + "]");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        RecyclerView.LayoutManager lm = recvWord.getLayoutManager();
        Parcelable scrollState = lm.onSaveInstanceState();
        outState.putParcelable(STATE_SCROLL_POSITION, scrollState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            recvWord.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(STATE_SCROLL_POSITION));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Snackbar snack = Snackbar.make(recvWord, R.string.hint_demo_select_long_press, Snackbar.LENGTH_INDEFINITE);
        snack.setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.dismiss();
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public void deleteWord(List<Word> wordList) {
        for(Word word : wordList) {
            RealmResults<Word> deleteList = mRealm.where(Word.class).equalTo("word", word.getWord()).findAll();
            mRealm.beginTransaction();
            deleteList.deleteFromRealm(0);
            mRealm.commitTransaction();
        }
    }
}
