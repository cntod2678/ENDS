package com.cdj.ends.ui.settings;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.constraint.ConstraintLayout;

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

public class SettingFragment extends Fragment {

    @BindView(R.id.container_sendDevelopers) ConstraintLayout containerSend;
    @BindView(R.id.container_oss) ConstraintLayout containerOss;
    @BindView(R.id.btnOpenSource) Button btnOpenSource;
    @BindView(R.id.btnDelete_cache) Button btnDeleteCache;
    Unbinder unbinder;

    private Realm mRealm;

    public SettingFragment() {}

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
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

    @OnClick(R.id.container_sendDevelopers)
    public void onSendToDeveloperClicked(View view) {
        sendEmail();
    }

    @OnClick(R.id.container_oss)
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


    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:cntod2678@naver.com"));
        String email = getEmail(getActivity().getApplicationContext());

        intent.putExtra(Intent.EXTRA_CC, (email != null)? email:"");
        intent.putExtra(Intent.EXTRA_SUBJECT, "오류 보고");

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
        unbinder.unbind();
    }
}
