package com.akexorcist.backgroundexecutionlimits;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Akexorcist on 7/9/2017 AD.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Check", "Action : " + intent.getAction());
        Toast.makeText(context, "Action : " + intent.getAction(), Toast.LENGTH_SHORT).show();
    }
}
