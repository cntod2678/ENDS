package com.cdj.ends.ui.keyword;

/**
 * Created by Dongjin on 2017. 8. 13..
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TextInputEditText;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.cdj.ends.Config;
import com.cdj.ends.R;
import com.cdj.ends.api.translation.TranslationAPI;
import com.cdj.ends.base.util.ChromeTabActionBuilder;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.data.Keyword;
import com.cdj.ends.data.Translation;
import com.cdj.ends.dto.TranslationDTO;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cdj.ends.Config.TRANS_GOOGLE_BASE_URL;
import static com.cdj.ends.Config.TRANS_NAVER_MAIN_URL;

public class KeywordActivity extends AppCompatActivity implements View.OnKeyListener, TextView.OnEditorActionListener{

    private static final String TAG = "KeywordActivity";

    @BindView(R.id.btnAdd_keyword) Button btnAddKeyword;
    @BindView(R.id.btnSearch_word) Button btnSearchWord;
    @BindView(R.id.imgvTrans_keyword) ImageView imgTransKeyword;
    @BindView(R.id.toolbar_keyword) Toolbar toolbarKeyword;

    private TextInputEditText editKeyword;
    private FlexboxLayout flexboxLayout;

    private Realm mRealm;

    private TranslationAPI translationAPI;

    private Map<String, String> filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);
        ButterKnife.bind(this);

        initView();
        setToolbar();

        translationAPI = new TranslationAPI(TRANS_GOOGLE_BASE_URL);

        Realm.init(this);
        mRealm = RealmBuilder.getRealmInstance();

        setKeywords();
        setEditText();
    }

    private void initView() {
        editKeyword = (TextInputEditText) findViewById(R.id.edit_keyword);
        flexboxLayout = (FlexboxLayout) findViewById(R.id.container_keyword);
    }

    private void setToolbar() {
        setSupportActionBar(toolbarKeyword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("키워드 추가");
    }

    private void setKeywords() {
        RealmResults<Keyword> keywords = mRealm.where(Keyword.class).findAll();

        for(Keyword keyword : keywords) {
            addChildView(keyword.getKeyword());
        }
    }

    private void setEditText() {
        editKeyword.setOnKeyListener(this);
        editKeyword.setOnEditorActionListener(this);
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


    @OnClick(R.id.imgvTrans_keyword)
    public void onTransKeywordClicked(View view) {
        String keyword = editKeyword.getText().toString();
        if(keyword.equals("")) {
            Toast.makeText(getApplicationContext(), this.getResources().getString(R.string.validation_check_null),
                    Toast.LENGTH_SHORT).show();
        }
        else if(keyword.matches("[A-Za-z]*")) {
            Toast.makeText(getApplicationContext(), "영어에서 한글로의 변환은 불가능합니다.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
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

    @OnClick(R.id.btnSearch_word)
    public void onSearchWordClicked(View view) {
        ChromeTabActionBuilder.openChromTab(this, TRANS_NAVER_MAIN_URL);
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
        if(keyword.matches("[0-9]")) {
            Toast.makeText(getApplicationContext(), "숫자를 제거해주세요", Toast.LENGTH_SHORT).show();
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

        txtNewKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(KeywordActivity.this);
                dialog.setMessage("지우겠습니까?");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RealmResults<Keyword> deleteKeyword = mRealm.where(Keyword.class).equalTo("keyword", keyword).findAll();

                        if(!deleteKeyword.isEmpty()) {
                            mRealm.beginTransaction();
                            deleteKeyword.deleteFromRealm(0);
                            mRealm.commitTransaction();
                        }

                        txtNewKeyword.setVisibility(View.GONE);
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
        });

        flexboxLayout.addView(txtNewKeyword);
        editKeyword.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
            addChildView(editKeyword.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            addChildView(editKeyword.getText().toString());
            return true;
        }
        return false;
    }
}
