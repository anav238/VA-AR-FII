package com.example.myapplication.util;

import android.app.Activity;
import android.content.Intent;

import com.example.myapplication.R;

public class ThemeUtils {

    public static int cTheme;
    public final static int  Light_Theme= 0;
    public final static int Dark_Theme=1;

    public static void changeToTheme(Activity activity, int theme) {
        cTheme = theme;
        activity.startActivity(activity.getIntent());
        activity.finish();
    }

    public static void onActivityCreateSetTheme(Activity activity){
        switch(cTheme){
            default:
            case Dark_Theme:
                activity.setTheme(R.style.DarkTheme);
                break;
            case Light_Theme:
                activity.setTheme(R.style.AppTheme);
                break;
        }
    }
}
