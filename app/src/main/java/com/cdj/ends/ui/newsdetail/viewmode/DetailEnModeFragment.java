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

import com.cdj.ends.R;
import com.cdj.ends.base.util.ClickableSpanTranslate;
import com.cdj.ends.data.News;

import org.parceler.Parcels;


public class DetailEnModeFragment extends Fragment {

    private static final String TAG= "DetailEnModeFragment";

    /**
     * Spaned Click 에서 SetText가 불가능
     * ButterKnife 사용하지 않음
     * */

    TextView txtTitleDetail;
    TextView txtDescriptionDetail;

    private News mNews;

    private static DetailEnModeFragment detailEnModeFragment;

    public DetailEnModeFragment() {}

    public static DetailEnModeFragment newInstance(News news) {
        if(detailEnModeFragment == null) {
            synchronized (DetailEnModeFragment.class) {
                if(detailEnModeFragment == null) {
                    detailEnModeFragment = new DetailEnModeFragment();
                }
            }
        }
        Bundle args = new Bundle();
        args.putParcelable(News.class.getName(), Parcels.wrap(news));
        detailEnModeFragment.setArguments(args);
        return detailEnModeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mNews = Parcels.unwrap(getArguments().getParcelable(News.class.getName()));
            Log.d(TAG, mNews.getTitle());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail_content_en, container, false);
        txtTitleDetail = (TextView) view.findViewById(R.id.txtTitle_detail);
        txtDescriptionDetail = (TextView) view.findViewById(R.id.txtDescription_detail);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
    }

    private void setView() {
        makeTextViewClickable(mNews.getTitle(), txtTitleDetail);
        makeTextViewClickable(mNews.getDescription(), txtDescriptionDetail);
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
