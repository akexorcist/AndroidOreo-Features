package com.akexorcist.omg.pinning;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Akexorcist on 7/8/2017 AD.
 */

public class PinningActivity extends AppCompatActivity {
    private Button btnPinShortcut;
    private Button btnPinWidget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinning);

        bindView();
        setupView();
    }

    private void bindView() {
        btnPinShortcut = findViewById(R.id.btnPinShortcut);
        btnPinWidget = findViewById(R.id.btnPinWidget);
    }

    private void setupView() {
        btnPinShortcut.setOnClickListener(onPinShortCutClick());
        btnPinWidget.setOnClickListener(onPinWidgetClick());
    }

    private View.OnClickListener onPinShortCutClick() {
        return view -> pinShortcutToHomeScreen(this);
    }

    private View.OnClickListener onPinWidgetClick() {
        return view -> pinWidgetToHomeScreen(this);
    }

    private void pinShortcutToHomeScreen(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager.isRequestPinShortcutSupported()) {
            ShortcutInfo pinShortcutInfo = createShortcutInfo(this);
            Intent resultIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, resultIntent, 0);
            shortcutManager.requestPinShortcut(pinShortcutInfo, pendingIntent.getIntentSender());
        } else {
            Toast.makeText(context, R.string.device_does_not_support_shortcut_pinning, Toast.LENGTH_SHORT).show();
        }
    }

    private ShortcutInfo createShortcutInfo(Context context) {
        return new ShortcutInfo.Builder(context, "pinning_screen")
                .setShortLabel(getString(R.string.shortcut_info_short_label))
                .setLongLabel(getString(R.string.shortcut_info_long_label))
                .setIcon(Icon.createWithResource(context, R.drawable.ic_open_in_new))
                .setIntent(new Intent(this, PinningActivity.class)
                        .setAction(Intent.ACTION_VIEW))
                .build();
    }

    private void pinWidgetToHomeScreen(Context context) {
        AppWidgetManager appWidgetManager = context.getSystemService(AppWidgetManager.class);
        if (appWidgetManager.isRequestPinAppWidgetSupported()) {
            ComponentName widgetProvider = new ComponentName(context, HelloWidgetProvider.class);
            appWidgetManager.requestPinAppWidget(widgetProvider, null, null);
        } else {
            Toast.makeText(context, R.string.device_does_not_support_widget_pinning, Toast.LENGTH_SHORT).show();
        }
    }
}
