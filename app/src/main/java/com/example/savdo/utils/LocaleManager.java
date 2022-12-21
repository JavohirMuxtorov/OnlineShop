package com.example.savdo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.bumptech.glide.load.engine.Resource;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class LocaleManager {

    public static Context setLocale(Context mContext){
        Log.d("prefs", getLanguagePref(mContext));
        return updateResources(mContext, getLanguagePref(mContext));
    }
    public static Context setNewLocate(Context mContext, String language){
        setLanguagePref(mContext, language);
        return updateResources(mContext, language);
    }

    private static void setLanguagePref(Context mContext, String localeKey) {
        Hawk.put("pref_lang", localeKey);
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17){
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
            res.updateConfiguration(config, res.getDisplayMetrics());
        }else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
    public static Locale getLocale(Resources res){
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >=24 ? config.getLocales().get(0) : config.locale;
    }

    private static String getLanguagePref(Context mContext) {

        return Hawk.get("pref_lang","en");
    }
}
