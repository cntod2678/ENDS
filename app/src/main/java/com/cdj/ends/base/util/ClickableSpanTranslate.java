package com.cdj.ends.base.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.cdj.ends.R;
import com.cdj.ends.api.translation.TranslationAPI;
import com.cdj.ends.data.Translation;
import com.cdj.ends.dto.TranslationDTO;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.cdj.ends.Config.TRANS_BASE_URL;

/**
 * Created by Dongjin on 2017. 8. 9..
 */

public class ClickableSpanTranslate extends ClickableSpan {

    private final String TAG = "ClickableSpanMaker";

    private TranslationAPI translationAPI;

    private String clickedText;
    private Context mContext;

    public ClickableSpanTranslate(Context context, String clickText) {
        super();
        mContext = context;
        this.clickedText = clickText;
        translationAPI = new TranslationAPI(TRANS_BASE_URL);
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

        translationAPI.requestTrnaslate(filter, new Callback<TranslationDTO>() {
            @Override
            public void onResponse(Call<TranslationDTO> call, Response<TranslationDTO> response) {
                if(response.body() != null) {
                    TranslationDTO translationDTO = response.body();
                    //todo 리스트로 보여주자
                    Translation translation = translationDTO.getData().getTranslations().get(0);
                    String translatedText = translation.getTranslatedText();

                    Snackbar.make(view, clickedText + " : " + translatedText, Snackbar.LENGTH_SHORT)
                            .setAction("Add", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(mContext, "클릭 확인", Toast.LENGTH_SHORT).show();
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
