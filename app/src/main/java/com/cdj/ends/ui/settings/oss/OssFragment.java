package com.cdj.ends.ui.settings.oss;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdj.ends.R;
import com.cdj.ends.data.OpenSource;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dongjin on 2017. 8. 20..
 */

public class OssFragment extends Fragment {

    private static final String TAG = "OssFragment";

    @BindView(R.id.recv_openSource) RecyclerView recvOpenSource;
    Unbinder unbinder;

    private List<OpenSource> openSourceList;

    private OssAdapter ossAdapter;

    public OssFragment() {}

    public static OssFragment newInstance() {
        Bundle args = new Bundle();
        OssFragment fragment = new OssFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
        ossAdapter = new OssAdapter(openSourceList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oss, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecv();
    }

    private void setRecv() {
        recvOpenSource.setLayoutManager(new LinearLayoutManager(getActivity()));
        recvOpenSource.setAdapter(ossAdapter);
    }

    private void setData() {
        openSourceList = new ArrayList<>();
        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.open_sources);
        int typeLength = ta.length();

        String[][] array = new String[typeLength][];

        for (int i = 0; i < typeLength; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                array[i] = res.getStringArray(id);
                OpenSource oss = new OpenSource();
                oss.setOss(array[i][0]);
                oss.setUrl(array[i][1]);
                List<String> copies = new ArrayList<>();
                List<String> licenses = new ArrayList<>();
                for(int j = 2; j < array[i].length; j++) {
                    if(array[i][j].startsWith("Copyright")) {
                        copies.add(array[i][j]);
                    } else {
                        licenses.add(array[i][j]);
                    }
                }
                oss.setCopyrights(copies);
                oss.setLicenses(licenses);

                openSourceList.add(oss);
            } else {
                Log.d(TAG, "resource array convert wrong");
            }
        }
        ta.recycle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
