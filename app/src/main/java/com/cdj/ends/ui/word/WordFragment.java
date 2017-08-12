package com.cdj.ends.ui.word;

/**
 * Created by Dongjin on 2017. 8. 12..
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdj.ends.R;

public class WordFragment extends Fragment {

    //private static WordFragment wordFragment;

    public WordFragment() {}

    public static WordFragment newInstance() {
//        if(wordFragment == null) {
//            synchronized (WordFragment.class) {
//                if(wordFragment == null) {
//                    wordFragment = new WordFragment();
//                }
//            }
//        }
        WordFragment wordFragment = new WordFragment();
        Bundle args = new Bundle();
        wordFragment.setArguments(args);
        return wordFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word , container, false);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
