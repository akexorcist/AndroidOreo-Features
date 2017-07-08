package com.akexorcist.omg.autosizing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by Akexorcist on 7/8/2017 AD.
 */

public class AutosizingActivity extends AppCompatActivity {
    private FrameLayout layoutRegularContainer;
    private FrameLayout layoutDefaultContainer;
    private FrameLayout layoutGranularityContainer;
    private FrameLayout layoutPresetContainer;

    private LayoutResizingGenerator regularResizingGenerator;
    private LayoutResizingGenerator defaultResizingGenerator;
    private LayoutResizingGenerator granularityResizingGenerator;
    private LayoutResizingGenerator presetResizingGenerator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autosizing);

        bindView();
        setupView();
    }

    private void bindView() {
        layoutRegularContainer = findViewById(R.id.layoutRegularContainer);
        layoutDefaultContainer = findViewById(R.id.layoutDefaultContainer);
        layoutGranularityContainer = findViewById(R.id.layoutGranularityContainer);
        layoutPresetContainer = findViewById(R.id.layoutPresetContainer);
    }

    private void setupView() {
        layoutRegularContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        layoutRegularContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        generateRegularResizing(layoutRegularContainer.getWidth());
                    }
                });
        layoutDefaultContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        layoutDefaultContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        generateDefaultResizing(layoutDefaultContainer.getWidth());
                    }
                });
//        layoutGranularityContainer.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        layoutGranularityContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        generateGranularityResizing(layoutGranularityContainer.getWidth());
//                    }
//                });
//        layoutPresetContainer.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        layoutPresetContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        generatePresetResizing(layoutPresetContainer.getWidth());
//                    }
//                });
    }

    private void generateRegularResizing(int layoutWidth) {
        regularResizingGenerator = LayoutResizingGenerator.newInstance(layoutWidth);
        regularResizingGenerator.setLayoutResizingListener(resizedWidth -> runOnUiThread(() -> {
            ViewGroup.LayoutParams layoutParams = layoutRegularContainer.getLayoutParams();
            layoutParams.width = resizedWidth;
            layoutRegularContainer.setLayoutParams(layoutParams);
        }));
    }

    private void generateDefaultResizing(int layoutWidth) {
        defaultResizingGenerator = LayoutResizingGenerator.newInstance(layoutWidth);
        defaultResizingGenerator.setLayoutResizingListener(resizedWidth -> runOnUiThread(() -> {
            ViewGroup.LayoutParams layoutParams = layoutDefaultContainer.getLayoutParams();
            layoutParams.width = resizedWidth;
            layoutDefaultContainer.setLayoutParams(layoutParams);
        }));
    }

    private void generateGranularityResizing(int layoutWidth) {
        granularityResizingGenerator = LayoutResizingGenerator.newInstance(layoutWidth);
        granularityResizingGenerator.setLayoutResizingListener(resizedWidth -> runOnUiThread(() -> {
            ViewGroup.LayoutParams layoutParams = layoutGranularityContainer.getLayoutParams();
            layoutParams.width = resizedWidth;
            layoutGranularityContainer.setLayoutParams(layoutParams);
        }));
    }

    private void generatePresetResizing(int layoutWidth) {
        presetResizingGenerator = LayoutResizingGenerator.newInstance(layoutWidth);
        presetResizingGenerator.setLayoutResizingListener(resizedWidth -> runOnUiThread(() -> {
            ViewGroup.LayoutParams layoutParams = layoutPresetContainer.getLayoutParams();
            layoutParams.width = resizedWidth;
            layoutPresetContainer.setLayoutParams(layoutParams);
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyDefaultResizing();
        destroyGranularityResizing();
        destroyPresetResizing();
    }

    private void destroyDefaultResizing() {
        if (defaultResizingGenerator != null) {
            defaultResizingGenerator.cancel();
            defaultResizingGenerator = null;
        }
    }

    private void destroyGranularityResizing() {
        if (granularityResizingGenerator != null) {
            granularityResizingGenerator.cancel();
            granularityResizingGenerator = null;
        }
    }

    private void destroyPresetResizing() {
        if (presetResizingGenerator != null) {
            presetResizingGenerator.cancel();
            presetResizingGenerator = null;
        }
    }
}
