package com.cdj.ends.ui.newsdetail.viewmode;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdj.ends.Config;
import com.cdj.ends.R;
import com.cdj.ends.api.call.TranslateCallService;
import com.cdj.ends.api.translation.TranslationAPI;
import com.cdj.ends.base.util.ClickableSpanTranslate;
import com.cdj.ends.data.News;
import com.cdj.ends.data.Translation;
import com.cdj.ends.dto.TranslationDTO;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEnKoModeFragment extends Fragment {

    private static final String TAG = "DetailEnKoModeFragment";

    private TextView txtTitleDetailTranslated;
    private TextView txtDescriptionOrigin;
    private TextView txtDescriptionTranslated;

    private News mNews;

    String translatedText = "";

    private static DetailEnKoModeFragment detailEnKoModeFragment;

    public DetailEnKoModeFragment() {}

    public static DetailEnKoModeFragment newInstance(News news) {
        if(detailEnKoModeFragment == null) {
            synchronized (DetailEnKoModeFragment.class) {
                if(detailEnKoModeFragment == null) {
                    detailEnKoModeFragment = new DetailEnKoModeFragment();
                }
            }
        }
        Bundle args = new Bundle();
        args.putParcelable(News.class.getName(), Parcels.wrap(news));
        detailEnKoModeFragment.setArguments(args);
        return detailEnKoModeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mNews = Parcels.unwrap(getArguments().getParcelable(News.class.getName()));
        }

        /***
         * https://translation.googleapis.com/language/translate/v2
         * ?q=hi%20dongjin
         * &source=en
         * &target=ko&model=nmt&key=AIzaSyDL00Tc1pp-WypllEw0kmM94B8YQip-dQI
         */
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail_content_enko, container, false);
        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "later : " + translatedText);
        setView();
    }

    private void initView(View view) {
        txtTitleDetailTranslated = (TextView) view.findViewById(R.id.txtTitle_detail_translated);
        txtDescriptionOrigin = (TextView) view.findViewById(R.id.txtDescription_detail_origin);
        txtDescriptionTranslated = (TextView) view.findViewById(R.id.txtDescription_detail_translated);
    }

    private void setView() {
        makeTextViewClickable(mNews.getTitle(), txtTitleDetailTranslated);
        makeTextViewClickable(mNews.getDescription(), txtDescriptionOrigin);
        txtDescriptionTranslated.setText(mNews.getTranslated());
    }

    private void makeTextViewClickable(final String text, final TextView tv) {
        tv.setText("");
        String[] split = text.split("(?= )");
        SpannableString spannableString = null;

        for(String s : split) {
            spannableString=  new SpannableString(s);
            int length = s.length();
            String tag = s.trim();
            spannableString.setSpan(new ClickableSpanTranslate(getContext(), tag), 1, length,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv.append(spannableString);
        }
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
