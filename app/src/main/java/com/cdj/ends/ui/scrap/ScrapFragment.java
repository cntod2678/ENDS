package com.cdj.ends.ui.scrap;

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
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.Scrap;
import com.cdj.ends.ui.scrap.viewmodel.ScrapItemViewModel;
import com.cdj.ends.ui.scrap.viewmodel.ScrapViewModel;
import com.cdj.ends.ui.scrap.viewmodel.ScrapViewModelImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dongjin on 2017. 8. 20..
 */

public class ScrapFragment extends Fragment {

    private static final String TAG = "ScrapFragment";

    @BindView(R.id.recv_scrap) RecyclerView recvScrap;
    Unbinder unbinder;

    private Realm mRealm;

    private ScrapAdapter scrapAdapter;

    private ScrapViewModel scrapViewModel;

    private List<Scrap> mScrapList;

    public ScrapFragment() {}

    public static ScrapFragment newInstance() {
        Bundle args = new Bundle();
        ScrapFragment fragment = new ScrapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScrapList = new ArrayList<>();

        Realm.init(getActivity());
        mRealm = RealmBuilder.getRealmInstance();

        RealmResults<Scrap> scraps = mRealm.where(Scrap.class).findAll();
        for(Scrap scrap : scraps) {
            mScrapList.add(scrap);
        }

        scrapAdapter = new ScrapAdapter(getActivity());
        scrapViewModel = new ScrapViewModelImpl(getActivity().getApplicationContext());
        scrapViewModel.setUpdateViewModelListener(new NotifyUpdateViewModelListener<List<ScrapItemViewModel>>() {
            @Override
            public void onUpdatedViewModel(List<ScrapItemViewModel> viewModel) {
                scrapAdapter.replaceData(viewModel);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrap, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecvScrap();
        scrapViewModel.fetchScraps(mScrapList);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setRecvScrap() {
        recvScrap.setLayoutManager(new LinearLayoutManager(getActivity()));
        recvScrap.setAdapter(scrapAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRealm.close();
    }
}
