package com.cdj.ends.ui.keyword;

/**
 * Created by Dongjin on 2017. 8. 13..
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.cdj.ends.R;
import com.cdj.ends.api.translation.TranslationAPI;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.data.Keyword;
import com.cdj.ends.data.Translation;
import com.cdj.ends.dto.TranslationDTO;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cdj.ends.Config.TRANS_GOOGLE_BASE_URL;

public class KeywordActivity extends AppCompatActivity {

    private static final String TAG = "KeywordActivity";

    private TextInputEditText editKeyword;
    private FlexboxLayout flexboxLayout;
    private Toolbar toolbarKeyword;

    private Realm mRealm;

    private TranslationAPI translationAPI;

    private Map<String, String> filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        initView();
        setToolbar();

        translationAPI = new TranslationAPI(TRANS_GOOGLE_BASE_URL);

        Realm.init(this);
        mRealm = RealmBuilder.getRealmInstance();

//        mRealm.beginTransaction();
//        mRealm.delete(Keyword.class);
//        mRealm.commitTransaction();

        RealmResults<Keyword> keywords = mRealm.where(Keyword.class).equalTo("keyword", "issue").findAll();
        if(keywords.size() == 0) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Keyword newKeyword = realm.createObject(Keyword.class);
                    newKeyword.setKeyword("issue");
                }
            });
        }

        setKeywords();
    }

    private void initView() {
        editKeyword = (TextInputEditText) findViewById(R.id.edit_keyword);
        flexboxLayout = (FlexboxLayout) findViewById(R.id.container_keyword);
        toolbarKeyword = (Toolbar) findViewById(R.id.toolbar_keyword);
    }

    private void setToolbar() {
        setSupportActionBar(toolbarKeyword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setKeywords() {
        RealmResults<Keyword> keywords = mRealm.where(Keyword.class).findAll();

        for(Keyword keyword : keywords) {
            addChildView(keyword.getKeyword());
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


    @OnClick(R.id.btnTrans_keyword)
    public void onTransKeywordClicked(View view) {
        if(editKeyword.getText() == null) {
            Toast.makeText(getApplicationContext(), this.getResources().getString(R.string.validation_check_null),
                    Toast.LENGTH_SHORT).show();
        } else {
            filter = new HashMap<>();
            filter.put("key", this.getResources().getString(R.string.GOOGLE_TRANSLATION_KEY));
            filter.put("source", "ko");
            filter.put("target", "en");
            filter.put("q", editKeyword.getText().toString());

            translationAPI.requestTranslate(filter, new Callback<TranslationDTO>() {
                @Override
                public void onResponse(Call<TranslationDTO> call, Response<TranslationDTO> response) {
                    TranslationDTO translationDTO = response.body();
                    Translation translation = translationDTO.getData().getTranslations().get(0);
                    final String translatedKeyword = translation.getTranslatedText();
                    editKeyword.setText(translatedKeyword);
                }

                @Override
                public void onFailure(Call<TranslationDTO> call, Throwable t) {

                }
            });

        }
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

                addChildView(keyword);

            } else {
                Toast.makeText(getApplicationContext(), "이미 추가되어 있는 단어입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateData(String keyword) {
        if (keyword == null || keyword.equals("")) {
            Toast.makeText(getApplicationContext(), this.getResources().getString(R.string.validation_check_null), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(keyword.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            Toast.makeText(getApplicationContext(), "한글을 변환해서 추가해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addChildView(final String keyword) {

        final TextView txtNewKeyword = new TextView(this);
        txtNewKeyword.setText(keyword);

        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(15, 20, 15, 30);
        txtNewKeyword.setLayoutParams(lp);

        txtNewKeyword.setPadding((int) getApplication().getResources().getDimension(R.dimen.keyword_padding_side),
                (int) getApplication().getResources().getDimension(R.dimen.keyword_padding_updown),
                        (int) getApplication().getResources().getDimension(R.dimen.keyword_padding_side),
                                (int) getApplication().getResources().getDimension(R.dimen.keyword_padding_updown));
        txtNewKeyword.setBackground(getDrawable(R.drawable.custom_edit_background));

        txtNewKeyword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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

        flexboxLayout.addView(txtNewKeyword);
        editKeyword.setText("");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
