package com.example.chav.poker.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;

/**
 * Created by Chav on 4/5/2016.
 */
public class SavedSharedPreferences {

    static final String PREF_USER_NAME = "username";
    static final String PREF_BEST_SCORE = "best_score";

    static SharedPreferences getSharedPreference(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String username) {
        SharedPreferences.Editor editor = getSharedPreference(ctx).edit();
        editor.putString(PREF_USER_NAME, username);
        editor.commit();
    }

    public static String getUsername(Context ctx) {
        return getSharedPreference(ctx).getString(PREF_USER_NAME, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreference(ctx).edit();
        editor.clear();
        editor.apply();
    }

    public static void setBestScore(Context ctx, int bestScore) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences("Score", Context.MODE_PRIVATE).edit();
        editor.putInt(PREF_BEST_SCORE, bestScore);
        editor.commit();
    }

    public static int getBestScore(Context ctx) {
        return ctx.getSharedPreferences("Score", Context.MODE_PRIVATE).getInt(PREF_BEST_SCORE, 0);
    }

    public static void clearBestScore(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreference(ctx).edit();
        editor.clear();
        editor.apply();
    }
}
