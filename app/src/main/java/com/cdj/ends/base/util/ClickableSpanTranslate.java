package com.cdj.ends.base.util;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.cdj.ends.R;
import com.cdj.ends.api.translation.TranslationAPI;
import com.cdj.ends.data.Translation;
import com.cdj.ends.data.Word;
import com.cdj.ends.dto.TranslationDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.cdj.ends.Config.TRANS_GOOGLE_BASE_URL;

/**
 * Created by Dongjin on 2017. 8. 9..
 */

public class ClickableSpanTranslate extends ClickableSpan {

    private final String TAG = "ClickableSpanMaker";

    private TranslationAPI translationAPI;

    private String clickedText;
    private Context mContext;
    private Date mDate;

    public ClickableSpanTranslate() {}

    public ClickableSpanTranslate(Context context, String clickText) {
        mContext = context;

        long now = System.currentTimeMillis();
        mDate = new Date(now);

        this.clickedText = clickText;
        translationAPI = new TranslationAPI(TRANS_GOOGLE_BASE_URL);
    }

    /**
     * https://www.googleapis.com/language/translate/v2/
     * ?key=AIzaSyDL00Tc1pp-WypllEw0kmM94B8YQip-dQI
     * &source=en
     * &target=ko
     * &q=hi
     */
    public void onClick(final View view) {

        Map<String, String> filter = new HashMap<>();
        filter.put("key", mContext.getResources().getString(R.string.GOOGLE_TRANSLATION_KEY));
        filter.put("source", "en");
        filter.put("target", "ko");
        filter.put("q", clickedText);

        translationAPI.requestTranslate(filter, new Callback<TranslationDTO>() {
            @Override
            public void onResponse(Call<TranslationDTO> call, Response<TranslationDTO> response) {
                if(response.body() != null) {
                    TranslationDTO translationDTO = response.body();
                    //todo 리스트로 보여주자
                    Translation translation = translationDTO.getData().getTranslations().get(0);
                    final String translatedText = translation.getTranslatedText();

                    Snackbar.make(view, clickedText + " : " + translatedText, Snackbar.LENGTH_SHORT)
                            .setAction("Add", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Realm.init(mContext);

                                    RealmConfiguration config = new RealmConfiguration.Builder()
                                            .deleteRealmIfMigrationNeeded() // Migration to run instead of throwing an exception
                                            .build();

                                    Realm mRealm = Realm.getInstance(config);

                                    SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                                    String strDate = dateFormat.format(mDate);

                                    RealmResults<Word> words = mRealm.where(Word.class).equalTo("word", clickedText).findAll();
                                    if(words.isEmpty()) {
                                        mRealm.beginTransaction();
                                        Word word = mRealm.createObject(Word.class);
                                        word.setWord(clickedText);
                                        word.setTranslatedWord(translatedText);
                                        word.setDate(strDate);
                                        word.setChkEdu(false);
                                        mRealm.commitTransaction();
                                        Toast.makeText(mContext, clickedText + " 단어가 성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(mContext, clickedText + " 단어를 이미 추가했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    RealmResults<Word> wordList = mRealm.where(Word.class).findAll();
                                    Log.d(TAG, wordList.size() + "");
                                    mRealm.close();

                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<TranslationDTO> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

    }

    @Override
    public void updateDrawState(TextPaint ds) {
    }

}
