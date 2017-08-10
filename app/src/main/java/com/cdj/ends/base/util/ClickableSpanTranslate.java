package com.cdj.ends.base.util;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    private Map<String, String> filter = new HashMap<>();

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
    public void onClick(View tv) {
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
                    Toast.makeText(mContext, "Source : " + clickedText + " Target : " + translatedText, Toast.LENGTH_SHORT).show();
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
