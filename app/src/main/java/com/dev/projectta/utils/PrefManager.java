package com.dev.projectta.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context mContext;

    private static final String PREF_NAME = "Pref_SetUp";

    public PrefManager(Context context){
        this.mContext = context;
        preferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
}
