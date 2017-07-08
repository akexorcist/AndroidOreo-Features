package com.akexorcist.omg.safebrowsing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Akexorcist on 7/8/2017 AD.
 */

public class SafeBrowsingActivity extends AppCompatActivity {
    private static final String WEB_URL = "http://testsafebrowsing.appspot.com/";
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_browsing);

        bindView();
        setupWebView();
    }

    private void bindView() {
        webView = findViewById(R.id.webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(getWebChromeClient());
        webView.setWebViewClient(getWebViewClient());
        webView.loadUrl(WEB_URL);
    }

    private WebChromeClient getWebChromeClient() {
        return new WebChromeClient() {

        };
    }

    private WebViewClient getWebViewClient() {
        return new WebViewClient() {

        };
    }
}
