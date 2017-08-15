package com.cdj.ends.ui.word;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

import android.os.Bundle;
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

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import io.realm.Realm;


public class WordActivity extends AppCompatActivity implements ActionMode.Callback {

    private static final String TAG = "WordActivity";

    public static final boolean SHOW_ADAPTER_POSITIONS = true;

    GestureDetectorCompat gestureDetector;
    WordAdapter wordAdapter;
    ActionMode actionMode;

    AppBarLayout appBarLayout;
    RecyclerView recyclerView;
    Toolbar toolbarWord;

    private Realm mRealm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Realm.init(this);
        mRealm = RealmBuilder.getRealmInstance();

        initView();
        setToolbar();
        setRecv();
        setGesture();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recv_words);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        toolbarWord = (Toolbar) findViewById(R.id.toolbar_word);
    }

    private void setToolbar() {
        setSupportActionBar(toolbarWord);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setRecv() {
        wordAdapter = new WordAdapter(this, mRealm, SHOW_ADAPTER_POSITIONS);

        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        recyclerView.setAdapter(wordAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void setGesture() {
//        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                Log.i(TAG, "onSingleTapConfirmed: view: " + view);
//                int adapterPosition = recyclerView.getChildAdapterPosition(view);
//
//                if (actionMode != null) {
//                    toggleSelection(adapterPosition);
//                } else {
//                    int sectionIndex = 0;
//
//
//                    try {
//                        sectionIndex = wordAdapter.getSectionForAdapterPosition(adapterPosition);
//                    } catch (IndexOutOfBoundsException idexException) {
//                        Log.d(TAG, idexException.getMessage());
//                    }
//
//
//                    int itemIndex = wordAdapter.getPositionOfItemInSection(sectionIndex, adapterPosition);
//                    if (itemIndex >= 0) {
//                        onItemTapped(sectionIndex, itemIndex);
//                    } else {
//                        onSectionTapped(sectionIndex);
//                    }
//                }
//
//                return super.onSingleTapConfirmed(e);
//            }

        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                Log.i(TAG, "onSingleTapConfirmed: view: " + view);
                int adapterPosition = recyclerView.getChildAdapterPosition(view);

                if (actionMode != null) {
                    toggleSelection(adapterPosition);
                } else {
                    int sectionIndex = 0;
                    int footerAdapterPosition = 0;

                    try {
                        sectionIndex = wordAdapter.getSectionForAdapterPosition(adapterPosition);
                        footerAdapterPosition = wordAdapter.getAdapterPositionForSectionFooter(sectionIndex);
                    } catch (IndexOutOfBoundsException idxException) {
                        Log.e(TAG, idxException.getMessage());
                    }

                    if (footerAdapterPosition == adapterPosition) {
                        onFooterTapped(sectionIndex);
                    } else {
                        try {
                            int itemIndex = wordAdapter.getPositionOfItemInSection(sectionIndex, adapterPosition);
                            if (itemIndex >= 0) {
                                onItemTapped(sectionIndex, itemIndex);
                            } else {
                                onSectionTapped(sectionIndex);
                            }
                        } catch (IndexOutOfBoundsException idxException) {
                            Log.e(TAG, idxException.getMessage());
                        }

                    }
                }

                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (actionMode == null) {
                    actionMode = startActionMode(WordActivity.this);

                    View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    int adapterPosition = recyclerView.getChildAdapterPosition(view);
                    toggleSelection(adapterPosition);
                }

                super.onLongPress(e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Snackbar snack = Snackbar.make(recyclerView, "길게 누르면 삭제가 가능합니다.", Snackbar.LENGTH_INDEFINITE);
        snack.setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.dismiss();
            }
        }).show();
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
        appBarLayout.setExpanded(false, true);
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
                wordAdapter.deleteSelection();
                mode.finish();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.actionMode = null;
        wordAdapter.clearSelection();
    }

    void toggleSelection(int adapterPosition) {

        Log.d(TAG, "toggleSelection() called with: " + "adapterPosition = [" + adapterPosition + "]");

        // note: We're not supporting selection of entire section because - while it can be useful
        // in some circumstances, it's confusing here. We only allow toggling of items/footers

        int sectionIndex = wordAdapter.getSectionForAdapterPosition(adapterPosition);
        int footerAdapterPosition = wordAdapter.getAdapterPositionForSectionFooter(sectionIndex);

        if (footerAdapterPosition == adapterPosition) {
            Log.d(TAG, "toggleSelection: toggling selection for footer @ section " + sectionIndex);
            wordAdapter.toggleSectionFooterSelection(sectionIndex);
        } else {
            int itemIndex = wordAdapter.getPositionOfItemInSection(sectionIndex, adapterPosition);
            if (itemIndex >= 0) {
                Log.d(TAG, "toggleSelection: toggling selection of item @ section: " + sectionIndex + " itemIndex: " + itemIndex);
                wordAdapter.toggleSectionItemSelected(sectionIndex, itemIndex);
            }
        }

        // update selected item count in CAB
        int selectedItemCount = wordAdapter.getSelectedItemCount();
        String title = this.getString(R.string.selected_word) + ": " + selectedItemCount;
        actionMode.setTitle(title);
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
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
