package com.cdj.ends.ui.keyword;

/**
 * Created by Dongjin on 2017. 8. 13..
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.cdj.ends.R;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.data.Keyword;

import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class KeywordActivity extends AppCompatActivity {

    private static final String TAG = "KeywordActivity";

    private TextInputEditText editKeyword;

    private GridLayout gridLayout;
    private GridLayout.LayoutParams layoutParams;

    private Toolbar toolbarKeyword;

    private Realm mRealm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        initView();
        setToolbar();

        Realm.init(this);
        mRealm = RealmBuilder.getRealmInstance();

        setKeywords();
    }

    private void initView() {
        editKeyword = (TextInputEditText) findViewById(R.id.edit_keyword);
        gridLayout = (GridLayout) findViewById(R.id.container_keyword);
        toolbarKeyword = (Toolbar) findViewById(R.id.toolbar_keyword);
    }

    private void setToolbar() {
        setSupportActionBar(toolbarKeyword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setKeywords() {
        RealmResults<Keyword> keywords = mRealm.where(Keyword.class).findAll();

        for(Keyword keyword : keywords) {
            TextView txtKeyword = new TextView(this);
            txtKeyword.setText(keyword.getKeyword());
            gridLayout.addView(txtKeyword);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btnAdd_keyword)
    public void onAddKeywordClicked(View view) {
        final String keyword = editKeyword.getText().toString();

        if(validateData(keyword)) {
            RealmResults<Keyword> keywords = mRealm.where(Keyword.class).equalTo("keyword", keyword).findAll();

            if (keywords.isEmpty()) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Keyword newKeyword = realm.createObject(Keyword.class);
                        newKeyword.setKeyword(keyword);
                    }
                });

                final TextView txtNewKeyword = new TextView(this);

                txtNewKeyword.setTextSize(15.0f);
                txtNewKeyword.setText(keyword);
                txtNewKeyword.setMaxLines(1);
                txtNewKeyword.setMaxEms(5);
                txtNewKeyword.setEllipsize(TextUtils.TruncateAt.END);

                txtNewKeyword.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //todo 삭제
                        Toast.makeText(getApplicationContext(), txtNewKeyword.getText().toString(), Toast.LENGTH_SHORT).show();

                        RealmResults<Keyword> deleteKeyword = mRealm.where(Keyword.class).equalTo("keyword", keyword).findAll();

                        if(!deleteKeyword.isEmpty()) {
                            mRealm.beginTransaction();
                            deleteKeyword.deleteFromRealm(0);
                            mRealm.commitTransaction();
                        }

                        txtNewKeyword.setVisibility(View.GONE);
                        return false;
                    }
                });

                gridLayout.addView(txtNewKeyword);
                editKeyword.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "이미 추가되어 있는 단어입니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean validateData(String keyword) {
        if (keyword == null) {
            Toast.makeText(getApplicationContext(), "키워드를 추가해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(keyword.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            Toast.makeText(getApplicationContext(), "한글을 변환해서 추가해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void deleteKeyword(View view, String keyword) {
        RealmResults<Keyword> deleteKeyword = mRealm.where(Keyword.class).equalTo("keyword", keyword).findAll();

        if(!deleteKeyword.isEmpty()) {
            mRealm.beginTransaction();
            deleteKeyword.deleteFromRealm(0);
            mRealm.commitTransaction();
        }
        gridLayout.removeView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RealmResults<Keyword> keywords = mRealm.where(Keyword.class).findAll();
        Log.d(TAG, "stop : " + keywords.size());
        mRealm.close();
    }
}
