//package com.cdj.ends.ui.word;
//
///**
// * Created by Dongjin on 2017. 8. 12..
// */
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.GestureDetectorCompat;
//import android.util.Log;
//import android.view.ActionMode;
//import android.view.GestureDetector;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.cdj.ends.R;
//
//public class WordFragment extends Fragment implements ActionMode.Callback {
//
//    private static final String TAG = WordFragment.class.getName();
//
//    GestureDetectorCompat gestureDetector;
//    private WordAdapter wordAdapter;
//    ActionMode actionMode;
//
//
//    public WordFragment() {}
//
//    public static WordFragment newInstance() {
//        WordFragment wordFragment = new WordFragment();
//        Bundle args = new Bundle();
//        wordFragment.setArguments(args);
//        return wordFragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_word , container, false);
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                View view = recvfindChildViewUnder(e.getX(), e.getY());
//                Log.i(TAG, "onSingleTapConfirmed: view: " + view);
//                int adapterPosition = recyclerView.getChildAdapterPosition(view);
//
//                if (actionMode != null) {
//                    toggleSelection(adapterPosition);
//                } else {
//                    int sectionIndex = adapter.getSectionForAdapterPosition(adapterPosition);
//                    int footerAdapterPosition = adapter.getAdapterPositionForSectionFooter(sectionIndex);
//
//                    if (footerAdapterPosition == adapterPosition) {
//                        onFooterTapped(sectionIndex);
//                    } else {
//                        int itemIndex = adapter.getPositionOfItemInSection(sectionIndex, adapterPosition);
//                        if (itemIndex >= 0) {
//                            onItemTapped(sectionIndex, itemIndex);
//                        } else {
//                            onSectionTapped(sectionIndex);
//                        }
//                    }
//                }
//
//
//                return super.onSingleTapConfirmed(e);
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//                if (actionMode == null) {
//                    actionMode = startActionMode(SelectionDemo.this);
//
//                    View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    int adapterPosition = recyclerView.getChildAdapterPosition(view);
//                    toggleSelection(adapterPosition);
//                }
//
//                super.onLongPress(e);
//            }
//        });
//
//        //wordAdapter = new WordAdapter(20, );    // 그룹 개수
//
//
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//
//    @Override
//    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//        return false;
//    }
//
//    @Override
//    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//        return false;
//    }
//
//    @Override
//    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//        return false;
//    }
//
//
//    @Override
//    public void onDestroyActionMode(ActionMode mode) {
//        this.actionMode = null;
//        wordAdapter.clearSelection();
//    }
//
//    void toggleSelection(int adapterPosition) {
//
//        Log.d(TAG, "toggleSelection() called with: " + "adapterPosition = [" + adapterPosition + "]");
//
//        // note: We're not supporting selection of entire section because - while it can be useful
//        // in some circumstances, it's confusing here. We only allow toggling of items/footers
//
//        int sectionIndex = wordAdapter.getSectionForAdapterPosition(adapterPosition);
//        int footerAdapterPosition = wordAdapter.getAdapterPositionForSectionFooter(sectionIndex);
//
//        if (footerAdapterPosition == adapterPosition) {
//            Log.d(TAG, "toggleSelection: toggling selection for footer @ section " + sectionIndex);
//            wordAdapter.toggleSectionFooterSelection(sectionIndex);
//        } else {
//            int itemIndex = wordAdapter.getPositionOfItemInSection(sectionIndex, adapterPosition);
//            if (itemIndex >= 0) {
//                Log.d(TAG, "toggleSelection: toggling selection of item @ section: " + sectionIndex + " itemIndex: " + itemIndex);
//                wordAdapter.toggleSectionItemSelected(sectionIndex, itemIndex);
//            }
//        }
//
//    }
//
//
//
//    void onItemTapped(int sectionIndex, int itemIndex) {
//        Log.d(TAG, "onItemTapped() called with: " + "sectionIndex = [" + sectionIndex + "], itemIndex = [" + itemIndex + "]");
//    }
//
//    void onSectionTapped(int sectionIndex) {
//        Log.d(TAG, "onSectionTapped() called with: " + "sectionIndex = [" + sectionIndex + "]");
//    }
//
//    void onFooterTapped(int sectionIndex) {
//        Log.d(TAG, "onFooterTapped() called with: " + "sectionIndex = [" + sectionIndex + "]");
//    }
//}
