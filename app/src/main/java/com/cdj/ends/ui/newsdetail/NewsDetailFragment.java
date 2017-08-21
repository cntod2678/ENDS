package com.cdj.ends.ui.newsdetail;

/**
 * Created by Dongjin on 2017. 8. 9..
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdj.ends.R;
import com.cdj.ends.api.translation.TranslationAPI;
import com.cdj.ends.base.util.ChromeTabActionBuilder;
import com.cdj.ends.data.News;
import com.cdj.ends.data.Translation;
import com.cdj.ends.dto.TranslationDTO;
import com.cdj.ends.ui.newsdetail.viewmode.DetailEnKoModeFragment;
import com.cdj.ends.ui.newsdetail.viewmode.DetailEnModeFragment;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cdj.ends.Config.TRANS_GOOGLE_BASE_URL;

public class NewsDetailFragment extends Fragment  {

    private final static String TAG = "NewsDetailFragment";

    @BindView(R.id.imgMain_news) ImageView imgMainNews;
    @BindView(R.id.btnChange_translation_mode) ImageButton btnTranslationMode;
    @BindView(R.id.btnShare) ImageButton btnShare;
    @BindView(R.id.btnScrap) ImageButton btnScrap;
    @BindView(R.id.txtDetail_source) TextView txtDetailSource;
    @BindView(R.id.toolbar_news_detail) Toolbar toolbarNewsDetail;
    @BindView(R.id.txtChange_detail) TextView txtChangeDetail;
    private Unbinder unbinder;

    private static News mNews;
    private static boolean translationFlag = true;

    private ScrapWordListener scrapWordListener;

    interface ScrapWordListener {
        void scrap(News news);
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity) {
            try {
                scrapWordListener = (NewsDetailActivity) getContext();
            } catch (ClassCastException cce) {
                throw new ClassCastException(context.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            scrapWordListener = (ScrapWordListener) activity;
        } catch(ClassCastException ce) {
            Log.d(TAG, ce.getMessage());
        }
    }

    public NewsDetailFragment() {}

    public static NewsDetailFragment newInstance(News news) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        mNews = new News();
        Bundle args = new Bundle();
        args.putParcelable(News.class.getName(), Parcels.wrap(news));
        newsDetailFragment.setArguments(args);
        return newsDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mNews = Parcels.unwrap(getArguments().getParcelable(News.class.getName()));
        }
        if(mNews.getTranslated() == null) {
            requestTranslate();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        DetailEnModeFragment detailEnModeFragment = DetailEnModeFragment.newInstance(mNews);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.translation_mode_view, detailEnModeFragment).commit();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar();
        setView();
        final Snackbar snack = Snackbar.make(view, getActivity().getApplicationContext().getString(R.string.guide_translation), Snackbar.LENGTH_INDEFINITE);
        snack.setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.dismiss();
            }
        }).show();
    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarNewsDetail);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setView() {
        Glide.with(getActivity())
                .load(mNews.getUrlToImage())
                .override(600, 400)
                .centerCrop()
                .into(imgMainNews);

        txtDetailSource.setText(mNews.getSource());
    }

    @OnClick(R.id.txtChange_detail)
    public void onChangeWebPageClicked(View view) {
        ChromeTabActionBuilder.openChromTab(getActivity(), mNews.getUrl());
    }

    @OnClick(R.id.btnScrap)
    public void onScrapClicked(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage("스크랩북에 추가하시겠습니까?");

        // OK 버튼 이벤트
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                scrapWordListener.scrap(mNews);
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

    @OnClick(R.id.btnShare)
    public void onShareClicked(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, mNews.getUrl());
        intent.setType("text/plain");
        startActivity(intent);
    }

    @OnClick(R.id.btnChange_translation_mode)
    public void onChangeTranslationModeClicked(View view) {
        Fragment fragment = null;

        if(translationFlag) {
            fragment = DetailEnModeFragment.newInstance(mNews);
            btnTranslationMode.setSelected(false);
            translationFlag = false;
        } else {
            fragment = DetailEnKoModeFragment.newInstance(mNews);
            btnTranslationMode.setSelected(true);
            translationFlag = true;
        }

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.translation_mode_view, fragment).commit();
    }

    private void requestTranslate() {
        Map<String, String> filter = new HashMap<>();
        filter.put("key", getActivity().getApplicationContext().getResources().getString(R.string.GOOGLE_TRANSLATION_KEY));
        filter.put("source", "en");
        filter.put("target", "ko");
        filter.put("model", "nmt");
        filter.put("q", mNews.getDescription());

        TranslationAPI translationAPI = new TranslationAPI(TRANS_GOOGLE_BASE_URL);
        translationAPI.requestTranslate(filter, new Callback<TranslationDTO>() {
            @Override
            public void onResponse(Call<TranslationDTO> call, Response<TranslationDTO> response) {
                TranslationDTO translationDTO = response.body();
                Translation translation = translationDTO.getData().getTranslations().get(0);

                mNews.setTranslated(translation.getTranslatedText());
            }

            @Override
            public void onFailure(Call<TranslationDTO> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
