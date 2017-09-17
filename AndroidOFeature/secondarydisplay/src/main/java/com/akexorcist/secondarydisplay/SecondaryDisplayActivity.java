package com.akexorcist.secondarydisplay;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.TextView;

public class SecondaryDisplayActivity extends AppCompatActivity {
    private TextView tvSecondaryDisplaySupportResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_display);

        bindView();
        setupView();
//        setupThing();
    }

    private void bindView() {
        tvSecondaryDisplaySupportResult = findViewById(R.id.tvSecondaryDisplaySupportResult);
    }

    private void setupView() {
        if (isSecondaryDisplaySupport(this)) {
            tvSecondaryDisplaySupportResult.setText(R.string.secondary_display_available);
        } else {
            tvSecondaryDisplaySupportResult.setText(R.string.secondary_display_unavailable);
        }
    }

    private void setupThing() {
        MediaRouter mediaRouter = (MediaRouter) getSystemService(Context.MEDIA_ROUTER_SERVICE);
        if (mediaRouter != null) {
            MediaRouter.RouteInfo routeInfo = mediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
            if (routeInfo != null) {
                Display presentationDisplay = routeInfo.getPresentationDisplay();
                Bundle bundle = ActivityOptions.makeBasic().setLaunchDisplayId(presentationDisplay.getDisplayId()).toBundle();
                Intent intent = new Intent(this, EachAnotherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent, bundle);
            }
        }
    }

    public boolean isSecondaryDisplaySupport(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS);
    }
}
