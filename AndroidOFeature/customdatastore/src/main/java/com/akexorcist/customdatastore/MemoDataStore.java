package com.akexorcist.customdatastore;

import android.content.Context;
import android.preference.PreferenceDataStore;

import com.orhanobut.hawk.Hawk;

/**
 * Created by Akexorcist on 7/9/2017 AD.
 */

public class MemoDataStore implements PreferenceDataStore {

    public static MemoDataStore getInstance(Context context) {
        if (!Hawk.isBuilt()) {
            Hawk.init(context).build();
        }
        return new MemoDataStore();
    }

    private MemoDataStore() {
    }

    @Override
    public void putString(String key, String value) {
        Hawk.put(key, value);
    }

    @Override
    public String getString(String key, String defValue) {
        String value = Hawk.get(key);
        return value != null ? value : defValue;
    }

    public boolean removeString(String key) {
        return Hawk.delete(key);
    }
}
