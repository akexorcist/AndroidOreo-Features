package com.akexorcist.omg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.akexorcist.customdatastore.MemoNoteActivity;
import com.akexorcist.fontxml.FontXmlActivity;
import com.akexorcist.notificationchannel.NotificationChannelActivity;
import com.akexorcist.omg.autosizing.AutosizingActivity;
import com.akexorcist.omg.downloadablefonts.DownloadableFontsActivity;
import com.akexorcist.omg.pinning.PinningActivity;
import com.akexorcist.omg.safebrowsing.SafeBrowsingActivity;
import com.akexorcist.pictureinpicture.PictureInPictureActivity;
import com.akexorcist.secondarydisplay.SecondaryDisplayActivity;

public class MenuActivity extends AppCompatActivity {
    private Button btnNotificationChannel;
    private Button btnPictureInPicture;
    private Button btnFontInXml;
    private Button btnDownloadableFonts;
    private Button btnAutosizingTextViews;
    private Button btnSafeBrowsing;
    private Button btnPinningShortcutsAndWidgets;
    private Button btnCustomDataStore;
    private Button btnBackgroundService;
    private Button btnSecondaryDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bindView();
        setupView();
    }

    private void bindView() {
        btnNotificationChannel = findViewById(R.id.btnNotificationChannel);
        btnPictureInPicture = findViewById(R.id.btnPictureInPicture);
        btnFontInXml = findViewById(R.id.btnFontInXml);
        btnDownloadableFonts = findViewById(R.id.btnDownloadableFonts);
        btnAutosizingTextViews = findViewById(R.id.btnAutosizingTextViews);
        btnSafeBrowsing = findViewById(R.id.btnSafeBrowsing);
        btnPinningShortcutsAndWidgets = findViewById(R.id.btnPinningShortcutsAndWidgets);
        btnCustomDataStore = findViewById(R.id.btnCustomDataStore);
        btnBackgroundService = findViewById(R.id.btnBackgroundService);
        btnSecondaryDisplay = findViewById(R.id.btnSecondaryDisplay);
    }

    private void setupView() {
        btnNotificationChannel.setOnClickListener(onNotificationChannelClick());
        btnPictureInPicture.setOnClickListener(onPictureInPictureClick());
        btnFontInXml.setOnClickListener(onFontInXmlClick());
        btnDownloadableFonts.setOnClickListener(onDownloadableFontsClick());
        btnAutosizingTextViews.setOnClickListener(onAutosizingTextViewsClick());
        btnSafeBrowsing.setOnClickListener(onSafeBrowsingClick());
        btnPinningShortcutsAndWidgets.setOnClickListener(onPinningShortcutsAndWidgetsClick());
        btnCustomDataStore.setOnClickListener(onCustomDataStoreClick());
        btnBackgroundService.setOnClickListener(onBackgroundServiceClick());
        btnSecondaryDisplay.setOnClickListener(onSecondaryDisplayClick());
    }

    private View.OnClickListener onNotificationChannelClick() {
        return view -> openActivity(NotificationChannelActivity.class);
    }

    private View.OnClickListener onPictureInPictureClick() {
        return view -> openActivity(PictureInPictureActivity.class);
    }

    private View.OnClickListener onFontInXmlClick() {
        return view -> openActivity(FontXmlActivity.class);
    }

    private View.OnClickListener onDownloadableFontsClick() {
        return view -> openActivity(DownloadableFontsActivity.class);
    }

    private View.OnClickListener onAutosizingTextViewsClick() {
        return view -> openActivity(AutosizingActivity.class);
    }

    private View.OnClickListener onSafeBrowsingClick() {
        return view -> openActivity(SafeBrowsingActivity.class);
    }

    private View.OnClickListener onPinningShortcutsAndWidgetsClick() {
        return view -> openActivity(PinningActivity.class);
    }

    private View.OnClickListener onCustomDataStoreClick() {
        return view -> openActivity(MemoNoteActivity.class);
    }

    private View.OnClickListener onBackgroundServiceClick() {
        return null;
    }

    private View.OnClickListener onSecondaryDisplayClick() {
        return view -> openActivity(SecondaryDisplayActivity.class);
    }

    private void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
