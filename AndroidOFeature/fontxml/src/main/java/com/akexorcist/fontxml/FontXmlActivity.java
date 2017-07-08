package com.akexorcist.fontxml;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Akexorcist on 7/8/2017 AD.
 */

public class FontXmlActivity extends AppCompatActivity {
    private TextView tvAnyMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_xml);

        bindView();
        setupView();
    }

    private void bindView() {
        tvAnyMessage = findViewById(R.id.tvAnyMessage);
    }

    private void setupView() {
        Typeface typeface = ResourcesCompat.getFont(this, R.font.th_sarabun_new);
        tvAnyMessage.setTypeface(typeface);
    }
}

