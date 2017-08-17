package com.cdj.ends.ui.newsdetail;

/**
 * Created by Dongjin on 2017. 8. 9..
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    @BindView(R.id.btnChange_translation_mode) ImageView btnTranslationMode;
    @BindView(R.id.btnChange_to_web) Button btnChangePage;
    @BindView(R.id.toolbar_news_detail) Toolbar toolbarNewsDetail;
    @BindView(R.id.txtChange_detail) TextView txtChangeDetail;
    private Unbinder unbinder;

    private static News mNews;
    private static boolean translationFlag = true;

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
        Snackbar.make(view, getActivity().getApplicationContext().getString(R.string.guide_translation), Snackbar.LENGTH_LONG).show();
    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarNewsDetail);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setView() {
        Glide.with(getActivity())
                .load(mNews.getUrlToImage())
                .fitCenter()
                .into(imgMainNews);
    }

    @OnClick({R.id.btnChange_to_web, R.id.txtChange_detail})
    public void onChangeWebPageClicked(View v) {
        ChromeTabActionBuilder.openChromTab(getActivity(), mNews.getUrl());
    }

    @OnClick(R.id.btnChange_translation_mode)
    public void onChangeTranslationMode(View v) {
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
        translationAPI.requestTrnaslate(filter, new Callback<TranslationDTO>() {
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
