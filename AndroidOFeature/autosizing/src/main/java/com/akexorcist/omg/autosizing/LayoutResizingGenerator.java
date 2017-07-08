package com.akexorcist.omg.autosizing;

import android.os.AsyncTask;

/**
 * Created by Akexorcist on 7/8/2017 AD.
 */

public class LayoutResizingGenerator extends AsyncTask<Integer, Integer, Void> {
    private static final float MINIMUM_SIZE_PERCENTAGE = 10f;
    private static final float RESIZE_STEP_PERCENTAGE = 0.5f;
    private static final int RESIZE_STEP_DURATION = 10;

    private LayoutResizingListener layoutResizingListener;

    private boolean isRunning = false;

    public static LayoutResizingGenerator newInstance(int layoutWidth) {
        LayoutResizingGenerator layoutResizingGenerator = new LayoutResizingGenerator();
        layoutResizingGenerator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, layoutWidth);
        return layoutResizingGenerator;
    }

    private LayoutResizingGenerator() {
    }

    public void setLayoutResizingListener(LayoutResizingListener listener) {
        this.layoutResizingListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isRunning = true;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        float layoutWidth = integers[0];
        int loopFactor = 1;
        float resizedWidth = layoutWidth;
        while (isRunning) {
            if (resizedWidth <= layoutWidth * (MINIMUM_SIZE_PERCENTAGE / 100f) ||
                    resizedWidth >= layoutWidth) {
                loopFactor *= -1;
            }
            resizedWidth += layoutWidth * (RESIZE_STEP_PERCENTAGE / 100f) * loopFactor;
            if (layoutResizingListener != null) {
                layoutResizingListener.onResizing((int) resizedWidth);
            }
            try {
                Thread.sleep(RESIZE_STEP_DURATION);
            } catch (InterruptedException ignored) {
            }
        }
        return null;
    }

    public void cancel() {
        isRunning = false;
        super.cancel(true);
    }

    public interface LayoutResizingListener {
        void onResizing(int resizedWidth);
    }
}
