package com.example.studentteachercollaborations.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AdminActivityStore {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public AdminActivityStore(Context context) {
        sharedPreferences = context.getSharedPreferences("UserActivity",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoginStatus(boolean status)
    {
        editor.putBoolean("status",status);
        editor.commit();
    }
    public boolean getLoginStatus()
    {
        return sharedPreferences.getBoolean("status",false);
    }
}
