package com.cdj.ends.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cdj.ends.R;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.ui.settings.oss.OssFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * Created by Dongjin on 2017. 8. 19..
 */

public class ManageFragment extends Fragment {

    @BindView(R.id.btnOpenSource) Button btnOpenSource;
    @BindView(R.id.btnDelete_cache) Button btnDeleteCache;
    Unbinder unbinder;

    private Realm mRealm;

    public ManageFragment() {}

    public static ManageFragment newInstance() {
        Bundle args = new Bundle();
        ManageFragment fragment = new ManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(getActivity());
        mRealm = RealmBuilder.getRealmInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btnOpenSource)
    public void onOpenSourceClicked(View view) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, OssFragment.newInstance()).addToBackStack(null).commit();
    }

    @OnClick(R.id.btnDelete_cache)
    public void onDeleteCacheClicked(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("데이터 삭제");
        dialog.setMessage("삭제하면 복구가 불가능합니다.\n삭제하시겠습니까?");

        // OK 버튼 이벤트
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mRealm.beginTransaction();
                mRealm.deleteAll();
                mRealm.commitTransaction();
            }
        });

        // Cancel 버튼 이벤트
        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
        unbinder.unbind();
    }
}
