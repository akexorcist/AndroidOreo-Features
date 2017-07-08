package com.akexorcist.omg.downloadablefonts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.provider.FontsContractCompat.FontRequestCallback;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Akexorcist on 7/8/2017 AD.
 */

public class DownloadableFontsActivity extends AppCompatActivity {
    private TextView tvGreetingMessage;
    private TextView tvDescriptionMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadable_fonts);

        bindView();
        setupFontRequest();
    }

    private void bindView() {
        tvGreetingMessage = findViewById(R.id.tvGreetingMessage);
        tvDescriptionMessage = findViewById(R.id.tvDescriptionMessage);
    }

    private void setupFontRequest() {
        String familyName = "Chonburi";
        String providerAuthority = "com.google.android.gms.fonts";
        String providerPackage = "com.google.android.gms";
        String query = "name=" + familyName;
        FontRequest request = new FontRequest(
                providerAuthority,
                providerPackage,
                query,
                R.array.com_google_android_gms_fonts_certs);

        FontsContractCompat.requestFont(this, request, fontRequestCallback, getFontHandler());
    }

    private Handler getFontHandler() {
        HandlerThread handlerThread = new HandlerThread("fonts");
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    private FontRequestCallback fontRequestCallback = new FontRequestCallback() {
        @Override
        public void onTypefaceRetrieved(Typeface typeface) {
            tvGreetingMessage.setTypeface(typeface);
        }

        @Override
        public void onTypefaceRequestFailed(int reason) {
            showFontRequestFailed(getFailedReasonMessage(reason));
        }
    };

    private void showFontRequestFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getFailedReasonMessage(int reason) {
        switch (reason) {
            case FontRequestCallback.FAIL_REASON_PROVIDER_NOT_FOUND:
                return getString(R.string.font_provider_not_found);
            case FontRequestCallback.FAIL_REASON_FONT_NOT_FOUND:
                return getString(R.string.font_not_found);
            case FontRequestCallback.FAIL_REASON_FONT_UNAVAILABLE:
                return getString(R.string.font_unavailable);
            case FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR:
                return getString(R.string.font_load_error);
            case FontRequestCallback.FAIL_REASON_MALFORMED_QUERY:
                return getString(R.string.malformed_query);
            case FontRequestCallback.FAIL_REASON_WRONG_CERTIFICATES:
                return getString(R.string.wrong_certificates);
            default:
                return getString(R.string.unknown_error);
        }
    }
}
