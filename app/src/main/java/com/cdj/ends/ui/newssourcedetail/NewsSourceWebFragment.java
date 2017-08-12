package com.cdj.ends.ui.newssourcedetail;

/**
 * Created by Dongjin on 2017. 8. 11..
 */

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cdj.ends.R;
import com.cdj.ends.data.NewsSource;

import org.parceler.Parcels;

public class NewsSourceWebFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "SourceWebFragment";

    private static NewsSource mNewsSource;

    private WebView mWebRTCWebView;

    private static NewsSourceWebFragment newsSourceWebFragment;

    public NewsSourceWebFragment () {}

    public static NewsSourceWebFragment newInstance(NewsSource newsSource) {
        if(newsSourceWebFragment == null) {
            synchronized (NewsSourceWebFragment.class) {
                if(newsSourceWebFragment == null) {
                    newsSourceWebFragment = new NewsSourceWebFragment();
                }
            }
        }
        mNewsSource = new NewsSource();

        Bundle args = new Bundle();
        args.putParcelable(NewsSource.class.getName(), Parcels.wrap(newsSource));
        newsSourceWebFragment.setArguments(args);
        return newsSourceWebFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((NewsSourceWebActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsSource = Parcels.unwrap(getArguments().getParcelable(NewsSource.class.getName()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_source_web, container, false);

        mWebRTCWebView = (WebView) view.findViewById(R.id.webView_source);

        setUpWebViewDefaults(mWebRTCWebView);

        mWebRTCWebView.loadUrl(mNewsSource.getUrl());

        mWebRTCWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "onPermissionRequest");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(request.getOrigin().toString().equals("https://apprtc-m.appspot.com/")) {
                            request.grant(request.getResources());
                        } else {
                            request.deny();
                        }
                    }
                });
            }

        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStop() {
        super.onStop();

        /**
         * When the application falls into the background we want to stop the media stream
         * such that the camera is free to use by other apps.
         */
        mWebRTCWebView.evaluateJavascript("if(window.localStream){window.localStream.stop();}", null);
    }

//    @TargetApi(Build.VERSION_CODES.L)
    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.setWebViewClient(new WebViewClient());

        // AppRTC requires third party cookies to work
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptThirdPartyCookies(mWebRTCWebView, true);
    }
}
