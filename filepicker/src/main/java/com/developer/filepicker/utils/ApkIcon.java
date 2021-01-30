package com.developer.filepicker.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ApkIcon extends AsyncTask<String, Integer, Drawable> {
    @SuppressLint("StaticFieldLeak")
    private ImageView icon;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String iconTag;

    public ApkIcon(Context context, ImageView icon) {
        this.context = context;
        this.icon = icon;
        this.iconTag = (String) icon.getTag();
    }

    @Override
    protected Drawable doInBackground(String... parameter) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageArchiveInfo(parameter[0], 0);
            if (packageInfo != null) {
                packageInfo.applicationInfo.sourceDir = parameter[0];
                packageInfo.applicationInfo.publicSourceDir = parameter[0];
                return packageInfo.applicationInfo.loadIcon(pm);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(Drawable result) {
        if (!icon.getTag().toString().equals(iconTag))
            return;
        if (result != null)
            icon.setImageDrawable(result);
    }
}